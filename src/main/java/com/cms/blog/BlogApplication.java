package com.cms.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BlogApplication extends SpringBootServletInitializer
{
    
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder)
    {
        return builder.sources(BlogApplication.class);
    }
    
    public static void main(String[] args)
    {
        ConfigurableApplicationContext run = SpringApplication.run(BlogApplication.class, args);
        /*String[] beanDefinitionNames = run.getBeanDefinitionNames();
        System.out.println("Beans: " + Arrays.stream(beanDefinitionNames).collect(Collectors.joining(", ", "[", "]")));*/
        
    }
    
    
    
    /*@Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder)
    {
        return builder.sources(BlogApplication.class);
    }
    
    public static void main(String[] args)
    {
        SpringApplication.run(BlogApplication.class, args);
    }*/
}
