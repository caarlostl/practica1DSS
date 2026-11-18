package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
	@Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public List<Product> filterProducts(String name, Double minPrice, Double maxPrice) {

        List<Product> products = productRepo.findAll();
        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : products) {
            boolean matches = true;

            if (name != null && !name.isEmpty() && !product.getName().toLowerCase().contains(name.toLowerCase())) {
                matches = false;
            }

            if (minPrice != null && product.getPrice() < minPrice) {
                matches = false;
            }

            if (maxPrice != null && product.getPrice() > maxPrice) {
                matches = false;
            }

            if (matches) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }
}
