package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class StaffDAOImpl implements StaffDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Staff> search(String keyword) {
        String jpql = "SELECT s FROM Staff s WHERE " +
                     "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                     "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                     "s.phone LIKE CONCAT('%', :keyword, '%')";
        
        return entityManager.createQuery(jpql, Staff.class)
                          .setParameter("keyword", keyword)
                          .getResultList();
    }

    @Override
    public List<Staff> findByRole(String role) {
        return entityManager.createQuery("SELECT s FROM Staff s WHERE s.role = :role", Staff.class)
                          .setParameter("role", role)
                          .getResultList();
    }

    @Override
    public List<Staff> findByActive(boolean active) {
        return entityManager.createQuery("SELECT s FROM Staff s WHERE s.active = :active", Staff.class)
                          .setParameter("active", active)
                          .getResultList();
    }

    @Override
    public List<Staff> findByRoleAndActive(String role, boolean active) {
        return entityManager.createQuery(
            "SELECT s FROM Staff s WHERE s.role = :role AND s.active = :active", Staff.class)
                          .setParameter("role", role)
                          .setParameter("active", active)
                          .getResultList();
    }

    // Các phương thức khác sẽ được thừa kế từ JpaRepository
}
