package com.cms.blog.repository;

import com.cms.blog.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>
{
    @Query(value = "select * from admins a where a.username=:username and a.password=:password LIMIT 1", nativeQuery = true)
    Admin login(@Param("username") String username, @Param("password") String password);
}
