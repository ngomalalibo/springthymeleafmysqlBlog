package com.cms.blog.service;

import com.cms.blog.entity.Comment;
import com.cms.blog.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService
{
    
    private CommentRepository commentRepository;
    
    public CommentServiceImpl(CommentRepository commentRepository)
    {
        this.commentRepository = commentRepository;
    }
    
    @Override
    public List<Comment> findAll()
    {
        List<Comment> list = new ArrayList<>();
        commentRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
    
    @Override
    public Comment findById(Integer id)
    {
        
        Optional<Comment> optional = commentRepository.findById(id);
        
        if (optional.isEmpty())
        {
            return null;
        }
        
        return optional.get();
    }
    
    @Override
    public Comment save(Comment entity)
    {
        return commentRepository.save(entity);
    }
    
    @Override
    public Comment update(Comment entity)
    {
        return commentRepository.save(entity);
    }
    
    @Override
    public void deleteById(Integer var1)
    {
        commentRepository.deleteById(var1);
    }
    
    @Override
    public boolean existsById(Integer var1)
    {
        Optional<Comment> optional = commentRepository.findById(var1);
        
        if (optional.isEmpty())
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public long count()
    {
        return commentRepository.count();
    }
    
    @Override
    public List<Comment> getApprovedCommentsByPost(int id)
    {
        return commentRepository.getApprovedCommentsByPost(id);
    }
    
    @Override
    public List<Comment> getUnapprovedCommentsByPost(int id)
    {
        return commentRepository.getUnapprovedCommentsByPost(id);
    }
    
    @Override
    public List<Comment> getAllApprovedComments()
    {
        return commentRepository.getAllApprovedComments();
    }
    
    @Override
    public List<Comment> getAllUnapprovedComments()
    {
        return commentRepository.getAllUnapprovedComments();
    }
}
