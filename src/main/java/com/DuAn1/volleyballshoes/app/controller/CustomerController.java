package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.CustomerDAOImpl;
import com.DuAn1.volleyballshoes.app.dto.request.CustomerCreateRequest;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import java.util.List;
import java.util.Objects;

/**
 * Controller for handling customer-related business logic.
 */
public class CustomerController {

    private final CustomerDAO customerDAO;

    public CustomerController() {
        this.customerDAO = new CustomerDAOImpl();
    }

    public CustomerController(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * Creates a new customer with the provided request data.
     *
     * @param request The customer creation request
     * @return The created customer
     * @throws IllegalArgumentException If the request is invalid or a customer
     * with the same code/phone/email exists
     */
    public Customer createCustomer(CustomerCreateRequest request) {
        // Validate the request
        String validationError = request.getValidationErrorMessage();
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }

        // Check if customer code already exists
        if (customerDAO.findByCode(request.getCustomerCode()) != null) {
            throw new IllegalArgumentException("Mã khách hàng đã tồn tại");
        }

        // Check if email already exists
        if (request.getCustomerEmail() != null && !request.getCustomerEmail().trim().isEmpty()
                && customerDAO.findByEmail(request.getCustomerEmail()) != null) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        }

        // Build and save the new customer
        Customer customer = Customer.builder()
                .customerCode(request.getCustomerCode())
                .customerFullName(request.getCustomerFullName())
                .customerEmail(request.getCustomerEmail())
                .customerPhone(request.getCustomerSdt())
                .build();

        return customerDAO.create(customer);
    }

    /**
     * Updates an existing customer.
     *
     * @param customer The customer with updated information
     * @throws IllegalArgumentException If the customer ID is invalid or the
     * customer doesn't exist
     */
    public void updateCustomer(Customer customer) {
        if (customer.getCustomerId() <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }

        Customer existingCustomer = customerDAO.findById(customer.getCustomerId());
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng");
        }

        // Validate phone number uniqueness if changed
        if (!Objects.equals(existingCustomer.getCustomerPhone(), customer.getCustomerPhone())
                && customer.getCustomerPhone() != null && !customer.getCustomerPhone().trim().isEmpty()) {
            Customer customerWithSamePhone = customerDAO.findByPhone(customer.getCustomerPhone());
            if (customerWithSamePhone != null && customerWithSamePhone.getCustomerId() != customer.getCustomerId()) {
                throw new IllegalArgumentException("Số điện thoại đã được sử dụng bởi khách hàng khác");
            }
        }

        // Validate email uniqueness if changed
        if (!Objects.equals(existingCustomer.getCustomerEmail(), customer.getCustomerEmail())
                && customer.getCustomerEmail() != null && !customer.getCustomerEmail().trim().isEmpty()) {
            Customer customerWithSameEmail = customerDAO.findByEmail(customer.getCustomerEmail());
            if (customerWithSameEmail != null && customerWithSameEmail.getCustomerId() != customer.getCustomerId()) {
                throw new IllegalArgumentException("Email đã được sử dụng bởi khách hàng khác");
            }
        }

        customerDAO.update(customer);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param customerId The ID of the customer to delete
     * @throws IllegalArgumentException If the customer ID is invalid or the
     * customer doesn't exist
     */
    public void deleteCustomer(Integer customerId) {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }

        Customer existingCustomer = customerDAO.findById(customerId);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng");
        }

        customerDAO.deleteById(customerId);
    }

    /**
     * Retrieves all customers.
     *
     * @return A list of all customers
     */
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    /**
     * Retrieves a customer by ID.
     *
     * @param customerId The ID of the customer to retrieve
     * @return The customer with the specified ID
     * @throws IllegalArgumentException If the customer ID is invalid or the
     * customer doesn't exist
     */
    public Customer getCustomerById(Integer customerId) {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }

        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng");
        }

        return customer;
    }

    /**
     * Retrieves a customer by code.
     *
     * @param customerCode The code of the customer to retrieve
     * @return The customer with the specified code
     * @throws IllegalArgumentException If the customer code is invalid or the
     * customer doesn't exist
     */
    public Customer getCustomerByCode(String customerCode) {
        if (customerCode == null || customerCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }

        Customer customer = customerDAO.findByCode(customerCode);
        if (customer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với mã: " + customerCode);
        }

        return customer;
    }

    /**
     * Searches for customers by keyword.
     *
     * @param keyword The search keyword (can be name, code, phone, or email)
     * @return A list of matching customers
     */
    public List<Customer> searchCustomers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return customerDAO.findAll();
        }

        return customerDAO.searchByKeyword(keyword.trim());
    }

    /**
     * Retrieves a paginated list of customers.
     *
     * @param page The page number (1-based)
     * @param pageSize The number of customers per page
     * @return A list of customers for the specified page
     */
    public List<Customer> getCustomersWithPagination(int page, int pageSize) {
        if (page < 1) {
            page = 1;
        }
        if (pageSize < 1) {
            pageSize = 10; // Default page size
        }

        int offset = (page - 1) * pageSize;
        return customerDAO.findWithPagination(offset, pageSize);
    }

    /**
     * Retrieves the total number of customers.
     *
     * @return The total number of customers
     */
    public long getTotalCustomers() {
        return customerDAO.count();
    }

    public int getTotalPages(int pageSize) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page size");
        }

        long totalCount = getTotalCustomers();
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}
