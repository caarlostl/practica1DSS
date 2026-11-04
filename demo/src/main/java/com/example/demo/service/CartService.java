package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class CartService{
    private List<Producto> products = new ArrayList<Producto>();

    public void addProduct(Producto producto){
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

    public List<Producto> getProducts(){
        return products;
    }
}