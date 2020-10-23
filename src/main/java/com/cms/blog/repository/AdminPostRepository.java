package com.cms.blog.repository;

import com.cms.blog.entity.AdminPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPostRepository extends JpaRepository<AdminPost, Integer>
{
    @Query(value = "select * from admin_posts a where a.datetime like %:search% or a.title like %:search1% or a.category like %:search2% or a.post like %:search3% order by a.datetime desc", nativeQuery = true)
    List<AdminPost> search(@Param("search") String search, @Param("search1") String search1, @Param("search2") String search2, @Param("search3") String search3);
    
    // List<AdminPost> findByNameContainingOrDatetimeContainingOrTitleContainingOrCategoryContainingOrPostContaining(String datetime, String title, String category, String post);
    
    @Query(value = "select * from admin_posts a ORDER BY a.datetime desc LIMIT :from,:to", nativeQuery = true)
    List<AdminPost> findRange(@Param("from") int from, @Param("to") int to);
    
    @Query(value = "select * from admin_posts a ORDER BY a.datatime desc LIMIT :from,3", nativeQuery = true)
    List<AdminPost> findPage(@Param("from") int from);
    
    @Query(value = "select * from admin_posts a where a.category=:categoryId order by a.datetime desc", nativeQuery = true)
    List<AdminPost> findByCategory(@Param("categoryId") Integer catId);
    
    @Modifying
    @Query(value = "select * from admin_posts a order by a.datetime desc", nativeQuery = true)
    List<AdminPost> findAll();
}
