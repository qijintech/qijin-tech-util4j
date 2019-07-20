package tech.qijin.util4j.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import tech.qijin.util4j.redis.RedisUtil;

/**
 * @author michealyang
 * @date 2019/3/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
@Import(RedisPropertiesConfiguration.class)
@ComponentScan("tech.qijin.util4j.redis")
public class RedisAutoConfiguration {

    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisStringTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate
                redisStringTemplate = new StringRedisTemplate(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        /**
         * 通用的序列化和反序列化设置
         * ObjectMapper类是Jackson库的主要类。它提供一些功能将转换成Java对象匹配JSON结构，反之亦然。
         */
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisStringTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisStringTemplate.afterPropertiesSet();
        return redisStringTemplate;
    }


    @Bean
    public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object>
                redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 注入封装RedisTemplate
     *
     * @return redisUtil
     * @throws
     * @Title: redisUtil
     * @autor lpl
     * @date 2017年12月21日
     */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> redisObjectTemplate) {
        RedisUtil redisUtil = new RedisUtil() {
        };
        redisUtil.setRedisStringTemplate(stringRedisTemplate);
        redisUtil.setRedisObjectTemplate(redisObjectTemplate);
        return redisUtil;
    }
}
