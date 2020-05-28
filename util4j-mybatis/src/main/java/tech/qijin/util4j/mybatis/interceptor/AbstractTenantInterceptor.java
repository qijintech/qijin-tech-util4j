package tech.qijin.util4j.mybatis.interceptor;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import tech.qijin.util4j.utils.LogFormat;

/**
 * @author michealyang
 * @date 2018/12/9
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j(topic = "MyBatis")
public abstract class AbstractTenantInterceptor<T> implements Interceptor {

    private RewriteService rewriteService = new RewriteService<T>();
    /**
     * enable属性定义字段
     */
    private final static String ENABLE_PROPERTY = "enable";
    private final static String INCLUDED_TABLES = "includedTables";
    private final static String EXCLUDED_TABLES = "excludedTables";
    private final static String TENANT_COLUMN_NAME = "tenantColumnName";

    /**
     * 是否开启env和valid增强。默认为true
     */
    private boolean enable = true;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //如果没有开启增强，则不添加env和valid
        if (!isEnabled()) {
            log.info(LogFormat.builder().message("tenant interceptor is disabled").build());
            return invocation.proceed();
        }
        long start = System.currentTimeMillis();
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[Config.MAPPED_STATEMENT_INDEX];
        Object parameter = args[Config.PARAMETER_INDEX];
        Configuration configuration = ms.getConfiguration();
        Object target = invocation.getTarget();
        StatementHandler handler = configuration.newStatementHandler((Executor) target, ms,
                parameter, RowBounds.DEFAULT, null, null);
        //记录SQL
        BoundSql boundSql = handler.getBoundSql();
        //sql类型
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        rewriteService.dispatch(sqlCommandType, invocation, boundSql, getTenantValue());
        log.info(LogFormat.builder()
                .message("tenant interceptor")
                .put("time cost", (System.currentTimeMillis() - start) + " ms")
                .build());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String enableProperty = properties.getProperty(ENABLE_PROPERTY, "true");
        enable = "true".equals(enableProperty.toLowerCase());
        String includedTablesProperty = properties.getProperty(INCLUDED_TABLES, "");
        rewriteService.setIncludedTables(
                Lists.newArrayList(includedTablesProperty.split(","))
                        .stream().map(String::trim)
                        .filter(ele -> StringUtils.isNotBlank(ele))
                        .collect(Collectors.toList()));
        String excludedTablesProperty = properties.getProperty(EXCLUDED_TABLES, "");
        rewriteService.setExcludedTables(
                Lists.newArrayList(excludedTablesProperty.split(","))
                        .stream().map(String::trim)
                        .filter(ele -> StringUtils.isNotBlank(ele))
                        .collect(Collectors.toList())
        );
        rewriteService.setTenantColumnName(properties.getProperty(TENANT_COLUMN_NAME, ""));
    }

    public abstract T getTenantValue();

    public static class RewriteService<T> {

        private List<String> includedTables;
        private List<String> excludedTables;
        private String tenantColumnName;

        private static final CCJSqlParserManager parserManager = new CCJSqlParserManager();

        private static Statement sqlParse(String originalSql) throws JSQLParserException {
            return parserManager.parse(new StringReader(originalSql));
        }

        public void dispatch(SqlCommandType sqlCommandType,
                             Invocation invocation,
                             BoundSql boundSql,
                             T tenantValue) {
            /**
             * 如果tenant column name为空，则不重写sql
             */
            if (StringUtils.isBlank(tenantColumnName)) {
                log.warn(LogFormat.builder().message("tenant column name is blank").build());
                return;
            }
            String originalSql = boundSql.getSql();
            Statement statement;
            try {
                statement = sqlParse(originalSql);
                switch (sqlCommandType) {
                    case INSERT:
                        rewriteInsert((Insert) statement, invocation, boundSql, tenantValue);
                        break;
                    case UPDATE:
                        rewriteUpdate((Update) statement, invocation, boundSql, tenantValue);
                        break;
                    case SELECT:
                        rewriteSelect((Select) statement, invocation, boundSql, tenantValue);
                        break;
                }
            } catch (JSQLParserException e) {
                log.error(LogFormat.builder().message("parse sql error").build(), e);
            }
        }

        public void rewriteInsert(Insert insert,
                                  Invocation invocation,
                                  BoundSql boundSql,
                                  T tenantValue) throws JSQLParserException {
            if (!needRewrite(insert)) {
                return;
            }
            List<Column> columns = insert.getColumns();
            //检查tenant是否已经存在
            long count = columns.stream().filter(column -> {
                String columnName = column.getColumnName();
                return columnName.equals(getTenantColumnName())
                        || columnName.contains("." + getTenantColumnName());
            }).count();
            if (count > 0) {
                return;
            }
            Table table = insert.getTable();
            Column column = new Column(table, getTenantColumnName());
            columns.add(column);

            ItemsList itemsList = insert.getItemsList();
            MultiExpressionList multiExpressionList;
            if (itemsList instanceof ExpressionList) {
                multiExpressionList = new MultiExpressionList();
                multiExpressionList.addExpressionList((ExpressionList) itemsList);
            } else {
                multiExpressionList = (MultiExpressionList) itemsList;
            }

            for (ExpressionList expressionList : multiExpressionList.getExprList()) {
                List<Expression> expressions = expressionList.getExpressions();
                String targetTenantValue = String.valueOf(tenantValue);
                if (tenantValue instanceof String) {
                    StringBuilder sb = new StringBuilder();
                    targetTenantValue = sb.append("'").append(tenantValue).append("'").toString();
                }
                Expression valueExpression = CCJSqlParserUtil.parseExpression(String.valueOf(targetTenantValue));
                expressions.add(valueExpression);
            }

            insert.setItemsList(multiExpressionList);
            doInvocation(insert.toString(), invocation, boundSql);
        }

        public void rewriteUpdate(Update update,
                                  Invocation invocation,
                                  BoundSql boundSql,
                                  T tenantValue) throws JSQLParserException {
            if (!needRewrite(update)) {
                return;
            }
            update.setWhere(modifyWhere(update.getWhere(), tenantValue));
            doInvocation(update.toString(), invocation, boundSql);
        }

        public void rewriteSelect(Select select,
                                  Invocation invocation,
                                  BoundSql boundSql,
                                  T tenantValue) throws JSQLParserException {
            PlainSelect ps = (PlainSelect) select.getSelectBody();
            Map<String, String> needRewriteTables = getNeedRewriteTables(ps);
            if (!needRewriteTables.isEmpty()) {
                Expression where = modifyWhere(ps.getWhere(), needRewriteTables, tenantValue);
                ps.setWhere(where);
            }
            doInvocation(ps.toString(), invocation, boundSql);
        }

        private Expression modifyWhere(Expression where, T tenantValue) throws JSQLParserException {
            if (where == null) {
                Statement statement = parserManager.parse(new StringReader("select * from a where 1 = 1"));
                Select select = (Select) statement;
                PlainSelect ps = (PlainSelect) select.getSelectBody();
                where = ps.getWhere();
            }
            Expression leftExpression = addParenthesis(where);
            Expression rightExpression = addParenthesis(getEqualsTo(getTenantColumnName(), tenantValue));
            AndExpression andExpression = new AndExpression(leftExpression, rightExpression);
            return andExpression;
        }

        private Expression modifyWhere(Expression where, Map<String, String> tableNameToAliasMap, T tenantValue)
                throws JSQLParserException {
            if (where == null) {
                Statement statement = parserManager.parse(new StringReader("select * from a where 1 = 1"));
                Select select = (Select) statement;
                PlainSelect ps = (PlainSelect) select.getSelectBody();
                where = ps.getWhere();
            }
            Expression leftExpression = where;
            Expression rightExpression;
            for (Map.Entry<String, String> entry : tableNameToAliasMap.entrySet()) {
                StringBuilder tenantColumnName = new StringBuilder();
                if (entry.getValue() != null) {
                    tenantColumnName.append(entry.getValue()).append(".").append(getTenantColumnName());
                    rightExpression = addParenthesis(getEqualsTo(tenantColumnName.toString(), tenantValue));
                } else {
                    tenantColumnName.append(entry.getKey()).append(".").append(getTenantColumnName());
                    rightExpression = addParenthesis(getEqualsTo(tenantColumnName.toString(), tenantValue));
                }
                leftExpression = new AndExpression(leftExpression, rightExpression);
            }
            return leftExpression;
        }

        private Expression getEqualsTo(String name, T tenantValue) {
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new Column(name));
            if (tenantValue instanceof String) {
                equalsTo.setRightExpression(new Column(new StringBuffer().append("'").append(tenantValue.toString()).append("'").toString()));
            } else {
                equalsTo.setRightExpression(new Column(tenantValue.toString()));
            }

            return equalsTo;
        }

        private Expression addParenthesis(Expression expression) {
            return new Parenthesis(expression);
        }

        private Map<String, String> getNeedRewriteTables(PlainSelect ps) {
            Map<String, String> tableNameToAliasMap = Maps.newHashMap();
            List<Table> tables = Lists.newArrayList();
            FromItem fromItem = ps.getFromItem();

            // 单表
            if (fromItem instanceof Table) {
                Table table = (Table) fromItem;
                if (checkConfiguration(table.getName())) {
                    tables.add(table);
                }
            }
            List<Join> joins = ps.getJoins();
            // 关联查询的表
            // 支持联查,可以配置是否开启
            if (CollectionUtils.isNotEmpty(joins)) {
                for (Join join : joins) {
                    if (join.getRightItem() instanceof Table) {
                        Table table = (Table) join.getRightItem();
                        if (checkConfiguration(table.getName())) {
                            tables.add((Table) join.getRightItem());
                        }
                    }
                }
            }
            for (Table table : tables) {
                if (table.getAlias() == null) {
                    tableNameToAliasMap.put(table.getName(), null);
                } else {
                    tableNameToAliasMap.put(table.getName(), table.getAlias().getName());
                }
            }
            return tableNameToAliasMap;
        }

        public void doInvocation(String newSql,
                                 Invocation invocation,
                                 BoundSql boundSql) {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[Config.MAPPED_STATEMENT_INDEX];
            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),
                    newSql,
                    boundSql.getParameterMappings(),
                    boundSql.getParameterObject());
            for (ParameterMapping mapping : boundSql.getParameterMappings()) {
                String prop = mapping.getProperty();
                if (boundSql.hasAdditionalParameter(prop)) {
                    newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
                }
            }

            // 把新的查询放到statement里
            MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));

            if (args.length == Config.LENGTH_SIX) {
                Executor executor = (Executor) invocation.getTarget();
                Object parameter = args[Config.PARAMETER_INDEX];
                RowBounds rowBounds = (RowBounds) args[Config.ROW_BOUNDS_INDEX];
                CacheKey newCacheKey = executor.createCacheKey(newMs, parameter, rowBounds, newBoundSql);
                args[Config.CACHE_KEY_INDEX] = newCacheKey;
                args[Config.BOUND_SQL_INDEX] = newBoundSql;
            }

            args[Config.MAPPED_STATEMENT_INDEX] = newMs;
        }

        private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
            MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
            builder.resource(ms.getResource());
            builder.fetchSize(ms.getFetchSize());
            builder.statementType(ms.getStatementType());
            builder.keyGenerator(ms.getKeyGenerator());
            if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
                builder.keyProperty(ms.getKeyProperties()[0]);
            }
            builder.timeout(ms.getTimeout());
            builder.parameterMap(ms.getParameterMap());
            builder.resultMaps(ms.getResultMaps());
            builder.resultSetType(ms.getResultSetType());
            builder.cache(ms.getCache());
            builder.flushCacheRequired(ms.isFlushCacheRequired());
            builder.useCache(ms.isUseCache());
            return builder.build();
        }

        private static class BoundSqlSqlSource implements SqlSource {

            private BoundSql boundSql;

            public BoundSqlSqlSource(BoundSql boundSql) {
                this.boundSql = boundSql;
            }

            @Override
            public BoundSql getBoundSql(Object parameterObject) {
                return boundSql;
            }
        }

        private boolean needRewrite(Insert insert) {
            return checkConfiguration(insert.getTable().getName());
        }

        private boolean needRewrite(Update updateStatement) {
            List<Table> tables = updateStatement.getTables();
            //暂时不支持update多个table的情况
            if (tables.size() > 1) {
                return false;
            }
            return checkConfiguration(tables.get(0).getName());
        }

        private boolean needRewrite(Select selectStatement) {
            return true;
        }

        /**
         * 检查table name是否在include和exclude列表中
         * <p>如果在include列表中，则需要重写sql</p>
         * <p>如果在exclude列表中，则无需重写sql</p>
         * <p>include的优先级高于exclude</p>
         * <p>默认所有table都需要重写sql</p>
         *
         * @param table
         * @return true需要重写，false无需重写
         */
        private boolean checkConfiguration(String table) {
            /**
             * 当include和exclude都没设置时，默认所有table都要重写
             */
            if (CollectionUtils.isEmpty(includedTables) && CollectionUtils.isEmpty(excludedTables)) {
                return true;
            }
            /**
             * 当include不为空时，忽略exclude配置
             */
            if (CollectionUtils.isNotEmpty(includedTables)) {
                return includedTables.contains(table);
            }
            /**
             * include为空，exclude不为空的情况，exclude中的table无需重写
             */
            return !excludedTables.contains(table);
        }

        public List<String> getIncludedTables() {
            return includedTables;
        }

        public void setIncludedTables(List<String> includedTables) {
            this.includedTables = includedTables;
        }

        public List<String> getExcludedTables() {
            return excludedTables;
        }

        public void setExcludedTables(List<String> excludedTables) {
            this.excludedTables = excludedTables;
        }

        public String getTenantColumnName() {
            return tenantColumnName;
        }

        public void setTenantColumnName(String tenantColumnName) {
            this.tenantColumnName = tenantColumnName;
        }
    }

    private boolean isEnabled() {
        return enable;
    }
}
