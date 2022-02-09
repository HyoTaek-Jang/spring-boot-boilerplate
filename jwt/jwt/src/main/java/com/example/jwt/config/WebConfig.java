package com.example.jwt.config;

import com.example.jwt.util.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.LinkedList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor testInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     List<String> excludePaths = new LinkedList<>();
    //     excludePaths.add("/login");
    //     excludePaths.add("/");
    //     registry.addInterceptor(testInterceptor).addPathPatterns("/**")
    //             .excludePathPatterns(excludePaths);
    // }

}
