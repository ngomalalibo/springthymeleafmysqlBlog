package com.cms.blog.repository;

import com.cms.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>
{
    @Query(value = "select * from comments c where c.status='OFF' order by c.datetime desc", nativeQuery = true)
    List<Comment> getAllUnapprovedComments();
    
    @Query(value = "select * from comments c where c.postid=:id and status='ON' order by c.datetime", nativeQuery = true)
    List<Comment> getApprovedCommentsByPost(@Param("id") Integer id);
    
    @Query(value = "select * from comments c where c.postid=:id and status='OFF' order by c.datetime", nativeQuery = true)
    List<Comment> getUnapprovedCommentsByPost(@Param("id") Integer id);
    
    @Query(value = "select * from comments c where c.status='ON' order by c.datetime", nativeQuery = true)
    List<Comment> getAllApprovedComments();
}
