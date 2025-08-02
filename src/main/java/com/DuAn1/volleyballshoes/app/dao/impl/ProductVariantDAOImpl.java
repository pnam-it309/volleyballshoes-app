package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductVariantDAOImpl implements ProductVariantDAO {


    @Override
    public List<ProductVariant> findAll() {
        String jpql = "SELECT pv FROM ProductVariant pv LEFT JOIN FETCH pv.product LEFT JOIN FETCH pv.color LEFT JOIN FETCH pv.size LEFT JOIN FETCH pv.soleType";
        return entityManager.createQuery(jpql, ProductVariant.class).getResultList();
    }

    @Override
    public List<ProductVariant> findByProductId(int productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProductVariant create(ProductVariant entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProductVariant update(ProductVariant entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProductVariant findById(Integer id) {
        try {
            String jpql = "SELECT pv FROM ProductVariant pv " +
                        "LEFT JOIN FETCH pv.product " +
                        "LEFT JOIN FETCH pv.color " +
                        "LEFT JOIN FETCH pv.size " +
                        "LEFT JOIN FETCH pv.soleType " +
                        "WHERE pv.id = :id";
            TypedQuery<ProductVariant> query = entityManager.createQuery(jpql, ProductVariant.class);
            query.setParameter("id", id);
            return query.getResultStream().findFirst().orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
