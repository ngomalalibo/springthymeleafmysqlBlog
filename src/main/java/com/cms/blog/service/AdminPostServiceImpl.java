package com.cms.blog.service;

import com.cms.blog.entity.AdminPost;
import com.cms.blog.repository.AdminPostRepository;
import com.cms.blog.util.Messages;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AdminPostServiceImpl implements AdminPostService
{
    AdminPostRepository adminPostRepository;
    
    HttpSession session;
    
    public AdminPostServiceImpl(AdminPostRepository adminPostRepository, HttpSession session)
    {
        this.adminPostRepository = adminPostRepository;
        this.session = session;
    }
    
    @Override
    public List<AdminPost> findAll()
    {
        List<AdminPost> list = new ArrayList<>();
        List<AdminPost> listSrno = new ArrayList<>();
        adminPostRepository.findAll().iterator().forEachRemaining(list::add);
        if (list.isEmpty())
        {
            session.setAttribute("message", new Messages().getMessages("No post found", "info"));
            // throw new NotFoundException("No post found");
        }
        final AtomicInteger i = new AtomicInteger(0);
        list.forEach((cat) ->
                     {
                         cat.setAdditionalProperties(Map.of("srno", i.incrementAndGet()));
                         listSrno.add(cat);
                     });
        return listSrno;
    }
    
    @Override
    public AdminPost findById(Integer id)
    {
        Optional<AdminPost> byId = adminPostRepository.findById(id);
        return byId.orElse(null);
    }
    
    @Override
    public List<AdminPost> findRange(int from, int to)
    {
        List<AdminPost> page = adminPostRepository.findRange(from, to);
        if (page.isEmpty())
        {
            session.setAttribute("message", new Messages().getMessages("No post in range", "info"));
            // throw new NotFoundException("");
        }
        return page;
    }
    
    @Override
    public List<AdminPost> findPage(int from)
    {
        List<AdminPost> page = adminPostRepository.findPage(from);
        if (page.isEmpty())
        {
            session.setAttribute("message", new Messages().getMessages("No post on page", "info"));
            // throw new NotFoundException("");
        }
        return page;
    }
    
    @Override
    public List<AdminPost> findByCategory(Integer catId)
    {
        List<AdminPost> byCategory = adminPostRepository.findByCategory(catId);
        if (byCategory.isEmpty())
        {
            session.setAttribute("message", new Messages().getMessages("No post in category", "info"));
            // throw new NotFoundException("No post in category");
        }
        return byCategory;
    }
    
    @Override
    public List<AdminPost> search(String search)
    {
        List<AdminPost> searchResult = adminPostRepository.search(search, search, search, search);
        if (searchResult.isEmpty())
        {
            session.setAttribute("message", new Messages().getMessages("No result in search", "info"));
            // throw new NotFoundException("No result in search");
        }
        return !search.isEmpty() ? searchResult : null;
    }
    
    @Override
    public AdminPost save(AdminPost entity)
    {
        return adminPostRepository.save(entity);
    }
    
    @Override
    public AdminPost update(AdminPost entity)
    {
        return adminPostRepository.save(entity);
    }
    
    @Override
    public void deleteById(Integer var1)
    {
        adminPostRepository.deleteById(var1);
    }
    
    @Override
    public boolean existsById(Integer var1)
    {
        return adminPostRepository.existsById(var1);
    }
    
    @Override
    public long count()
    {
        return adminPostRepository.count();
    }
   /* @Override
    public AdminPost mapRow(ResultSet rs, int i) throws SQLException
    {
        AdminPost adminPost = new AdminPost();
        adminPost.setId(rs.getInt("id"));
        adminPost.setDatetime(DateToLocalDateTimeConverter.dateToLocalDateConverter(rs.getDate("datetime")));
        adminPost.setCategory(rs.getString("category"));
        adminPost.setAuthor(rs.getString("author"));
        adminPost.setPost(rs.getString("post"));
        adminPost.setTitle(rs.getString("title"));
        adminPost.setImage(rs.getBlob("image"));
        
        return adminPost;
    }*/
}
