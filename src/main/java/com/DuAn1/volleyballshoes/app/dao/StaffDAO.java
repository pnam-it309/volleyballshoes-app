package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffDAO extends JpaRepository<Staff, Long> {
    
    Optional<Staff> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT s FROM Staff s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "s.phone LIKE CONCAT('%', :keyword, '%')")
    List<Staff> search(@Param("keyword") String keyword);
    
    @Query("SELECT s FROM Staff s WHERE s.role = :role")
    List<Staff> findByRole(@Param("role") String role);
    
    @Query("SELECT s FROM Staff s WHERE s.active = :active")
    List<Staff> findByActive(@Param("active") boolean active);
    
    @Query("SELECT s FROM Staff s WHERE s.role = :role AND s.active = :active")
    List<Staff> findByRoleAndActive(@Param("role") String role, @Param("active") boolean active);
}
