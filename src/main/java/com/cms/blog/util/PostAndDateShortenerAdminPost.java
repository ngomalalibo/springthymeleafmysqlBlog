package com.cms.blog.util;

import com.cms.blog.entity.AdminPost;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PostAndDateShortenerAdminPost
{
    public List<AdminPost> shortenPostAndDateForView(List<AdminPost> list)
    {
        AtomicReference<Map> additionalProperties = new AtomicReference<>();
        AtomicInteger srno = new AtomicInteger();
        list.forEach(post ->
                     {
                         additionalProperties.set(new HashMap());
                         if (post.getPost().length() > 150)
                         {
                             additionalProperties.get().put("midPost", post.getPost().substring(0, 150) + "...");
                         }
                         else
                         {
                             additionalProperties.get().put("midPost", post.getPost());
                         }
                         if (post.getPost().length() > 15)
                         {
                             additionalProperties.get().put("shortPost", post.getPost().substring(0, 15) + "... ");
                         }
                         else
                         {
                             additionalProperties.get().put("shortPost", post.getPost());
                         }
            
                         if (post.getDatetime().length() > 11)
                         {
                             additionalProperties.get().put("shortDate", post.getDatetime().substring(0, 11));
                         }
                         else
                         {
                             additionalProperties.get().put("shortDate", post.getDatetime());
                         }
            
                         if (post.getTitle().length() > 15)
                         {
                             additionalProperties.get().put("shortTitle", post.getTitle().substring(0, 15) + "...");
                         }
                         else
                         {
                             additionalProperties.get().put("shortTitle", post.getTitle());
                         }
            
                         additionalProperties.get().put("srno", srno.incrementAndGet());
                         post.setAdditionalProperties(additionalProperties.get());
                     });
        
        return list;
    }
}
