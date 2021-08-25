package com.zjc.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
     @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry){

     }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
         //次路径由springsecurity 提供
         registry.addViewController("/").setViewName("redirect:/login");
    }
}
