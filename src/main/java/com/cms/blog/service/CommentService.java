package com.cms.blog.service;

import com.cms.blog.entity.Category;
import com.cms.blog.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentService
{
    /*getApprovedCommentsByPost, getUnapprovedCommentsByPost, getAllUnapprovedComments,
    getAll, getApprovedCommentById, getUnapprovedCommentById, addComment, updateComments, deleteComment*/
    
    //getAllComments
    List<Comment> findAll();
    
    Comment findById(Integer id);
    
    Comment save(Comment entity);
    
    Comment update(Comment entity);
    
    void deleteById(Integer var1);
    
    boolean existsById(Integer var1);
    
    long count();
    
    List<Comment> getApprovedCommentsByPost(int id);
    
    List<Comment> getUnapprovedCommentsByPost(int id);
    
    List<Comment> getAllApprovedComments();
    
    List<Comment> getAllUnapprovedComments();
    
}
