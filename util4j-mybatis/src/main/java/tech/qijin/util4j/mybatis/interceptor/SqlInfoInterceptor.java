package tech.qijin.util4j.mybatis.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tech.qijin.util4j.lang.constant.EnumValue;
import tech.qijin.util4j.utils.LogFormat;

/**
 * 打印sql执行信息
 * <p>
 * <li>执行时间</li>
 * <li>sql语句</li>
 * <li>affect rows</li></li>
 * </p>
 *
 * @author michealyang
 * @date 2018/11/11
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Intercepts(value = {
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
                        CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class SqlInfoInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger("MyBatis");
    private static ThreadLocal<SimpleDateFormat> dateTimeFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[Config.MAPPED_STATEMENT_INDEX];
        Object parameter = invocation.getArgs()[Config.PARAMETER_INDEX];
        Configuration configuration = mappedStatement.getConfiguration();
        Object target = invocation.getTarget();
        StatementHandler handler = configuration.newStatementHandler((Executor) target, mappedStatement,
                parameter, RowBounds.DEFAULT, null, null);
        //记录SQL
        BoundSql boundSql = handler.getBoundSql();
        //记录耗时
        long start = System.currentTimeMillis();
        //执行真正的方法
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        String sql = boundSql.getSql();
        //记录影响行数
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        switch (sqlCommandType) {
            case UPDATE:
            case DELETE:
            case INSERT:
                int affectedRows = Integer.valueOf(Integer.parseInt(result.toString()));
                LOGGER.info(LogFormat.builder()
                        .message(sqlCommandType.name().toLowerCase() + " dml")
                        .put("sql", readableSql(sql, parseParameter(boundSql, parameter, configuration)))
                        .put("affect rows", affectedRows)
                        .put("time cost", String.format("%d %s", end - start, "ms"))
                        .build());
                break;
            case SELECT:
                LOGGER.info(LogFormat.builder()
                        .message(sqlCommandType.name().toLowerCase() + " dml")
                        .put("sql", readableSql(sql, parseParameter(boundSql, parameter, configuration)))
                        .put("time cost", String.format("%d %s", end - start, "ms"))
                        .build());
                break;
        }
        return result;
    }

    /**
     * 将sql中的?修改成对应的值
     *
     * @param sql
     * @param params
     * @return
     */
    private String readableSql(String sql, String params) {
        if (StringUtils.isBlank(sql) || StringUtils.isBlank(params)) return sql;
        String[] paramArr = params.split(",");
        if (paramArr.length <= 0) return sql;
        for (String param : paramArr) {
            sql = StringUtils.replace(sql, "?", param, 1);
        }
        return StringUtils.replace(sql, "\n", " ");
    }

    private String parseParameter(BoundSql boundSql, Object parameterObject, Configuration configuration) {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        StringBuilder sb = new StringBuilder();

        if (parameterMappings != null) {
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    //  参数值
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    //  获取参数名称
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 获取参数值
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        // 如果是单个值则直接赋值
                        value = parameterObject;
                    } else {
                        value = metaObject == null ? null :
                                metaObject.hasGetter(propertyName) ? metaObject.getValue(propertyName) : null;
                    }

                    if (null == value) {
                        continue;
                    }

                    if (value instanceof Number) {
                        sb.append(String.valueOf(value)).append(",");
                    } else {
                        if (value instanceof Date) {
                            sb.append(dateTimeFormatter.get().format((Date) value)).append(",");
                        } else if (value instanceof String) {
                            sb.append(value).append(",");
                        } else if (value instanceof EnumValue) {
                            sb.append(((EnumValue) value).value()).append(",");
                        } else if (value instanceof Boolean) {
                            sb.append((Boolean) value).append(",");
                        }
                    }
                }
            }
        }

        String ret = sb.toString();
        if (ret.endsWith(",")) {
            ret = ret.substring(0, ret.length() - 1);
        }

        return ret;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
