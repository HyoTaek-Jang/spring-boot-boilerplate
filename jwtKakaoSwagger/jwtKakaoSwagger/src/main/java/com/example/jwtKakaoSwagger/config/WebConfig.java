package com.example.jwtKakaoSwagger.config;

import com.example.jwtKakaoSwagger.util.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.LinkedList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = new LinkedList<>();
//        excludePaths.add("/");
//        excludePaths.add("/swagger-ui/#");
//        excludePaths.add("/swagger-ui/");
//        excludePaths.add("/index.html");
//        excludePaths.add("/login");
        registry.addInterceptor(jwtTokenInterceptor).addPathPatterns("/test")
                .excludePathPatterns(excludePaths);
    }

}
