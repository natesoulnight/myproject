package com.example.springSecurityApplication.controllers;

import com.example.springSecurityApplication.repositories.ProductRepository;
import com.example.springSecurityApplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("")
    public String getAllProduct(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "/product/product";
    }

    @GetMapping("/info/{id}")
    public String infoUser(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price", required = false, defaultValue = "")String price, @RequestParam(value = "contact", required = false, defaultValue = "")String contact, Model model) {
        if (!price.isEmpty()) {
            if (price.equals("sorted_by_ascending_price")) {
                model.addAttribute("search_product", productRepository.OrderByPriceAsc(price));
            } else if (price.equals("sorted_by_descending_price")) {
                model.addAttribute("search_product", productRepository.OrderByPriceDesc(price));
            }
        }

        else if (!ot.isEmpty() & !Do.isEmpty()) {
            model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search, Float.parseFloat(ot), Float.parseFloat(Do)));
        }

        else if (!contact.isEmpty()) {
            if (contact.equals("pc_mono")) {
                model.addAttribute("search_product", productRepository.findByCategory(1));
            } else if (contact.equals("notebooks")) {
                model.addAttribute("search_product", productRepository.findByCategory(2));
            } else if (contact.equals("smartphone")) {
                model.addAttribute("search_product", productRepository.findByCategory(3));
            } else if (contact.equals("pud")) {
                model.addAttribute("search_product", productRepository.findByCategory(4));
            } else if (contact.equals("complete")) {
                model.addAttribute("search_product", productRepository.findByCategory(5));
            } else if (contact.equals("acupressure")) {
                model.addAttribute("search_product", productRepository.findByCategory(6));
            }
        }

        else{
            model.addAttribute("search_product", productRepository.findByTitleContainingIgnoreCase(search));
        }

        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);
        model.addAttribute("products", productService.getAllProduct());
        return "/product/product";

    }

}
