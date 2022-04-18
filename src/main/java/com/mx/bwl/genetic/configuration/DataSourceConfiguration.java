package com.mx.bwl.genetic.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Primary
@Log4j2
@Configuration
@EnableRedisRepositories
public class DataSourceConfiguration
{
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    /**
     * This method return a connection properties model for redis connection
     * Properties are loaded before start by spring autoconfiguration in the spring context
     *
     * @return JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(getRedisHost());
        configuration.setPort(getRedisPort());
        return new JedisConnectionFactory(configuration);
    }

    private String getRedisHost() throws IllegalArgumentException{
        if (redisHost== null || redisHost.isEmpty()){
            log.fatal("No se ha definido el host de redis por properties file, intentando obtener del env");
            String redisHostByEnv = System.getenv("SPRING_REDIS_HOST");

            if (redisHostByEnv== null || redisHostByEnv.isEmpty()){
                throw new IllegalArgumentException("No se ha definido el host de redis del env, no es posible continuar");
            }

            return redisHostByEnv;
        }

        return redisHost;
    }

    private Integer getRedisPort() throws IllegalArgumentException{
        if (redisPort== null){
            log.fatal("No se ha definido el puerto de redis por properties file, intentando obtener del env");
            String redisPortByEnv = System.getenv("SPRING_REDIS_PORT");

            if (redisPortByEnv== null || redisPortByEnv.isEmpty()){
                throw new IllegalArgumentException("No se ha definido el puerto de redis del env, no es posible continuar");
            }

            return Integer.valueOf(redisPortByEnv);
        }

        return redisPort;
    }

    /**
     * @param redisConnectionFactory is the configuration model which has redis connection properties
     * @return RedisTemplate<String, Object> which we can use to interact dynamically whit redis hashes
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired JedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
