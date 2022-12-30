package com.cydeo.controller;


import com.cydeo.dto.ProductDTO;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        return "redirect:/products/list";
    }

    @GetMapping("/list")
    public String listProduct(Model model){
        model.addAttribute("products", productService.listAllProducts());

        return "/product/product-list";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:/products/list";
    }

    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, Model model) {

        model.addAttribute("product", productService.findById(id));

        return "/product/product-create";
    }

    @PostMapping("/list")
    public String updateCategory(@ModelAttribute("newProduct") ProductDTO categoryDTO) {

        productService.update(categoryDTO);

        return "redirect/products/list";

    }






}
