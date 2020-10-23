package com.cms.blog.service;

import com.cms.blog.entity.Admin;
import com.cms.blog.repository.AdminRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService
{
    AdminRepository adminRepository;
    HttpSession session;
    
    public AdminServiceImpl(AdminRepository adminRepository, HttpSession session)
    {
        this.adminRepository = adminRepository;
        this.session = session;
    }
    
    @Override
    public Admin login(String username, String password)
    {
        return adminRepository.login(username, password);
    }
    
    @Override
    public List<Admin> findAll()
    {
        return adminRepository.findAll();
    }
    
    @Override
    public Admin findById(Integer id)
    {
        Optional<Admin> byId = adminRepository.findById(id);
        return byId.orElse(null);
    }
    
    @Override
    public Admin save(Admin entity)
    {
        return adminRepository.save(entity);
    }
    
    @Override
    public Admin update(Admin entity)
    {
        return adminRepository.save(entity);
    }
    
    @Override
    public void deleteById(Integer var1)
    {
        adminRepository.deleteById(var1);
    }
    
    @Override
    public boolean existsById(Integer var1)
    {
        return adminRepository.existsById(var1);
    }
    
    @Override
    public long count()
    {
        return adminRepository.count();
    }
}
