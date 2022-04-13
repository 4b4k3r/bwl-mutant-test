package com.mx.bwl.genetic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class DataSourceConfiguration
{
    /**
     * This method return a connection properties model for redis connection
     * Properties are loaded before start by spring autoconfiguration in the spring context
     *
     * @return JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory()
    {
        return new JedisConnectionFactory();
    }

    /**
     * @param redisConnectionFactory is the configuration model which has redis connection properties
     * @return RedisTemplate<String, Object> which we can use to interact dynamically whit redis hashes
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
