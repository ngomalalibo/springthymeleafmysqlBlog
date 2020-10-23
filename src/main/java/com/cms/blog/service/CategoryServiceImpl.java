package com.cms.blog.service;

import com.cms.blog.entity.Category;
import com.cms.blog.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService, RowMapper<Category>
{
    
    private CategoryRepository categoryRepository;
    
    public CategoryServiceImpl(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public List<Category> findAll()
    {
        List<Category> caties = new ArrayList<>();
        List<Category> catiesSrno = new ArrayList<>();
        
        categoryRepository.findAll().iterator().forEachRemaining(caties::add);
        final AtomicInteger i = new AtomicInteger(0);
        caties.forEach((cat) ->
                                {
                                    cat.setAdditionalProperties(Map.of("srno", i.incrementAndGet()));
                                    catiesSrno.add(cat);
                                });
        /*int srno = 0;
        
        for (Category one : caties)
        {
            one.setAdditionalProperties(Map.of("srno", srno++));
            catiesSrno.add(one);
        }*/
        
        return catiesSrno;
    }
    
    @Override
    public Category findById(Integer id)
    {
        
        Optional<Category> optional = categoryRepository.findById(id);
        
        if (optional.isEmpty())
        {
            return null;
        }
        
        return optional.get();
    }
    
    @Override
    public Category save(Category entity)
    {
        return categoryRepository.save(entity);
    }
    
    @Override
    public Category update(Category entity)
    {
        return categoryRepository.save(entity);
    }
    
    @Override
    public void deleteById(Integer var1)
    {
        categoryRepository.deleteById(var1);
    }
    
    @Override
    public boolean existsById(Integer var1)
    {
        Optional<Category> optional = categoryRepository.findById(var1);
        
        if (optional.isEmpty())
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public long count()
    {
        return categoryRepository.count();
    }
    
    @Override
    public Category mapRow(ResultSet rs, int i) throws SQLException
    {
        Category entity = new Category();
        
        entity.setId(rs.getInt("id"));
        entity.setDatetime(rs.getString("datetime"));
        entity.setCategoryname(rs.getString("categoryname"));
        entity.setCreatorName(rs.getString("creatorname"));
        return entity;
        /*entity.setDatetime(rs.getDate());
        
        adminPost.setId(rs.getInt("id"));
        adminPost.setDatetime(DateToLocalDateTimeConverter.dateToLocalDateConverter(rs.getDate("datetime")));
        adminPost.setCategory(rs.getString("category"));
        adminPost.setAuthor(rs.getString("author"));
        adminPost.setPost(rs.getString("post"));
        adminPost.setTitle(rs.getString("title"));
        adminPost.setImage(rs.getBlob("image"));*/
        
        
    }
}
