package com.cms.blog.controller;

import com.cms.blog.entity.Comment;
import com.cms.blog.service.CommentService;
import com.cms.blog.util.Messages;
import com.cms.blog.util.PostAndDateShortenerComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class CommentController
{
    
    CommentService commentService;
    Messages messages;
    PostAndDateShortenerComment postAndDateShortenerComment;
    
    public CommentController(CommentService commentService, Messages messages, PostAndDateShortenerComment postAndDateShortenerComment)
    {
        this.commentService = commentService;
        this.messages = messages;
        this.postAndDateShortenerComment = postAndDateShortenerComment;
    }
    
    @GetMapping("/comment")
    public String getComments(Model model, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        model.addAttribute("noOfComments", commentService.getAllUnapprovedComments().size());
        model.addAttribute("approvedComments", postAndDateShortenerComment.shortenPostAndDateForView(commentService.getAllApprovedComments()));
        model.addAttribute("unapprovedComments", postAndDateShortenerComment.shortenPostAndDateForView(commentService.getAllUnapprovedComments()));
        
        return "comment";
    }
    
    @GetMapping("/comment/approve/{id}")
    public String approveComment(@PathVariable Integer id, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        Comment comment = commentService.findById(id);
        String message;
        
        comment.setApprovedBy("SYSTEM");
        comment.setStatus("ON");
        
        Comment saved = commentService.save(comment);
        if (Objects.isNull(saved))
        {
            message = messages.getMessages("Something went wrong. Try again later  !", "error");
        }
        else
        {
            message = messages.getMessages("Comment approved successfully", "success");
        }
        
        
        session.setAttribute("message", message);
        
        return "redirect:/comment";
    }
    
    @GetMapping("/comment/unapprove/{id}")
    public String unapproveComment(@PathVariable Integer id, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        String message;
        Comment comment = commentService.findById(id);
        comment.setStatus("OFF");
        
        Comment saved = commentService.save(comment);
        if (Objects.isNull(saved))
        {
            message = messages.getMessages("Something went wrong. Try again later  !", "error");
        }
        else
        {
            message = messages.getMessages("Comment unapproved successfully", "success");
        }
        
        session.setAttribute("message", message);
        
        return "redirect:/comment";
    }
    
    
    @GetMapping("/comment/delete/{id}")
    public String deletePost(@PathVariable Integer id, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        String view;
        
        commentService.deleteById(id);
        Comment byId = commentService.findById(id);
        
        if (Objects.isNull(byId))
        {
            session.setAttribute("message", messages.getMessages("Comment deleted Successfully !", "success"));
            view = "redirect:/comment";
        }
        else
        {
            session.setAttribute("message", messages.getMessages("Something went wrong. Try Again !", "error"));
            view = "redirect:/comment/delete/" + id;
        }
        
        return view;
    }
}
