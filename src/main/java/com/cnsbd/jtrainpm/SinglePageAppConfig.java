package com.cnsbd.jtrainpm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class SinglePageAppConfig implements WebMvcConfigurer {

    @Value("${spa.resource-path}")
    private String resPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(resPath + "/**")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        if (resourcePath.matches("^api/.*")) throw new IOException("Page not found");
                        Resource requestedRes = location.createRelative(resourcePath);
                        if (requestedRes.exists() && requestedRes.isReadable()) {
                            return requestedRes;
                        }
                        return location.createRelative("/index.html");
                    }
                });
    }
}
