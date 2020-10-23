package com.cms.blog.controller;

import com.cms.blog.entity.AdminPost;
import com.cms.blog.entity.Comment;
import com.cms.blog.exceptions.BadRequestException;
import com.cms.blog.exceptions.NotFoundException;
import com.cms.blog.service.AdminPostService;
import com.cms.blog.service.CategoryService;
import com.cms.blog.service.CommentService;
import com.cms.blog.util.Messages;
import com.cms.blog.util.PostAndDateShortenerAdminPost;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
public class FullPostController
{
    
    private AdminPostService adminPostService;
    private CommentService commentService;
    private CategoryService categoryService;
    
    Messages messages;
    
    PostAndDateShortenerAdminPost postAndDateShortenerAdminPost;
    
    public FullPostController(AdminPostService adminPostService, CommentService commentService, CategoryService categoryService, Messages messages, PostAndDateShortenerAdminPost postAndDateShortenerAdminPost)
    {
        this.adminPostService = adminPostService;
        this.commentService = commentService;
        this.categoryService = categoryService;
        this.messages = messages;
        this.postAndDateShortenerAdminPost = postAndDateShortenerAdminPost;
        
    }
    
    @PostMapping("/fullPost/comment/{id}")
    public String saveCommentForFullPost(@Valid @ModelAttribute("commentObj") Comment comment, @PathVariable(value = "id") String id, Model model, BindingResult bindingResult, HttpSession session)
    {
        //model.addAttribute("commentObj", new Comment());
        
        String message = null;
        if (bindingResult.hasErrors())
        {
            StringBuilder errors = new StringBuilder("Something went wrong! <br>");
            bindingResult.getAllErrors().forEach(objectError ->
                                                 {
                                                     log.debug(objectError.toString());
                                                     errors.append(objectError.toString()).append(" <br>");
                                                 });
            message = messages.getMessages(errors.toString(), "error");
        }
        else
        {
            comment.setStatus("OFF");
            comment.setPostId(Integer.valueOf(id));
            comment.setDatetime(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss a Z")));
            Comment saved = commentService.save(comment);
            if (!Objects.isNull(saved))
            {
                message = messages.getMessages("Thank you " + comment.getEmail() + " for your comment. It will be approved shortly", "success");
            }
        }
        
        session.setAttribute("message", message);
        
        
        return "redirect:/fullPost/" + id;
    }
    
    @GetMapping({"/fullPost/{id}"})
    public String getFullPost(
            @PathVariable(value = "id") String id, Model model)
    {
        if (!Strings.isNullOrEmpty(id))
        {
            if (!StringUtils.isNumeric(id))
            {
                throw new BadRequestException("Wrong ID ! Post not found for id: " + id);
            }
            
            int id1 = Integer.parseInt(id);
            AdminPost byId = adminPostService.findById(id1);
            if (byId == null)
            {
                throw new NotFoundException("Post not found for ID: " + id1);
            }
            model.addAttribute("fullPost", byId);
        }
        
        List<AdminPost> recentPosts = postAndDateShortenerAdminPost.shortenPostAndDateForView(adminPostService.findRange(0, 5));
        
        model.addAttribute("commentObj", new Comment());
        model.addAttribute("commentService", commentService);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("recentPosts", recentPosts);
        
        
        return "fullPost";
    }
    
    @GetMapping("/error")
    public String error(Exception exception, ModelAndView modelAndView)
    {
        modelAndView.setViewName("error");
        modelAndView.addObject("exception", exception.getMessage());
        modelAndView.addObject("errorCode", HttpStatus.NOT_FOUND.toString());
        return "error";
    }
    
    
}
