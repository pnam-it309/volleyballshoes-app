package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Customer;
import java.util.List;

public interface CustomerDAO {
    
    Customer create(Customer customer);
    
    void update(Customer customer);
    
    void deleteById(Integer id);
    
    List<Customer> findAll();
    
    Customer findById(Integer id);
    
    Customer findByCode(String customerCode);
    
    Customer findByPhone(String phone);
    
    Customer findByEmail(String email);
    
    List<Customer> searchByName(String name);
    
    List<Customer> searchByKeyword(String keyword);
    
    List<Customer> findWithPagination(int offset, int limit);
    
    long countAll();
}
