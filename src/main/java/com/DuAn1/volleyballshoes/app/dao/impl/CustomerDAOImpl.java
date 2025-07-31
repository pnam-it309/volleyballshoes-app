package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.CustomerDAO;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import com.DuAn1.volleyballshoes.app.entity.Customer.Gender;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of CustomerDAO interface for managing customer data.
 */
public class CustomerDAOImpl implements CustomerDAO {
    
    private final List<Customer> customers;
    private int nextId = 1;
    
    public CustomerDAOImpl() {
        this.customers = new ArrayList<>();
        initializeData();
    }
    
    private void initializeData() {
        // Sample data with all fields including the new ones
        customers.add(Customer.builder()
            .customerId(nextId++)
            .customerCode("KH001")
            .customerFullName("Nguyễn Văn A")
            .customerEmail("nguyenvana@email.com")
            .customerPhone("0123456789")
            .customerGender(Gender.MALE)
            .customerBirthDate(LocalDate.of(1990, Month.JANUARY, 15))
            .customerAddress("123 Đường Lê Lợi, Quận 1, TP.HCM")
            .customerPoints(100)
            .build());
            
        customers.add(Customer.builder()
            .customerId(nextId++)
            .customerCode("KH002")
            .customerFullName("Trần Thị B")
            .customerEmail("tranthib@email.com")
            .customerPhone("0987654321")
            .customerGender(Gender.FEMALE)
            .customerBirthDate(LocalDate.of(1992, Month.MAY, 22))
            .customerAddress("456 Đường Lê Văn Sỹ, Quận 3, TP.HCM")
            .customerPoints(250)
            .build());
            
        customers.add(Customer.builder()
            .customerId(nextId++)
            .customerCode("KH003")
            .customerFullName("Lê Văn C")
            .customerEmail("levanc@email.com")
            .customerPhone("0369852147")
            .customerGender(Gender.OTHER)
            .customerBirthDate(LocalDate.of(1985, Month.OCTOBER, 5))
            .customerAddress("789 Đường Cách Mạng Tháng 8, Quận 10, TP.HCM")
            .customerPoints(50)
            .build());
    }
    
    @Override
    public Customer create(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        // Ensure the customer doesn't already exist
        if (customer.getCustomerId() > 0) {
            throw new IllegalArgumentException("Customer ID must be null or 0 for new customer");
        }
        
        // Set the new ID and add to the list
        customer.setCustomerId(nextId++);
        customers.add(customer);
        return customer;
    }
    
    @Override
    public void update(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId() == customer.getCustomerId()) {
                customers.set(i, customer);
                return;
            }
        }
        
        throw new IllegalArgumentException("Customer not found with ID: " + customer.getCustomerId());
    }
    
    @Override
    public void deleteById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid customer ID: " + id);
        }
        
        boolean removed = customers.removeIf(customer -> customer.getCustomerId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Customer not found with ID: " + id);
        }
    }
    
    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }
    
    @Override
    public Customer findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid customer ID: " + id);
        }
        
        return customers.stream()
            .filter(customer -> customer.getCustomerId() == id)
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public Customer findByCode(String customerCode) {
        if (customerCode == null || customerCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer code cannot be null or empty");
        }
        
        return customers.stream()
            .filter(customer -> customer.getCustomerCode().equalsIgnoreCase(customerCode.trim()))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public Customer findByPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        
        String phoneNumber = phone.trim();
        return customers.stream()
            .filter(customer -> phoneNumber.equals(customer.getCustomerPhone()) || 
                              phoneNumber.equals(customer.getCustomerSdt()))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public Customer findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        String emailLower = email.trim().toLowerCase();
        return customers.stream()
            .filter(customer -> emailLower.equals(customer.getCustomerEmail().toLowerCase()))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<Customer> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return findAll();
        }
        
        String searchTerm = name.trim().toLowerCase();
        return customers.stream()
            .filter(customer -> customer.getCustomerFullName().toLowerCase().contains(searchTerm))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Customer> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        
        String searchTerm = keyword.trim().toLowerCase();
        return customers.stream()
            .filter(customer -> 
                (customer.getCustomerCode() != null && customer.getCustomerCode().toLowerCase().contains(searchTerm)) ||
                (customer.getCustomerFullName() != null && customer.getCustomerFullName().toLowerCase().contains(searchTerm)) ||
                (customer.getCustomerPhone() != null && customer.getCustomerPhone().contains(searchTerm)) ||
                (customer.getCustomerSdt() != null && customer.getCustomerSdt().contains(searchTerm)) ||
                (customer.getCustomerEmail() != null && customer.getCustomerEmail().toLowerCase().contains(searchTerm)) ||
                (customer.getCustomerAddress() != null && customer.getCustomerAddress().toLowerCase().contains(searchTerm)) ||
                (customer.getCustomerGender() != null && 
                 customer.getCustomerGender().getGenderDisplayName().toLowerCase().contains(searchTerm))
            )
            .collect(Collectors.toList());
    }
    
    /**
     * Finds customers with pagination support.
     * 
     * @param offset The number of items to skip
     * @param limit The maximum number of items to return
     * @return A list of customers for the specified page
     */
    @Override
    public List<Customer> findWithPagination(int offset, int limit) {
        if (offset < 0) {
            offset = 0;
        }
        if (limit <= 0) {
            limit = 10; // Default page size
        }
        
        return customers.stream()
            .skip(offset)
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    @Override
    public long countAll() {
        return customers.size();
    }
}
