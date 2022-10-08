//package com.xu.redis;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.lettuce.core.ReadFrom;
//import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//@Configuration
//public class RedisTemplateCompoment {
////    @Bean
////    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
////        RedisTemplate<Object, Object> template = new RedisTemplate<>();
////        template.setConnectionFactory(connectionFactory);
////
////        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
////        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
////
////        ObjectMapper mapper = new ObjectMapper();
////        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
////        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
////        serializer.setObjectMapper(mapper);
////
////        template.setValueSerializer(serializer);
////        //使用StringRedisSerializer来序列化和反序列化redis的key值
////        template.setKeySerializer(new StringRedisSerializer());
////        template.afterPropertiesSet();
////        return template;
////    }
//
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        //Use Jackson 2Json RedisSerializer to serialize and deserialize the value of redis (default JDK serialization)
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        //将类名称序列化到json串中
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        //设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        //Use String RedisSerializer to serialize and deserialize the key value of redis
//        RedisSerializer redisSerializer = new StringRedisSerializer();
//        //key
//        redisTemplate.setKeySerializer(redisSerializer);
//        redisTemplate.setHashKeySerializer(redisSerializer);
//        //value
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//
//    }
//
////@Configuration
////public class RedisConfig {
////    @Bean
////    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {
////        RedisTemplate<String,Object> template = new RedisTemplate<>();
////        template.setConnectionFactory(factory);
////        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
////        ObjectMapper om = new ObjectMapper();
////        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
////        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
////        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////
////        jackson2JsonRedisSerializer.setObjectMapper(om);
////        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
////        template.setKeySerializer(stringRedisSerializer);
////        template.setHashKeySerializer(stringRedisSerializer);
////        template.setValueSerializer(jackson2JsonRedisSerializer);
////        template.setHashValueSerializer(jackson2JsonRedisSerializer);
////        template.afterPropertiesSet();
////        return template;
//
//
////            RedisTemplate<String, Object> template = new RedisTemplate<>();
////            template.setConnectionFactory(factory);
////
////            //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
////            Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
////
////            ObjectMapper mapper = new ObjectMapper();
////            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
////            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
////            serializer.setObjectMapper(mapper);
////
////            template.setValueSerializer(serializer);
////            //使用StringRedisSerializer来序列化和反序列化redis的key值
////            template.setKeySerializer(new StringRedisSerializer());
////            template.afterPropertiesSet();
////            return template;
////
////
////    }
////}
//
//}
