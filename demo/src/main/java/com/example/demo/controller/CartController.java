package com.example.demo.controller;
import com.example.demo.model.Product;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {
	private final ProductService productService;
	private final CartService cartService;

    public CartController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

	@GetMapping("/cart")
	public String cart(Model model){
		model.addAttribute("cartItems", cartService.getProducts());
		return "cart"; 
	}

	@PostMapping("/cart/add/{id}")
	public String addToCart(@PathVariable("id") Long id, RedirectAttributes ra) {
		Product p = productService.getProductById(id);
		if (p != null) {
			cartService.addProduct(p);
			ra.addFlashAttribute("msg", "Añadido al carrito");
		}
		return "redirect:/cart";
	}

	@PostMapping("/cart/remove/{id}")
	public String removeFromCart(@PathVariable("id") Long id, RedirectAttributes ra) {
		cartService.removeProduct(id);
		ra.addFlashAttribute("msg", "Eliminado del carrito");
		return "redirect:/cart";
	}
}
