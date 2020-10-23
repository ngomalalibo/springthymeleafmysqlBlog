package com.cms.blog.controller;

import com.cms.blog.entity.Admin;
import com.cms.blog.entity.Category;
import com.cms.blog.service.CategoryService;
import com.cms.blog.service.CommentService;
import com.cms.blog.util.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Controller
public class CategoryController
{
    private final CategoryService categoryService;
    private final CommentService commentService;
    
    Messages messages;
    
    public CategoryController(CategoryService categoryService, CommentService commentService, Messages messages)
    {
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.messages = messages;
    }
    
    @GetMapping("/categories")
    public String showCatgories(Model model, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        model.addAttribute("category", new Category());
        
        int noOfComments = commentService.getAllUnapprovedComments().size();
        
        model.addAttribute("noOfComments", noOfComments);
        model.addAttribute("categories", categoryService.findAll());
        
        return "categories";
    }
    
    
    @PostMapping("/categories")
    public String saveOrUpdate(@Valid @ModelAttribute Category category, BindingResult bindingResult, HttpSession session, Model model)
    {
        Admin currentUser = (Admin) session.getAttribute("currentUser");
        if (currentUser == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        //resets the form to this object
        model.addAttribute("category", new Category());
        
        String message = null;
        
        if (bindingResult.hasErrors())
        {
            StringBuilder errors = new StringBuilder("Something went wrong! <br>");
            bindingResult.getAllErrors().forEach(objectError ->
                                                 {
                                                     log.debug(objectError.toString());
                                                     errors.append(objectError.toString()).append(" <br>");
                                                 });
            session.setAttribute("message", messages.getMessages(errors.toString(), "error"));
        }
        else
        {
            category.setCreatorName(currentUser.getUsername());
            category.setDatetime(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss a Z")));
            Category saved = categoryService.save(category);
            if (!Objects.isNull(saved))
            {
                session.setAttribute("message", messages.getMessages("Category added successfully", "success"));
            }
        }
        
        model.addAttribute("noOfComments", commentService.getAllUnapprovedComments().size());
        model.addAttribute("categories", categoryService.findAll());
        
        return "categories";
    }
    
    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id, Model model, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        //resets the form to this object
        model.addAttribute("message", messages.successMessages(session));
        
        categoryService.deleteById(id);
        
        
        Category cat = categoryService.findById(id);
        if (Objects.isNull(cat))
        {
            session.setAttribute("message", messages.getMessages("Category deleted successfully", "success"));
        }
        else
        {
            session.setAttribute("message", messages.getMessages("Something went wrong !", "error"));
        }
        
        return "redirect:/categories";
    }
    
    @GetMapping("/categories/show")
    public String getCategories(Model model, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        int noOfComments = commentService.getAllUnapprovedComments().size();
        
        model.addAttribute("noOfComments", noOfComments);
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }
    
    
}
