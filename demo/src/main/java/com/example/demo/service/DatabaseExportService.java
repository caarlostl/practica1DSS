package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.ProductRepo;
import com.example.demo.model.Product;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service 
public class DatabaseExportService {
    private final ProductRepo productoRepo;

    public DatabaseExportService(ProductRepo productoRepo){
        this.productoRepo = productoRepo;
    }

    public byte[] exportDatabaseToSql(){
        List<Product> productos = productoRepo.findAll();
        String sql = "";

        for (Product producto : productos){
            sql += "INSERT INTO producto (id, name, price) VALUES ("
                + producto.getId() + ", '" + producto.getName() + "', "
                + producto.getPrice() + ");\n";
        }

        return sql.getBytes(StandardCharsets.UTF_8);
    }
}
