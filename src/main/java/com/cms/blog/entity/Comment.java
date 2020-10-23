package com.cms.blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "comments")
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "datetime")
    private String datetime;
    private String name;
    private String email;
    private String comment;
    private String status;
    @Column(name = "postid")
    private Integer postId;
    @Column(name = "approvedby")
    private String approvedBy;
    
    @Transient
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
