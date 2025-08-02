package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Customer;
import java.util.List;

/**
 * Data Access Object interface for Customer entities.
 * Defines CRUD operations and custom queries for Customer management.
 */
public interface CustomerDAO extends CrudDAO<Customer, Integer> {
    
    /**
     * Find a customer by their unique code.
     * @param customerCode The customer code to search for
     * @return The customer with the given code, or null if not found
     */
    Customer findByCode(String customerCode);
    
    /**
     * Find a customer by their phone number.
     * @param phone The phone number to search for
     * @return The customer with the given phone number, or null if not found
     */
    Customer findByPhone(String phone);
    
    /**
     * Find a customer by their email address.
     * @param email The email address to search for
     * @return The customer with the given email, or null if not found
     */
    Customer findByEmail(String email);
    
    /**
     * Search for customers by name (partial match).
     * @param name The name or part of the name to search for
     * @return List of customers whose names match the search term
     */
    List<Customer> searchByName(String name);
    
    /**
     * Search for customers by any keyword (searches code, name, phone, and email).
     * @param keyword The search term
     * @return List of customers matching the search criteria
     */
    List<Customer> searchByKeyword(String keyword);
    
    /**
     * Get a paginated list of customers.
     * @param offset The starting position of the result set
     * @param limit The maximum number of results to return
     * @return List of customers for the specified page
     */
    List<Customer> findWithPagination(int offset, int limit);
    
    /**
     * Count the total number of customers in the system.
     * @return The total count of customers
     */
    long count();
    
    /**
     * Đếm số lượng khách hàng mới trong tháng hiện tại.
     * @return Số lượng khách hàng mới
     */
    int getNewCustomersCount();
}
