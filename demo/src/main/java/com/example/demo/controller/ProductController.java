package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;


@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
    private ProductService productService;
	@GetMapping
    public String getProductsPage(Model model, @RequestParam(required =  false) String name, @RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) {
        model.addAttribute("products", productService.filterProducts(name, minPrice, maxPrice));

        return "products";
    }
    
	@GetMapping("/add")
    public String showAddForm(Model model, @RequestParam(required = false) String name, @RequestParam(required = false) Double price) {
        Product product = new Product();

        if(name != null){
            product.setName(name);
        }

        if (price != null){
            product.setPrice(price);
        }

        model.addAttribute("product", product);
        
        return "formulario-producto";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);

        if (product == null){
            return "redirect:/products";
        }

        model.addAttribute("product", product);

        return "formulario-producto";
    }
    

	@PostMapping("/save")
    public String saveProduct(Product product){
        productService.saveProduct(product);

        return "redirect:/products";
    }

	@PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}