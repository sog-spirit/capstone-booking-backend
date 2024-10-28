package com.capstone.core.productimage;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/image/product")
@AllArgsConstructor
public class ProductImageController {

    private ProductImageService productImageService;

    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE})
    ResponseEntity<Object> getProductImage(@RequestParam(name = "productId", required = true) Long productId) throws FileNotFoundException, IOException {
        return productImageService.getImageProduct(productId);
    }
}
