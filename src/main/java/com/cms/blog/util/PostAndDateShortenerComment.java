package com.cms.blog.util;

import com.cms.blog.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PostAndDateShortenerComment
{
    public List<Comment> shortenPostAndDateForView(List<Comment> list)
    {
        AtomicReference<Map> additionalProperties = new AtomicReference<>();
        AtomicInteger srno = new AtomicInteger();
        list.forEach(comment ->
                     {
                         additionalProperties.set(new HashMap());
                         if (comment.getComment().length() > 20)
                         {
                             additionalProperties.get().put("shortComment", comment.getComment().substring(0, 20) + "...");
                         }
                         else
                         {
                             additionalProperties.get().put("shortComment", comment.getComment());
                         }
            
                         if (comment.getDatetime().length() > 11)
                         {
                             additionalProperties.get().put("shortDate", comment.getDatetime().substring(0, 11));
                         }
                         else
                         {
                             additionalProperties.get().put("shortDate", comment.getDatetime());
                         }
                         additionalProperties.get().put("srno", srno.incrementAndGet());
                         comment.setAdditionalProperties(additionalProperties.get());
                     });
        
        return list;
    }
}
