package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductVariantDAOImpl implements ProductVariantDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductVariant> findAll() {
        String jpql = "SELECT pv FROM ProductVariant pv LEFT JOIN FETCH pv.product LEFT JOIN FETCH pv.color LEFT JOIN FETCH pv.size LEFT JOIN FETCH pv.soleType";
        return entityManager.createQuery(jpql, ProductVariant.class).getResultList();
    }

    @Override
    public Page<ProductVariant> findAll(Pageable pageable) {
        String jpql = "SELECT pv FROM ProductVariant pv LEFT JOIN FETCH pv.product LEFT JOIN FETCH pv.color LEFT JOIN FETCH pv.size LEFT JOIN FETCH pv.soleType";
        String countJpql = "SELECT COUNT(pv) FROM ProductVariant pv";
        
        TypedQuery<ProductVariant> query = entityManager.createQuery(jpql, ProductVariant.class);
        Long total = entityManager.createQuery(countJpql, Long.class).getSingleResult();
        
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        return new PageImpl<>(query.getResultList(), pageable, total);
    }

    @Override
    public List<ProductVariant> findByProductId(Long productId) {
        String jpql = "SELECT pv FROM ProductVariant pv LEFT JOIN FETCH pv.product LEFT JOIN FETCH pv.color LEFT JOIN FETCH pv.size LEFT JOIN FETCH pv.soleType WHERE pv.product.id = :productId";
        return entityManager.createQuery(jpql, ProductVariant.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public List<ProductVariant> findBySku(String sku) {
        String jpql = "SELECT pv FROM ProductVariant pv LEFT JOIN FETCH pv.product LEFT JOIN FETCH pv.color LEFT JOIN FETCH pv.size LEFT JOIN FETCH pv.soleType WHERE pv.sku LIKE :sku";
        return entityManager.createQuery(jpql, ProductVariant.class)
                .setParameter("sku", "%" + sku + "%")
                .getResultList();
    }

    @Override
    public List<ProductVariant> findByBarcode(String barcode) {
        String jpql = "SELECT pv FROM ProductVariant pv LEFT JOIN FETCH pv.product LEFT JOIN FETCH pv.color LEFT JOIN FETCH pv.size LEFT JOIN FETCH pv.soleType WHERE pv.barcode = :barcode";
        return entityManager.createQuery(jpql, ProductVariant.class)
                .setParameter("barcode", barcode)
                .getResultList();
    }

    @Override
    public List<ProductVariant> findByQuantityLessThanEqual(int quantity) {
        String jpql = "SELECT pv FROM ProductVariant pv LEFT JOIN FETCH pv.product LEFT JOIN FETCH pv.color LEFT JOIN FETCH pv.size LEFT JOIN FETCH pv.soleType WHERE pv.quantity <= :quantity";
        return entityManager.createQuery(jpql, ProductVariant.class)
                .setParameter("quantity", quantity)
                .getResultList();
    }

    @Override
    public ProductVariant save(ProductVariant productVariant) {
        if (productVariant.getId() == null) {
            entityManager.persist(productVariant);
            return productVariant;
        } else {
            return entityManager.merge(productVariant);
        }
    }

    @Override
    public void deleteById(Long id) {
        ProductVariant productVariant = entityManager.find(ProductVariant.class, id);
        if (productVariant != null) {
            entityManager.remove(productVariant);
        }
    }

    @Override
    public long count() {
        String jpql = "SELECT COUNT(pv) FROM ProductVariant pv";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }
}
