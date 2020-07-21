package com.ea.eadp.product.product.service;

import com.ea.eadp.product.product.model.Product;
import com.ea.eadp.product.product.model.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

@Service
public class ProductService implements ProductServiceInter {

    private List<Product> productList = new ArrayList<Product>();
    private Utility utility;
    private static final AtomicLong counter = new AtomicLong();

    public ProductService() {
    }

    @Autowired
    public ProductService(Utility utility) {
        this.utility = utility;
        initDatabase(new Product(counter.incrementAndGet(), "FIFA 20", "fifa-20", "FIFA", "Football game"));
        initDatabase(new Product(counter.incrementAndGet(), "FIFA 18", "fifa-18", "FIFA", "Football game"));
        initDatabase(new Product(counter.incrementAndGet(), "Battlefield 2", "Battlefield-2", "Battlefield", "Shooting game"));
        initDatabase(new Product(counter.incrementAndGet(), "Need For Speed : Most Wanted", "nfs-mw", "Need For Speed", "Racing game"));
        initDatabase(new Product(counter.incrementAndGet(), "Burnout Revenge", "burnout-revenge", "Burnout", "Racing game"));
        initDatabase(new Product(counter.incrementAndGet(), "Madden NFL 21", "Madden-21", "Madden NFL", "American Football game"));

    }

    private void initDatabase(Product product) {
        String userId = utility.generateUserId();
        product.setUserId(userId);
        productList.add(product);

    }

    private Product filterProduct(Predicate<Product> strategy) {
        return getProductList().stream().filter(strategy).findFirst().orElse(null);
    }

    public Product getProductById(Long id) {
        Predicate<Product> byId = p -> p.getSerialNumber().equals(id);
        return filterProduct(byId);
    }

    @Override
    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public Product createProduct(Product product) {
        String userId = utility.generateUserId();
        product.setUserId(userId);
        productList.add(product);
        for (int i = 0; i < productList.size(); i++) {
            productList.get(i).setSerialNumber((long) (i + 1));
        }
        return product;
    }

    @Override
    public Product updateProduct(Product product, Product eproduct) {
        if (product.getProductName() != null) eproduct.setProductName(product.getProductName());
        if (product.getProductInternalName() != null) eproduct.setProductInternalName(product.getProductInternalName());
        if (product.getFranchise() != null) eproduct.setFranchise(product.getFranchise());
        if (product.getDescription() != null) eproduct.setDescription(product.getDescription());
        return eproduct;
    }


    @Override
    public void deleteProduct(Product product) {
        productList.remove(product);
        for (int i = 0; i < productList.size(); i++) {
            productList.get(i).setSerialNumber((long) (i + 1));
        }
    }


}
