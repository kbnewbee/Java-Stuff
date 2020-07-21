package com.ea.eadp.product.product.model;

import java.util.Objects;

public class Product {
    private Long serialNumber;
    private String userId;
    private String productName;
    private String productInternalName;
    private String franchise;
    private String description;

    public Product() {
    }

    public Product(String productName, String productInternalName, String franchise, String description) {
        this.productName = productName;
        this.productInternalName = productInternalName;
        this.franchise = franchise;
        this.description = description;
    }

    public Product(long serialNumber, String productName, String productInternalName, String franchise, String description) {
        this.serialNumber = serialNumber;
        this.productName = productName;
        this.productInternalName = productInternalName;
        this.franchise = franchise;
        this.description = description;
    }

    public Product(String userId, Long serialNumber, String productName, String productInternalName, String franchise, String description) {
        this.userId = userId;
        this.serialNumber = serialNumber;
        this.productName = productName;
        this.productInternalName = productInternalName;
        this.franchise = franchise;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductInternalName() {
        return productInternalName;
    }

    public void setProductInternalName(String productInternalName) {
        this.productInternalName = productInternalName;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(userId, product.userId) &&
                Objects.equals(serialNumber, product.serialNumber) &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(productInternalName, product.productInternalName) &&
                Objects.equals(franchise, product.franchise) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, serialNumber, productName, productInternalName, franchise, description);
    }

    @Override
    public String toString() {
        return "Product{" +
                "serialNumber=" + serialNumber +
                ", userId='" + userId + '\'' +
                ", productName='" + productName + '\'' +
                ", productInternalName='" + productInternalName + '\'' +
                ", franchise='" + franchise + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
