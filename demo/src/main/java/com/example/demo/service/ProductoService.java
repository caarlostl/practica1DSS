package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {
	@Autowired
    private ProductoRepo productRepo;

    public List<Producto> getAllProducts() {
        return productRepo.findAll();
    }

    public Producto getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public void saveProduct(Producto product) {
        productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public List<Producto> filterProducts(String name, Double minPrice, Double maxPrice) {

        List<Producto> products = productRepo.findAll();
        List<Producto> filteredProducts = new ArrayList<>();

        for (Producto product : products) {
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
