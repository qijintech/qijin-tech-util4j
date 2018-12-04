package tech.qijin.util4j.mybatis.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.qijin.util4j.trace.pojo.EnvEnum;
import tech.qijin.util4j.trace.util.EnvUtil;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 向数据库的table中默认插入env的值
 * <p>
 * env值的获取方式推荐使用ThreadLocal<br>
 * 这里使用
 * </p>
 *
 * @author michealyang
 * @date 2018/11/7
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
public class EnvInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvInterceptor.class);

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
        //sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        EnvEnum envEnum = EnvUtil.getEnv();
        String sql = boundSql.getSql();
        switch (sqlCommandType) {
            case INSERT:
                //插入操作时，自动插入env
                Field fieldEnv = parameter.getClass().getDeclaredField("env");
                fieldEnv.setAccessible(true);
                fieldEnv.set(parameter, envEnum);
                break;
            case UPDATE:
                InterceptorUtil.newBoundSql(invocation, sql, envEnum, boundSql, mappedStatement);
                break;
            case SELECT:
                InterceptorUtil.newBoundSql(invocation, sql, envEnum, boundSql, mappedStatement);
                break;
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
