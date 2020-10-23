package com.cms.blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "admin_posts")
public class AdminPost
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String datetime;
    private String title;
    private String category;
    private String author;
    private String image;
    private String post;
    
    @Transient
    MultipartFile imageMPF;
    
    @Transient
    private Map<String, Object> additionalProperties = new HashMap<>();
}
