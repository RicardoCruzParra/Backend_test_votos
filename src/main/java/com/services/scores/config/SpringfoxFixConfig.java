package com.services.scores.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SpringfoxFixConfig {

    @Bean
    public static WebMvcRequestHandlerProvider springfoxWebMvcRequestHandlerProvider(WebMvcRequestHandlerProvider provider) throws Exception {
        Field field = WebMvcRequestHandlerProvider.class.getDeclaredField("handlerMappings");
        field.setAccessible(true);
        List<?> mappings = (List<?>) field.get(provider);
        List<?> updatedMappings = mappings.stream()
                .filter(mapping -> mapping.getClass().getSimpleName().contains("RequestMappingHandlerMapping"))
                .collect(Collectors.toList());
        field.set(provider, updatedMappings);
        return provider;
    }
}

