package com.ea.eadp.product.product.service;

import com.ea.eadp.product.product.model.Product;

import java.util.List;

public interface ProductServiceInter {
    Product createProduct(Product product);
    Product updateProduct(Product product, Product eproduct);
    void deleteProduct(Product product);
    List<Product> getProductList();
}
