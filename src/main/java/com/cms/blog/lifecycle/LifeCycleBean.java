package com.cms.blog.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Slf4j
@Component
public class LifeCycleBean implements InitializingBean, DisposableBean, BeanNameAware,
        BeanFactoryAware
{
    
    public LifeCycleBean()
    {
        log.info("## I'm in the LifeCycleBean Constructor");
    }
    
    @Override
    public void destroy() throws Exception
    {
        log.info("## The Lifecycle bean has been terminated");
        
    }
    
    @Override
    public void afterPropertiesSet() throws Exception
    {
        log.info("## The LifeCycleBean has its properties set!");
        
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException
    {
        log.info("## Bean Factory has been set");
    }
    
    @Override
    public void setBeanName(String name)
    {
        log.info("## My Bean Name is: " + name);
        
    }
    
    //Bean specific
    @PostConstruct
    public void postConstruct()
    {
        log.info("## Bean Post Construct method has been called");
    }
    
    //Bean specific
    @PreDestroy
    public void preDestroy()
    {
        log.info("## Bean Pre destroy method has been called");
    }
    
    
    
    @EventListener(classes = {ContextStartedEvent.class, ContextStoppedEvent.class/*, ContextClosedEvent.class, ContextRefreshedEvent.class*/})
    public void handleMultipleEvents()
    {
        log.info("Multi-event listener invoked");
    }
}

