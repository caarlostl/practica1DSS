package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;


@Controller
@RequestMapping("/productos")
public class ProductoController {
	@Autowired
    private ProductoService productService;
	@GetMapping
    public String getProductsPage(Model model, @RequestParam(required =  false) String name, @RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) {
        model.addAttribute("products", productService.filterProducts(name, minPrice, maxPrice));

        return "productos";
    }
    
	@GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Producto());
        return "formulario-producto";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Producto product = productService.getProductById(id);

        if (product == null){
            return "redirect:/productos";
        }

        model.addAttribute("product", product);

        return "formulario-producto";
    }
    

	@PostMapping("/save")
    public String saveProduct(Producto product){
        productService.saveProduct(product);

        return "redirect:/productos";
    }

	@PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/productos";
    }
}