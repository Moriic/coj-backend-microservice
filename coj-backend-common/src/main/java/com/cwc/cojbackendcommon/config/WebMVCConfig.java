package com.cwc.cojbackendcommon.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwc.cojbackendcommon.interceptor.JwtTokenUserInterceptor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * WebMVC相关配置
 */

@Configuration
public class WebMVCConfig extends WebMvcConfigurationSupport {

    @Resource
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    /**
     * 配置jwt拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenUserInterceptor);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = converter.getObjectMapper();

        // Custom serializer to handle Long to String conversion, excluding Page objects
        JsonSerializer<Long> longToStringSerializer = new JsonSerializer<Long>() {
            @Override
            public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                Object currentObject = gen.getCurrentValue();
                if (!(currentObject instanceof Page)) {
                    gen.writeString(value.toString());
                } else {
                    gen.writeNumber(value);
                }
            }
        };

        // Custom module to register the custom serializer
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, longToStringSerializer);
        simpleModule.addSerializer(Long.TYPE, longToStringSerializer);

        // Register the module with the ObjectMapper
        objectMapper.registerModule(simpleModule);

        // Set the modified ObjectMapper back to the converter
        converter.setObjectMapper(objectMapper);

        // Add the customized converter at the beginning of the converters list
        converters.add(0, converter);
    }
}
