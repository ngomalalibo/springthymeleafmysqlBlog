package com.cms.blog.util;

import com.cms.blog.entity.Admin;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PostAndDateShortenerAdmin
{
    public List<Admin> shortenPostAndDateForView(List<Admin> list)
    {
        AtomicReference<Map> additionalProperties = new AtomicReference<>();
        AtomicInteger srno = new AtomicInteger();
        list.forEach(admin ->
                     {
                         additionalProperties.set(new HashMap());
                         additionalProperties.get().put("srno", srno.incrementAndGet());
                         admin.setAdditionalProperties(additionalProperties.get());
                     });
        
        return list;
    }
}
