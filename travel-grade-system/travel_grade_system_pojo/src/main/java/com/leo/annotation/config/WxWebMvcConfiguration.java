package com.leo.annotation.config;

import com.leo.annotation.support.LoginUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 自定义配置类，将自定义的参数解析器进行配置
 */
@Configuration //告诉spring该类为配置类
public class WxWebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserHandlerMethodArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        将本地文件夹设为服务器资源
        registry.addResourceHandler("/wx")
                .addResourceLocations("file:E:/File/travel-grade/");
    }
}
