package com.cms.blog.controller;

import com.cms.blog.exceptions.NotFoundException;
import com.cms.blog.exceptions.NumberFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ExceptionController
{
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception)
    {
        
        log.error("Handling not found exception");
        log.error(exception.getMessage());
        
        ModelAndView modelAndView = new ModelAndView();
        
        modelAndView.setViewName("error");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("errorCode", HttpStatus.NOT_FOUND.toString());
        
        return modelAndView;
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception)
    {
        
        log.error("Handling not found exception");
        log.error(exception.getMessage());
        
        ModelAndView modelAndView = new ModelAndView();
        
        modelAndView.setViewName("error");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("errorCode", HttpStatus.BAD_REQUEST.toString());
        
        return modelAndView;
    }
}
