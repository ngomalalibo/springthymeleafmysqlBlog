package com.cms.blog.service;

import com.cms.blog.entity.Admin;
import com.cms.blog.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminService
{
    Admin login(String username, String password);
    
    List<Admin> findAll();
    
    Admin findById(Integer id);
    
    Admin save(Admin entity);
    
    Admin update(Admin entity);
    
    void deleteById(Integer var1);
    
    boolean existsById(Integer var1);
    
    long count();
}
