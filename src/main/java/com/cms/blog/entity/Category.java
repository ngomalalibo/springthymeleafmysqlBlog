package com.cms.blog.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "category")
public class Category implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "datetime")
    private String datetime;
    @NotBlank
    //@Pattern(regexp = "/^[A-Za-z. ]*$/", message = "Only Letters and white spaces are allowed")
    @Column(name = "name")
    private String categoryname;
    @Column(name = "creatorname")
    private String creatorName;
    
    @Transient
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
