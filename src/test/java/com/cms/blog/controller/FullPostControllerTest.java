package com.cms.blog.controller;

import com.cms.blog.exceptions.NotFoundException;
import com.cms.blog.service.AdminPostService;
import com.cms.blog.service.CategoryService;
import com.cms.blog.service.CommentService;
import com.cms.blog.util.Messages;
import com.cms.blog.util.PostAndDateShortenerAdminPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// @AutoConfigureMockMvc
class FullPostControllerTest
{
    @Mock
    private AdminPostService adminPostService;
    @Mock
    private CommentService commentService;
    @Mock
    private CategoryService categoryService;
    @Mock
    Messages messages;
    @Mock
    PostAndDateShortenerAdminPost postAndDateShortenerAdminPost;
    
    private FullPostController fullPostController;
    
    
    MockMvc mockMvc;
    
    @BeforeEach
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        
        fullPostController = new FullPostController(adminPostService, commentService, categoryService, messages, postAndDateShortenerAdminPost);
        mockMvc = MockMvcBuilders.standaloneSetup(fullPostController)
                                 .setControllerAdvice(new ExceptionController()).build();
    }
    
    @Test
    void homeNotFoundEx() throws Exception
    {
        when(adminPostService.findById(anyInt())).thenThrow(NotFoundException.class);
        
        mockMvc.perform(get("/fullPost/1"))
               .andExpect(status().isNotFound())
               .andExpect(view().name("error"));
    }
}