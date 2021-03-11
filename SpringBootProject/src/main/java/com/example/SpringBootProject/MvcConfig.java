package com.example.SpringBootProject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path imageCategoryDir = Paths.get("./src/main/resources/static/images/category");
        String imageCategoryPath = imageCategoryDir.toFile().getAbsolutePath();
        registry.addResourceHandler("../images/category/**").addResourceLocations("file:/" + imageCategoryPath + "/");

        Path imageProductDir = Paths.get("./src/main/resources/static/images/product");
        String imageProductPath = imageCategoryDir.toFile().getAbsolutePath();
        registry.addResourceHandler("../images/product/**").addResourceLocations("file:/" + imageProductPath + "/");
    }
}
