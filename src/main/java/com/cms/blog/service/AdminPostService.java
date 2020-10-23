package com.cms.blog.service;

import com.cms.blog.entity.AdminPost;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPostService
{
    // place custom CRUD function specification here
    /*posts - getAll, getAllByCategory, getPage, getPost, searchPosts, addPost, updatePost, deletePost*/
    List<AdminPost> findAll();
    
    AdminPost findById(Integer id);
    
    List<AdminPost> findRange(int from, int to);
    
    List<AdminPost> findPage(int from);
    
    List<AdminPost> findByCategory(Integer catId);
    
    List<AdminPost> search(String search);
    
    AdminPost save(AdminPost entity);
    
    AdminPost update(AdminPost entity);
    
    void deleteById(Integer var1);
    
    boolean existsById(Integer var1);
    
    long count();
}
