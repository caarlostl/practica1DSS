package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.ProductoRepo;
import com.example.demo.model.Producto;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service 
public class DatabaseExportService {
    private final ProductoRepo productoRepo;

    public DatabaseExportService(ProductoRepo productoRepo){
        this.productoRepo = productoRepo;
    }

    public byte[] exportDatabaseToSql(){
        List<Producto> productos = productoRepo.findAll();
        String sql = "";

        for (Producto producto : productos){
            sql += "INSERT INTO producto (id, name, price) VALUES ("
                + producto.getId() + ", '" + producto.getName() + "', "
                + producto.getPrice() + ");\n";
        }

        return sql.getBytes(StandardCharsets.UTF_8);
    }
}
