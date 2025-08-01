package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Category;
import java.util.List;

/**
 * Data Access Object interface for Category entity.
 * Provides CRUD operations and additional query methods for Category.
 */
public interface CategoryDAO extends CrudDAO<Category, Integer> {
    
    /**
     * Find a category by its name.
     * @param name the name of the category to find
     * @return the category with the given name, or null if not found
     */
    Category findByName(String name);
    
    /**
     * Search for categories by keyword (searches in name and description).
     * @param keyword the search term
     * @return a list of matching categories
     */
    List<Category> search(String keyword);
    
    /**
     * Check if a category with the given name exists.
     * @param name the name to check
     * @return true if a category with the name exists, false otherwise
     */
    boolean existsByName(String name);
}
