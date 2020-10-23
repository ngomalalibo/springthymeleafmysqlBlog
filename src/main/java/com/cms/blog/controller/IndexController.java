package com.cms.blog.controller;

import com.cms.blog.entity.AdminPost;
import com.cms.blog.service.AdminPostService;
import com.cms.blog.service.CategoryService;
import com.cms.blog.service.CommentService;
import com.cms.blog.util.Messages;
import com.cms.blog.util.PostAndDateShortenerAdminPost;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class IndexController
{
    private AdminPostService adminPostService;
    private CommentService commentService;
    private CategoryService categoryService;
    
    PostAndDateShortenerAdminPost postAndDateShortenerAdminPost;
    
    Messages messages;
    
    public IndexController(AdminPostService adminPostService, CommentService commentService, CategoryService categoryService, Messages messages, PostAndDateShortenerAdminPost postAndDateShortenerAdminPost)
    {
        this.adminPostService = adminPostService;
        this.commentService = commentService;
        this.categoryService = categoryService;
        this.messages = messages;
        this.postAndDateShortenerAdminPost = postAndDateShortenerAdminPost;
    }
    
    /*@GetMapping({"/index", "", "/"})
    public String blogHome(Model model)
    {
        List<AdminPost> result;
        result = adminPostService.findRange(0, 3);
        
        result = processPostList(result);
        
        model.addAttribute("resultList", result);
        
        model.addAttribute("commentService", commentService);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("recentPosts", adminPostService.findRange(0, 5));
        
        return "index";
    }*/
    
    @GetMapping({"/index", "", "/"})
    public String getPostBySearch(@RequestParam(value = "search", required = false) String search, Model model)
    {
        List<AdminPost> result = new ArrayList<>();
        if (Strings.isNullOrEmpty(search))
        {
            result = adminPostService.findRange(0, 3);
        }
        else
        {
            result = adminPostService.search(search);
            
        }
        
        result = postAndDateShortenerAdminPost.shortenPostAndDateForView(result);
        
        model.addAttribute("resultList", result);
        
        model.addAttribute("commentService", commentService);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("recentPosts", result);
        
        return "index";
    }
    
    @GetMapping({"/{category}/index"})
    public String getPostByCategory(@PathVariable(value = "category", required = false) String category, Model model)
    {
        List<AdminPost> result = new ArrayList<>();
        
        if (!Strings.isNullOrEmpty(category))
        {
            if (StringUtils.isNumeric(category))
            {
                result = adminPostService.findByCategory(Integer.valueOf(category));
            }
        }
        else
        {
            result = adminPostService.findRange(0, 3);
        }
        
        result = postAndDateShortenerAdminPost.shortenPostAndDateForView(result);
        
        model.addAttribute("resultList", result);
        
        model.addAttribute("commentService", commentService);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("recentPosts", result);
        
        return "index";
    }
    
    @GetMapping({"/index/page/{page}"})
    public String getPostByPage(@PathVariable(value = "page", required = false) String page, Model model)
    {
        List<AdminPost> result = new ArrayList<>();
        int count = 1;
        
        if (Strings.isNullOrEmpty(page))
        {
            if (StringUtils.isNumeric(page))
            {
                result = adminPostService.findPage(Integer.parseInt(page));
                if (Objects.isNull(result))
                {
                    result = adminPostService.findAll();
                }
                count = Long.valueOf(adminPostService.count()).intValue();
            }
        }
        else
        {
            result = adminPostService.findAll();
        }
        
        model.addAttribute("count", count);
        model.addAttribute("noOfPages", Math.ceil(count / 3));
        
        result = postAndDateShortenerAdminPost.shortenPostAndDateForView(result);
        
        model.addAttribute("resultList", result);
        
        model.addAttribute("commentService", commentService);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("recentPosts", adminPostService.findRange(0, 5));
        
        return "index";
    }
}
