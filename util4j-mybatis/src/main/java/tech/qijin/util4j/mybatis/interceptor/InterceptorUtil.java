package tech.qijin.util4j.mybatis.interceptor;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Invocation;
import tech.qijin.util4j.lang.constant.EnumValue;
import tech.qijin.util4j.web.pojo.EnvEnum;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author michealyang
 * @date 2018/11/28
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class InterceptorUtil {

    private static Field sqlField;

    private static final String WHERE_UPPER = "WHERE";
    private static final String WHERE_LOWER = "where";
    private static final String AND = "and";
    private static final String JOIN = "join";
    private static final String ORDER_BY = "order by";
    private static final String LIMIT = "limit";
    private static final String ENV = "env";
    private static final String VALID = "valid";

    static {
        try {
            sqlField = BoundSql.class.getDeclaredField("sql");
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("获取BoundSql属性sql失败", e);
        }
        sqlField.setAccessible(true);
    }

    /**
     * join语句不进行拼装
     *
     * @param sql
     * @return
     */
    public static boolean ignore(String sql) {
        String copy = new String(sql).toLowerCase();
        return copy.contains(JOIN);
    }

    public static String genNewSql(String sql, EnumValue value) {
        if (sql.contains(WHERE_UPPER)) {
            String[] arr = split(sql, WHERE_UPPER, false);
            List<String> newArr = Lists.newArrayList();
            newArr.add(arr[0].trim());
            newArr.add(WHERE_UPPER);
            newArr.add(envSql(sql, value));
            newArr.add(AND);
            newArr.add(validSql(sql));
            newArr.add(AND);
            newArr.add(arr[1].trim());
            return StringUtils.join(newArr, " ");
        } else if (sql.contains(WHERE_LOWER)) {
            String[] arr = split(sql, WHERE_LOWER, false);
            List<String> newArr = Lists.newArrayList();
            newArr.add(arr[0].trim());
            newArr.add(WHERE_LOWER);
            newArr.add(envSql(sql, value));
            newArr.add(AND);
            newArr.add(validSql(sql));
            newArr.add(AND);
            newArr.add(arr[1].trim());
            return StringUtils.join(newArr, " ");
        } else if (hasOrderBy(sql)) {
            String[] arr = split(sql, ORDER_BY, true);
            List<String> newArr = Lists.newArrayList();
            newArr.add(arr[0].trim());
            newArr.add(WHERE_LOWER);
            newArr.add(envSql(sql, value));
            newArr.add(AND);
            newArr.add(validSql(sql));
            newArr.add(ORDER_BY);
            newArr.add(arr[1].trim());
            return StringUtils.join(newArr, " ");
        } else if (hasLimit(sql) && !hasOrderBy(sql)) {
            String[] arr = split(sql, LIMIT, true);
            List<String> newArr = Lists.newArrayList();
            newArr.add(arr[0].trim());
            newArr.add(WHERE_LOWER);
            newArr.add(envSql(sql, value));
            newArr.add(AND);
            newArr.add(validSql(sql));
            newArr.add(LIMIT);
            newArr.add(arr[1].trim());
            return StringUtils.join(newArr, " ");
        } else {
            List<String> newArr = Lists.newArrayList();
            newArr.add(sql);
            newArr.add(WHERE_LOWER);
            newArr.add(AND);
            newArr.add(validSql(sql));
            return StringUtils.join(newArr, " ");
        }
    }

    private static String envSql(String sql, EnumValue value) {
        StringBuilder sb = new StringBuilder();
        return sb.append(ENV)
                .append("=")
                .append(value.value()).toString();
    }

    private static String validSql(String sql) {
        StringBuilder sb = new StringBuilder();
        return sb.append(VALID)
                .append("=")
                .append(1).toString();
    }

    private static String[] split(String sql, String separator, boolean toLowerCase) {
        if (toLowerCase) {
            String newSql = sql.toLowerCase();
            return newSql.split(separator);
        } else {
            return sql.split(separator);
        }
    }


    private static boolean hasOrderBy(String sql) {
        return sql.contains(ORDER_BY);
    }

    private static boolean hasLimit(String sql) {
        return sql.contains(LIMIT);
    }

    public static void newBoundSql(Invocation invocation,
                                   String sql,
                                   EnvEnum envEnum,
                                   BoundSql boundSql,
                                   MappedStatement mappedStatement) {
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(),
                InterceptorUtil.genNewSql(sql, envEnum),
                boundSql.getParameterMappings(),
                boundSql.getParameterObject());
        //因为上面的构造中(this.additionalParameters = new HashMap<String, Object>();)
        // additionalParameters被重新初始化了,需要单独赋值
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        // 把新的查询放到statement里
        MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
        invocation.getArgs()[Config.MAPPED_STATEMENT_INDEX] = newMs;
    }

    public static class BoundSqlSqlSource implements SqlSource {

        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    private static MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
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
}


