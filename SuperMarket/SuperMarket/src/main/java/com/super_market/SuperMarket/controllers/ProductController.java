package com.super_market.SuperMarket.controllers;

import com.super_market.SuperMarket.models.ProductDto;
import com.super_market.SuperMarket.repositories.ProductRepository;
import com.super_market.SuperMarket.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/stock")
    public String getProducts(Model model) {
        List<Product> products = productRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products/stock";
    }

    @GetMapping("/product")
    public String showProductForm(@RequestParam(required = false) Integer id, Model model) {
        ProductDto productDto = new ProductDto();

        // If id is present, we're editing an existing product
        if (id != null) {
            Optional<Product> productOptional = productRepo.findById(id);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                productDto.setName(product.getName());
                productDto.setCategory(product.getCategory());
                productDto.setPrice(product.getPrice());
                productDto.setQuantityInStock(product.getQuantityInStock());
                productDto.setDescription(product.getDescription());

                // Add the id to the model for edit operations
                model.addAttribute("productId", product.getId());
            }
        }

        model.addAttribute("productDto", productDto);
        return "products/product";
    }

    @PostMapping("/product")
    public String saveProduct(@ModelAttribute("productDto") ProductDto productDto,
                              @RequestParam(required = false) Integer productId,
                              Model model) {
        try {
            Product product;

            if (productId != null) {
                // Editing existing product
                Optional<Product> productOptional = productRepo.findById(productId);
                if (productOptional.isPresent()) {
                    product = productOptional.get();
                } else {
                    throw new Exception("Product not found");
                }
            } else {
                // Creating new product
                product = new Product();
                product.setCreatedAt(new Date());
            }

            product.setName(productDto.getName());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setQuantityInStock(productDto.getQuantityInStock());
            product.setDescription(productDto.getDescription());

            productRepo.save(product);
            model.addAttribute("message", "Product saved successfully!");

        } catch (Exception e) {
            model.addAttribute("error", "Error saving product: " + e.getMessage());
            return "products/product";
        }

        return "redirect:/products/stock";
    }

    @GetMapping("/edit")
    public String editProduct(@RequestParam Integer id, Model model) {
        return "redirect:/products/product?id=" + id;
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Integer id) {
        productRepo.deleteById(id);
        return "redirect:/products/stock";
    }
}