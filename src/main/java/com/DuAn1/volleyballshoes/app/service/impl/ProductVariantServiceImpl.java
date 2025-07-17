package com.DuAn1.volleyballshoes.app.service.impl;

import com.DuAn1.volleyballshoes.app.dto.request.ProductVariantRequest;
import com.DuAn1.volleyballshoes.app.dto.response.ProductVariantResponse;
import com.DuAn1.volleyballshoes.app.entity.*;
import com.DuAn1.volleyballshoes.app.repository.ProductVariantRepository;
import com.DuAn1.volleyballshoes.app.service.ProductVariantService;
import com.DuAn1.volleyballshoes.app.utils.NotificationUtil;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductVariantRepository repo = new ProductVariantRepository();
    private final Component parentComponent;
    public ProductVariantServiceImpl(Component parentComponent) {
        this.parentComponent = parentComponent;
    }
    private ProductVariant toEntity(ProductVariantRequest req) {
        ProductVariant pv = new ProductVariant();
        pv.setVariantId(req.getVariantId());
        pv.setProductId(req.getProductId());
        pv.setSizeId(req.getSizeId());
        pv.setColorId(req.getColorId());
        pv.setVariantSku(req.getVariantSku());
        pv.setVariantOrigPrice(req.getVariantOrigPrice());
        pv.setVariantImgUrl(req.getVariantImgUrl());
        return pv;
    }
    private ProductVariantResponse toResponse(ProductVariant v) {
        ProductVariantResponse res = new ProductVariantResponse();
        res.setVariantId(v.getVariantId());
        res.setProductId(v.getProductId());
        res.setSizeId(v.getSizeId());
        res.setColorId(v.getColorId());
        res.setVariantSku(v.getVariantSku());
        res.setVariantOrigPrice(v.getVariantOrigPrice());
        res.setVariantImgUrl(v.getVariantImgUrl());
        return res;
    }
    @Override
    public boolean add(ProductVariantRequest request) {
        try {
            repo.insert(toEntity(request));
            NotificationUtil.showSuccess(parentComponent, "Thêm sản phẩm thành công!");
            return true;
        } catch (Exception e) {
            NotificationUtil.showError(parentComponent, "Thêm sản phẩm thất bại!\n" + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean update(ProductVariantRequest request) {
        try {
            repo.update(toEntity(request));
            NotificationUtil.showSuccess(parentComponent, "Cập nhật sản phẩm thành công!");
            return true;
        } catch (Exception e) {
            NotificationUtil.showError(parentComponent, "Cập nhật sản phẩm thất bại!\n" + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean delete(int variantId) {
        try {
            repo.delete(variantId);
            NotificationUtil.showSuccess(parentComponent, "Xóa sản phẩm thành công!");
            return true;
        } catch (Exception e) {
            NotificationUtil.showError(parentComponent, "Xóa sản phẩm thất bại!\n" + e.getMessage());
            return false;
        }
    }
    @Override
    public List<ProductVariantResponse> findAll() {
        List<ProductVariantResponse> result = new ArrayList<>();
        for (ProductVariant v : repo.findAll()) {
            result.add(toResponse(v));
        }
        return result;
    }
    @Override
    public List<ProductVariantResponse> search(String keyword) {
        List<ProductVariantResponse> result = new ArrayList<>();
        for (ProductVariant v : repo.searchByKeyword(keyword)) {
            result.add(toResponse(v));
        }
        return result;
    }
} 