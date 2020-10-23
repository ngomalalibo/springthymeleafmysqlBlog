package com.cms.blog.service;

import com.cms.blog.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService
{
    /*getAll (orderby date and id), addCategory, updateCategory, deleteCategory*/
    List<Category> findAll();
    
    Category findById(Integer id);
    
    Category save(Category entity);
    
    Category update(Category entity);
    
    void deleteById(Integer var1);
    
    boolean existsById(Integer var1);
    
    long count();
}
