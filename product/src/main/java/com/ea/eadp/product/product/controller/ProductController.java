package com.ea.eadp.product.product.controller;

import com.ea.eadp.product.product.exception.ProductMissingException;
import com.ea.eadp.product.product.model.Product;
import com.ea.eadp.product.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService ps;

    @GetMapping(value = "/list",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public List<Product> getProductList() {
        return ps.getProductList();
    }

    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long productId) throws ProductMissingException {
        Product product = ps.getProductById(productId);
        if (product == null) {
            throw new ProductMissingException(MessageFormat.format("Product with Serial Id: {0}  does not exist in the database", productId));

        } else {
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }

    }

    @GetMapping(value = "/grant/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public Product grantProduct(@PathVariable("id") Long id) {
        return ps.getProductById(id);
    }

    @PostMapping(value = "/create",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product p = ps.createProduct(product);
        return new ResponseEntity<Product>(p, HttpStatus.OK);
    }

    @PostMapping(value = "/update/{id}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("id") Long id) throws ProductMissingException {
        Product ep = ps.getProductById(id);
        if (ep == null) {
            throw new ProductMissingException(MessageFormat.format("Product with Serial Id: {0}  does not exist in the database", id));
        }
        Product p = ps.updateProduct(product, ep);
        return new ResponseEntity<Product>(p, HttpStatus.OK);

    }

    /**
     * @param id
     * @return
     * @throws ProductMissingException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) throws ProductMissingException {
        Product product = ps.getProductById(id);
        if (product == null) {
            throw new ProductMissingException(MessageFormat.format("Product with Serial Id: {0}  does not exist in the database", id));
        }
        ps.deleteProduct(product);
        return new ResponseEntity(HttpStatus.OK);
    }


}
