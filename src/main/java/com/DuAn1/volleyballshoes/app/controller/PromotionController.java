package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.dao.ProductVariantPromotionDAO;
import com.DuAn1.volleyballshoes.app.dao.PromotionDAO;
import com.DuAn1.volleyballshoes.app.dto.request.PromotionCreateRequest;
import com.DuAn1.volleyballshoes.app.dto.request.PromotionUpdateRequest;
import com.DuAn1.volleyballshoes.app.dto.response.PromotionResponse;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.entity.ProductVariantPromotion;
import com.DuAn1.volleyballshoes.app.entity.Promotion;
import com.DuAn1.volleyballshoes.app.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionDAO promotionDAO;
    private final ProductVariantPromotionDAO productVariantPromotionDAO;
    private final ProductVariantDAO productVariantDAO;

    public List<PromotionResponse> getAllPromotions() {
        return promotionDAO.findAll().stream()
                .map(this::mapToPromotionResponse)
                .collect(Collectors.toList());
    }

    public PromotionResponse getPromotionById(Long id) {
        return promotionDAO.findById(id)
                .map(this::mapToPromotionResponse)
                .orElseThrow(() -> new BusinessException("Không tìm thấy chương trình khuyến mãi với ID: " + id));
    }

    public PromotionResponse createPromotion(PromotionCreateRequest request) {
        validatePromotionDates(request.getStartDate(), request.getEndDate());

        Promotion promotion = new Promotion();
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setDiscountType(request.getDiscountType());
        promotion.setDiscountValue(request.getDiscountValue());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
        promotion.setActive(true);

        Promotion savedPromotion = promotionDAO.save(promotion);
        
        // Nếu có danh sách biến thể sản phẩm áp dụng
        if (request.getProductVariantIds() != null && !request.getProductVariantIds().isEmpty()) {
            saveProductVariantPromotions(savedPromotion, request.getProductVariantIds());
        }

        return mapToPromotionResponse(savedPromotion);
    }

    public PromotionResponse updatePromotion(Long id, PromotionUpdateRequest request) {
        Promotion promotion = promotionDAO.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy chương trình khuyến mãi với ID: " + id));

        if (request.getName() != null) {
            promotion.setName(request.getName());
        }
        if (request.getDescription() != null) {
            promotion.setDescription(request.getDescription());
        }
        if (request.getDiscountType() != null) {
            promotion.setDiscountType(request.getDiscountType());
        }
        if (request.getDiscountValue() != null) {
            promotion.setDiscountValue(request.getDiscountValue());
        }
        if (request.getStartDate() != null && request.getEndDate() != null) {
            validatePromotionDates(request.getStartDate(), request.getEndDate());
            promotion.setStartDate(request.getStartDate());
            promotion.setEndDate(request.getEndDate());
        }
        if (request.getActive() != null) {
            promotion.setActive(request.getActive());
        }

        // Cập nhật danh sách sản phẩm áp dụng nếu có
        if (request.getProductVariantIds() != null) {
            // Xóa các liên kết cũ
            productVariantPromotionDAO.deleteByPromotion(promotion);
            // Thêm các liên kết mới
            if (!request.getProductVariantIds().isEmpty()) {
                saveProductVariantPromotions(promotion, request.getProductVariantIds());
            }
        }

        Promotion updatedPromotion = promotionDAO.save(promotion);
        return mapToPromotionResponse(updatedPromotion);
    }

    public void deletePromotion(Long id) {
        if (!promotionDAO.existsById(id)) {
            throw new BusinessException("Không tìm thấy chương trình khuyến mãi với ID: " + id);
        }
        // Xóa các liên kết với sản phẩm trước
        productVariantPromotionDAO.deleteByPromotionId(id);
        promotionDAO.deleteById(id);
    }

    public List<PromotionResponse> getActivePromotions() {
        LocalDateTime now = LocalDateTime.now();
        return promotionDAO.findActivePromotions(now).stream()
                .map(this::mapToPromotionResponse)
                .collect(Collectors.toList());
    }

    private void saveProductVariantPromotions(Promotion promotion, List<Long> productVariantIds) {
        for (Long variantId : productVariantIds) {
            ProductVariant variant = productVariantDAO.findById(variantId)
                    .orElseThrow(() -> new BusinessException("Không tìm thấy biến thể sản phẩm với ID: " + variantId));
            
            ProductVariantPromotion pvp = new ProductVariantPromotion();
            pvp.setProductVariant(variant);
            pvp.setPromotion(promotion);
            productVariantPromotionDAO.save(pvp);
        }
    }

    private void validatePromotionDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BusinessException("Ngày bắt đầu phải trước ngày kết thúc");
        }
        if (endDate.isBefore(LocalDateTime.now())) {
            throw new BusinessException("Ngày kết thúc phải sau ngày hiện tại");
        }
    }

    private PromotionResponse mapToPromotionResponse(Promotion promotion) {
        return PromotionResponse.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .description(promotion.getDescription())
                .discountType(promotion.getDiscountType())
                .discountValue(promotion.getDiscountValue())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .active(promotion.isActive())
                .createdAt(promotion.getCreatedAt())
                .updatedAt(promotion.getUpdatedAt())
                .build();
    }
}
