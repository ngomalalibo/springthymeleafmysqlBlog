package com.cms.blog.temp;

public class TempCode
{
    
    /** Single control for multiple endpoints
     * @GetMapping({"/index/{search}", "/{category}/index", "/index/page/{page}"})
    public String getSearch(@PathVariable(value = "search", required = false) String search,
     @PathVariable(value = "category", required = false) String category,
     @PathVariable(value = "page", required = false) String page, Model model)
     {
     List<AdminPost> result = new ArrayList<>();
     if (search != null)
     {
     if (!search.isEmpty())
     {
     result = adminPostService.search(search);
     }
     }
     else if (category != null)
     {
     if (!category.isEmpty())
     {
     if (StringUtils.isNumeric(category))
     {
     result = adminPostService.findByCategory(Integer.valueOf(category));
     }
     }
     
     }
     else if (page != null)
     {
     if (!page.isEmpty())
     {
     if (StringUtils.isNumeric(page))
     {
     result = adminPostService.findPage(Integer.parseInt(page));
     if (Objects.isNull(result))
     {
     result = adminPostService.findAll();
     }
     
     int count = Long.valueOf(adminPostService.count()).intValue();
     model.addAttribute("count", count);
     model.addAttribute("noOfPages", count / 3);
     }
     }
     }
     else
     {
     result = adminPostService.findAll();
     }
     
     result.forEach(post ->
     {
     if (post.getPost().length() > 150)
     {
     post.setPost(post.getPost().substring(0, 150) + "...");
     }
     if (post.getDatetime().toString().length() > 11)
     {
     post.setAdditionalProperties(Map.of("shortDate", post.getDatetime().toString().substring(0, 11)));
     }
     else
     {
     post.setAdditionalProperties(Map.of("shortDate", post.getDatetime().toString()));
     }
     if (post.getPost().length() > 15)
     {
     post.setAdditionalProperties(Map.of("shortPost", post.getPost().substring(0, 15)));
     }
     else
     {
     post.setAdditionalProperties(Map.of("shortPost", post.getPost()));
     }
     });
     
     model.addAttribute("resultList", result);
     
     model.addAttribute("commentService", commentService);
     model.addAttribute("categories", categoryService.findAll());
     model.addAttribute("recentPosts", adminPostService.findRange(0, 5));
     
     return "index";
     }
      * */
    
    
    /*public static String copyUploadedFileToDir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        
        
        String destination = "/Upload";
        Part filePart = request.getPart("imageselect"); // Retrieves <input type="file" name="file">
        System.out.println("filePart = " + filePart);
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        System.out.println("fileName = " + fileName);
        
        //if destination does not exit, create it
        Path path = Paths.get(destination);
        if (!path.toFile().exists())
        {
            boolean mkdir = path.toFile().mkdir();
            if (mkdir)
            {
                log.info("Directory created successfully: " + path.toFile());
                System.out.println("Directory created successfully: " + path.toFile().getName());
            }
        }
        //get files from source
        InputStream fileContent = filePart.getInputStream();
        OutputStream out = new FileOutputStream(destination + "/"
                                                        + fileName);
        
        
        // Copy the bits from input stream to output stream
        // byte[] buf = new byte[(int) fileToCopy.length()];
        byte[] buf = new byte[fileContent.readAllBytes().length];
        int len;
        while ((len = fileContent.read(buf)) > 0)
        {
            out.write(buf, 0, len);
        }
        fileContent.close();
        out.close();
        
        return fileName;
    }*/
    
 
    /*public static void main(String[] args)
    {
        File file = new File("C:\\Users\\Ngo\\Desktop\\Martang Academy\\marketing\\images\\guide\\allie-Pwc1wNurzl8-unsplash.jpg");
        if (file.exists())
        {
            System.out.println("File Exist => " + file.getName() + " :: " + file.getAbsolutePath());
        }
        FileInputStream input = null;
        try
        {
            input = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), "image/jpeg",
                                                                IOUtils.toByteArray(input));
            
            if (saveFile(multipartFile))
            {
                System.out.println("File saved");
            }
            else
            {
                System.out.println("File not saved");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
    }*/
    
    
    /**public static boolean saveFile(MultipartFile multipartFile) throws IOException
     {
     String uploadDir = "/Upload";
     Path uploadPath = Paths.get(uploadDir);
     String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
     
     if (!Files.exists(uploadPath))
     {
     Path path = Files.createDirectories(uploadPath);
     Path effectivePath = path;
     if (!path.isAbsolute())
     {
     Path absPath = path.toAbsolutePath();
     System.out.println("absPath = " + absPath);
     Path base = Paths.get("");
     effectivePath = base.resolve(path).toAbsolutePath();
     }
     System.out.println("effectivePath = " + effectivePath.normalize().toString());
     }
     
     try (InputStream inputStream = multipartFile.getInputStream())
     {
     Path filePath = uploadPath.resolve(fileName);
     Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
     return true;
     }
     catch (IOException ioe)
     {
     throw new IOException("Could not save image file: " + fileName, ioe);
     }
     }*/
}
