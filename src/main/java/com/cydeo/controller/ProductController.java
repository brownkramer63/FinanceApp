package com.cydeo.controller;


import com.cydeo.dto.ProductDTO;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")

public class ProductController {

    private final ProductService productService;
    private final MapperUtil mapperUtil;

    public ProductController(ProductService productService, MapperUtil mapperUtil) {
        this.productService = productService;
        this.mapperUtil = mapperUtil;
    }

    @GetMapping("/create")
    public String createProduct(Model model){
    model.addAttribute("newProduct", new ProductDTO());
  return "/product/product-create";
}


    @PostMapping("/insert")
    public String insertProduct(@ModelAttribute("newProduct")  ProductDTO productDTO){
        productService.save(productDTO);

        return "redirect:/list";
    }

    @GetMapping("/list")
    public String listProduct(Model model){
        model.addAttribute("products", productService.listAllProducts());

        return "/product/product-list";
    }



}
