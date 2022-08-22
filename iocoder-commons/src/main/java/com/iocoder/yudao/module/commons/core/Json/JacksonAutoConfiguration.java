//package com.iocoder.yudao.module.commons.core.Json;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import com.iocoder.yudao.module.commons.utils.json.JsonUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
///**
// * @author wu kai
// * @since 2022/8/19
// */
//@Slf4j
//@Component
//public class JacksonAutoConfiguration {
//    @Bean
//    public BeanPostProcessor objectMapperBeanPostProcessor() {
//        return new BeanPostProcessor() {
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                if (!(bean instanceof ObjectMapper)) {
//                    return bean;
//                }
//                ObjectMapper objectMapper = (ObjectMapper) bean;
//                SimpleModule simpleModule = new SimpleModule();
//                simpleModule
//                .addSerializer(Long.class, ToStringSerializer.instance)
//                    .addSerializer(Long.TYPE, ToStringSerializer.instance)
//                        .addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE)
//                        .addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
//
//                objectMapper.registerModules(simpleModule);
//
//                JsonUtils.init(objectMapper);
//                log.info("初始化 jackson 自动配置");
//                return bean;
//            }
//        };
//    }
//}
