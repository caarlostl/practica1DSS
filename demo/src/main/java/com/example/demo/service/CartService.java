package com.example.demo.service;

import com.example.demo.repository.ProductRepo;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class CartService{
    private final ProductRepo productoRepo;
    private List<Product> products = new ArrayList<Product>();

    public CartService(ProductRepo productoRepo) {
        this.productoRepo = productoRepo;
    }

    public void addProduct(Product producto){
        products.add(producto);
    }

    public void removeProduct(Long id){
        for (int i = 0; i < products.size(); i++){
            if (products.get(i).getId().equals(id)){
                products.remove(i);
                break;
            }
        }
    }

    public List<Product> getProducts(){
        List<Product> availableProductsList = productoRepo.findAll();
        List<Product> updatedProductsList = new ArrayList<Product>();
        for (Product p: products){
            for (Product available: availableProductsList){
                if (p.getId().equals(available.getId())){
                    updatedProductsList.add(available);
                    break;
                }
            }
        }

        products = updatedProductsList;

        return products;
    }
}