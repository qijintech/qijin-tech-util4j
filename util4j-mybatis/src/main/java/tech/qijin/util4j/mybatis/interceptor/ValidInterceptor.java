package tech.qijin.util4j.mybatis.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import tech.qijin.util4j.trace.util.EnvUtil;

/**
 * @author michealyang
 * @date 2018/12/9
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
public class ValidInterceptor extends AbstractTenantInterceptor<Integer> {
    @Override
    public Integer getTenantValue() {
        return 1;
    }
}
