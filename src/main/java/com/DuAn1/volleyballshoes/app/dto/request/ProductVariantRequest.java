package com.DuAn1.volleyballshoes.app.dto.request;

import lombok.Data;

@Data
public class ProductVariantRequest {
    private int variantId;
    private int productId;
    private int sizeId;
    private int colorId;
    private String variantSku;
    private double variantOrigPrice;
    private String variantImgUrl;
    private int stock;
} 