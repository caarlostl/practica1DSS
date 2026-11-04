package com.example.demo.controller;

import com.example.demo.service.DatabaseExportService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final DatabaseExportService exportService;

    public AdminController(DatabaseExportService exportService){
        this.exportService = exportService;
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportDatabase(){
        byte[] file = exportService.exportDatabaseToSql();
        return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=productos.sql").body(file);
    }

}
