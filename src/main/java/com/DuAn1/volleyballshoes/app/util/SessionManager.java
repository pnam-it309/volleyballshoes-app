package com.DuAn1.volleyballshoes.app.util;

import com.DuAn1.volleyballshoes.app.entity.Staff;

/**
 * Utility class to manage user session information
 */
public class SessionManager {
    private static SessionManager instance;
    private Staff currentStaff;
    
    private SessionManager() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Get the singleton instance of SessionManager
     */
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    /**
     * Set the currently logged-in staff member
     */
    public void setCurrentStaff(Staff staff) {
        this.currentStaff = staff;
    }
    
    /**
     * Get the currently logged-in staff member
     */
    public Staff getCurrentStaff() {
        return currentStaff;
    }
    
    /**
     * Clear the current session (logout)
     */
    public void clearSession() {
        this.currentStaff = null;
    }
    
    /**
     * Check if a staff is currently logged in
     */
    public boolean isLoggedIn() {
        return currentStaff != null;
    }
}
