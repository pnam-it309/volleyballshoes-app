package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Staff;
import java.util.List;

public interface StaffDAO extends CrudDAO<Staff, Integer> {

    /**
     * Find a staff member by their email address.
     * @param email The email address to search for
     * @return The staff member with the given email, or null if not found
     */
    Staff findByEmail(String email);

    /**
     * Check if a staff member with the given email exists.
     * @param email The email address to check
     * @return true if a staff member with the email exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Check if a staff member with the given username exists.
     * @param username The username to check
     * @return true if a staff member with the username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Search for staff members by keyword (searches username, full name, email, phone, and code).
     * @param keyword The search term
     * @return List of staff members matching the search criteria
     */
    List<Staff> search(String keyword);

    /**
     * Find staff members by their role.
     * @param role The role to search for
     * @return List of staff members with the given role
     */
    List<Staff> findByRole(int role);
    
    /**
     * Find a staff member by their username.
     * @param username The username to search for
     * @return The staff member with the given username, or null if not found
     */
    Staff findByUsername(String username);
    
    /**
     * Get the total number of staff members.
     * @return The total count of staff members
     */
    long count();
    
    /**
     * Get a paginated list of staff members with optional filtering.
     * @param page The page number (1-based)
     * @param pageSize The number of items per page
     * @param filter The filter string (can be null or empty for no filter)
     * @return List of staff members for the specified page and filter
     */
    List<Staff> findWithPagination(int page, int pageSize, String filter);
    
    /**
     * Find a staff member by their staff code.
     * @param staffCode The staff code to search for
     * @return The staff member with the given code, or null if not found
     */
    Staff findByStaffCode(String staffCode);
}
