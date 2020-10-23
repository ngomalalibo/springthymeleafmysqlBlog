package com.cms.blog.controller;

import com.cms.blog.entity.Admin;
import com.cms.blog.service.AdminService;
import com.cms.blog.service.CommentService;
import com.cms.blog.util.Messages;
import com.cms.blog.util.PostAndDateShortenerAdmin;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Controller
public class AdminController
{
    AdminService adminService;
    CommentService commentService;
    Messages messages;
    PostAndDateShortenerAdmin postAndDateShortenerAdmin;
    
    public AdminController(AdminService adminService, Messages messages, CommentService commentService, PostAndDateShortenerAdmin postAndDateShortenerAdmin)
    {
        this.adminService = adminService;
        this.messages = messages;
        this.commentService = commentService;
        this.postAndDateShortenerAdmin = postAndDateShortenerAdmin;
    }
    
    @GetMapping({"/admin", "admin"})
    public String admins(Model model, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        model.addAttribute("adminObj", new Admin());
        
        model.addAttribute("noOfComments", commentService.getAllUnapprovedComments().size());
        model.addAttribute("admins", postAndDateShortenerAdmin.shortenPostAndDateForView(adminService.findAll()));
        return "admin";
    }
    
    @GetMapping("/admin/delete/{id}")
    public String deletePost(@PathVariable Integer id, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        String view;
        
        adminService.deleteById(id);
        Admin byId = adminService.findById(id);
        
        if (Objects.isNull(byId))
        {
            session.setAttribute("message", messages.getMessages("Admin deleted Successfully !", "success"));
            view = "redirect:/admin";
        }
        else
        {
            session.setAttribute("message", messages.getMessages("Something went wrong. Try Again !", "error"));
            view = "redirect:/admin/delete/" + id;
        }
        
        return view;
    }
    
    @PostMapping("/admin")
    public String saveOrUpdateAdmin(@ModelAttribute Admin adminObj, Model model, HttpSession session)
    {
        Admin currentUser = (Admin) session.getAttribute("currentUser");
        if (currentUser == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        adminObj.setAddedby(currentUser.getAddedby());
        adminObj.setDatetime(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss a Z")));
        model.addAttribute("adminObj", adminObj);
        Admin saved = adminService.save(adminObj);
        if (Objects.isNull(saved))
        {
            session.setAttribute("message", messages.getMessages("Something went wrong !", "error"));
        }
        else
        {
            if (!Objects.isNull(adminObj.getId()))
            {
                //update
                session.setAttribute("message", messages.getMessages("Admin added successfully !", "success"));
            }
            else
            {
                //new
                session.setAttribute("message", messages.getMessages("Admin updated successfully !", "success"));
            }
            
            model.addAttribute("noOfComments", commentService.getAllUnapprovedComments().size());
        }
        return "redirect:/admin";
    }
    
    
    @GetMapping("/login")
    public String home(Model model, HttpSession session)
    {
        
        model.addAttribute("adminObj", new Admin());
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute Admin adminObj, HttpSession session, Model model)
    {
        String login;
        
        model.addAttribute("adminObj", adminObj);
        if (Strings.isNullOrEmpty(adminObj.getUsername()) || Strings.isNullOrEmpty(adminObj.getPassword()))
        {
            session.setAttribute("message", messages.getMessages("All fields must be filled out.", "error"));
            login = "redirect:/login";
        }
        else
        {
            Admin user = adminService.login(adminObj.getUsername(), adminObj.getPassword());
            if (!Objects.isNull(user))
            {
                session.setAttribute("currentUser", user);
                session.setAttribute("message", messages.getMessages("Welcome " + user.getUsername() + " !", "success"));
                return "redirect:/dashboard";
            }
            else
            {
                session.setAttribute("currentUser", null);
                session.setAttribute("message", messages.getMessages("Login failure !", "error"));
                login = "login";
            }
        }
        
        return login;
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        String view = "redirect:/login";
        session.setAttribute("currentUser", null);
        session.setAttribute("message", messages.getMessages("Logout Successful !", "success"));
        
        return view;
    }
    
   /* public String confirmLogin(HttpSession session)
    {
    
    }*/
}
