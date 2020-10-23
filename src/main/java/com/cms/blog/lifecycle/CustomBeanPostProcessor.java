package com.cms.blog.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomBeanPostProcessor implements BeanPostProcessor
{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
    {
        //put code here that will be run before bean initialization
        if (bean instanceof LifeCycleBean)
        {
            beforeInit();
        }
        
        return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
    {
        //put code here that will be run after bean initialization
        if (bean instanceof LifeCycleBean)
        {
            afterInit();
        }
        
        return bean;
    }
    
    public void beforeInit()
    {
        
        log.info("## - Before Init - Called by Bean Post Processor");
    }
    
    public void afterInit()
    {
        log.info("## - After init called by Bean Post Processor");
        
    }
}
