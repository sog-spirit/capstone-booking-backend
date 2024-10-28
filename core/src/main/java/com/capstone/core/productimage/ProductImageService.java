package com.capstone.core.productimage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstone.core.table.ProductImageTable;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductImageService {

    private ProductImageRepository productImageRepository;

    ResponseEntity<Object> getImageProduct(Long productId) throws FileNotFoundException, IOException {
        Optional<ProductImageTable> productImageQuery = productImageRepository.findByProductId(productId);
        if (productImageQuery.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProductImageTable productImage = productImageQuery.get();
        String filePath = productImage.getImage().getFilePath();
        File imageFile = new File(filePath);
        try (InputStream inputStream = new FileInputStream(imageFile)) {
            return new ResponseEntity<>(inputStream.readAllBytes(), HttpStatus.OK);
        }
    }
}
