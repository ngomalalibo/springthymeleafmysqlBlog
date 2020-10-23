package com.cms.blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "admins")
public class Admin
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String datetime;
    //@Pattern(regexp = "/[a-zA-Z0-9._-]{3,}@[a-zA-Z0-9._-]{3,}[.]{1}[a-zA-Z0-9._-]{2,}/", message = "Invalid email format")
    private String username;
    private String password;
    
    @Transient
    private String confirmPassword;
    
    private String addedby;
    
    @Transient
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
