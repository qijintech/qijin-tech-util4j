package tech.qijin.util4j.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        //记录影响行数
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        switch (sqlCommandType) {
            case UPDATE:
            case DELETE:
            case INSERT:
                int affectedRows = Integer.valueOf(Integer.parseInt(result.toString()));
                LOGGER.info(LogFormat.builder().put("sql", boundSql.getSql())
                        .put("affect rows", affectedRows)
                        .put("time cost", String.format("%d %s", end - start, "ms"))
                        .build());
                break;
            case SELECT:
                LOGGER.info(LogFormat.builder().put("sql", boundSql.getSql())
                        .put("time cost", String.format("%d %s", end - start, "ms"))
                        .build());
                break;
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
