package com.cms.blog.controller;

import com.cms.blog.entity.AdminPost;
import com.cms.blog.exceptions.BadRequestException;
import com.cms.blog.service.AdminPostService;
import com.cms.blog.service.CategoryService;
import com.cms.blog.service.CommentService;
import com.cms.blog.util.Messages;
import com.cms.blog.util.PostAndDateShortenerAdminPost;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Controller
public class AdminPostController
{
    AdminPostService adminPostService;
    CategoryService categoryService;
    CommentService commentService;
    
    Messages messages;
    
    PostAndDateShortenerAdminPost postAndDateShortenerAdminPost;
    
    public AdminPostController(AdminPostService adminPostService, CategoryService categoryService, CommentService commentService, Messages messages, PostAndDateShortenerAdminPost postAndDateShortenerAdminPost)
    {
        this.adminPostService = adminPostService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.messages = messages;
        this.postAndDateShortenerAdminPost = postAndDateShortenerAdminPost;
    }
    
    @GetMapping({"/post", "addNewPost"})
    public String getPosts(Model model, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        model.addAttribute("adminPostObj", new AdminPost());
        
        model.addAttribute("noOfComments", commentService.getAllUnapprovedComments().size());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("adminPosts", postAndDateShortenerAdminPost.shortenPostAndDateForView(adminPostService.findAll()));
        return "addNewPost";
    }
    
    
    @PostMapping("/post/new")
    public String saveOrUpdatePost(@ModelAttribute AdminPost adminPostObj, Model model, BindingResult bindingResult, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        String message = null;
        String view = null;
        
        if (bindingResult.hasErrors())
        {
            StringBuilder errors = new StringBuilder("Something went wrong! <br>");
            bindingResult.getAllErrors().forEach(objectError ->
                                                 {
                                                     log.debug(objectError.toString());
                                                     errors.append(objectError.toString()).append(" <br>");
                                                 });
            // message = messages.getMessages(errors.toString(), "error");
            session.setAttribute("message", errors.toString());
            model.addAttribute("message", messages.errorMessages(session));
        }
        else
        {
            adminPostObj.setDatetime(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss a Z")));
            MultipartFile multipartFile = adminPostObj.getImageMPF();
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            long fileSize = multipartFile.getSize();
            if (fileSize > 50000000L)
            {
                
                
                message = messages.getMessages("Maximum file size is 50mb", "error");
                session.setAttribute("message", message);
                //throw new BadRequestException("Maximum file size is 50mb");
            }
            else if (Strings.isNullOrEmpty(fileName))
            {
                message = messages.getMessages("Select image to upload", "error");
                view = "redirect:/post";
                // view = "editPost";
            }
            else
            {
                try
                {
                    adminPostObj.setImage(fileName);
                    String uploadDir = "/Upload";
                    Path uploadPath = Paths.get(uploadDir);
                    if (!Files.exists(uploadPath))
                    {
                        Files.createDirectories(uploadPath);
                    }
                    
                    InputStream inputStream = multipartFile.getInputStream();
                    Path filePath = uploadPath.resolve(fileName);
                    if (new File(uploadDir, fileName).exists())
                    {
                        if (Files.deleteIfExists(filePath))
                        {
                            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                            inputStream.close();
                        }
                        else
                        {
                            throw new BadRequestException("Could not delete image file: " + fileName);
                        }
                    }
                    else
                    {
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                        inputStream.close();
                    }
                    
                    // String fileName = copyUploadedFileToDir(request, response);
                    adminPostObj.setImage(fileName);
                    // adminPostObj.setAuthor();
                    
                    // AdminPost dataCheck = adminPostService.findById(adminPostObj.getId());
                    AdminPost saved = adminPostService.save(adminPostObj);
                    System.out.println("adminPostObj = " + adminPostObj.getId());
                    if (Objects.isNull(adminPostObj.getId()))
                    {
                        if (Objects.isNull(saved))
                        {
                            message = messages.getMessages("Something went wrong !", "error");
                            return "addNewPost";
                        }
                        
                        message = messages.getMessages("Admin post added successfully", "success");
                        view = "addNewPost";
                        
                    }
                    else
                    {
                        message = messages.getMessages("Admin post updated successfully", "success");
                        view = "redirect:/dashboard";
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    message = messages.getMessages("Could not save image file: " + fileName, "error");
                    view = "addNewPost";
                    //throw new BadRequestException("Could not save image file: " + fileName, e);
                }
            }
        }
        model.addAttribute("adminPostObj", new AdminPost());
        session.setAttribute("message", message);
        
        model.addAttribute("noOfComments", commentService.getAllUnapprovedComments().size());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("adminPosts", postAndDateShortenerAdminPost.shortenPostAndDateForView(adminPostService.findAll()));
        return view;
    }
    
    
    @GetMapping("/post/edit/{id}")
    public String editPost(@PathVariable Integer id, Model model, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        String message = null;
        
        AdminPost byId = adminPostService.findById(id);
        
        model.addAttribute("fullPost", byId);
        
        model.addAttribute("noOfComments", commentService.getAllUnapprovedComments().size());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("adminPosts", postAndDateShortenerAdminPost.shortenPostAndDateForView(adminPostService.findAll()));
        
        model.addAttribute("adminPostObj", byId);
        session.setAttribute("adminPostObj", byId);
        
        return "editPost";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        //model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("allPosts", postAndDateShortenerAdminPost.shortenPostAndDateForView(adminPostService.findAll()));
        model.addAttribute("commentService", commentService);
        model.addAttribute("noOfComments", commentService.getAllUnapprovedComments().size());
        return "dashboard";
    }
    
    
    @GetMapping("/post/delete/{id}")
    public String deletePage(@PathVariable Integer id, Model model, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        AdminPost post = adminPostService.findById(id);
        model.addAttribute("adminPostObj", post);
        model.addAttribute("noOfComments", commentService.getAllUnapprovedComments().size());
        
        return "deletePost";
    }
    
    @PostMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Integer id, HttpSession session)
    {
        if (session.getAttribute("currentUser") == null)
        {
            session.setAttribute("message", messages.getMessages("Login Required !", "error"));
            return "redirect:/login";
        }
        String view;
        
        adminPostService.deleteById(id);
        AdminPost byId = adminPostService.findById(id);
        
        if (Objects.isNull(byId))
        {
            session.setAttribute("message", messages.getMessages("Post deleted Successfully !", "success"));
            view = "redirect:/dashboard";
        }
        else
        {
            session.setAttribute("message", messages.getMessages("Something went wrong. Try Again !", "error"));
            view = "/post/delete/" + id;
        }
        
        return view;
    }
    
    
}
