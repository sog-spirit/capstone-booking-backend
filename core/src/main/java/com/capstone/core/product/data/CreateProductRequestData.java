package com.capstone.core.product.data;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestData {
    private String name;
    private Long price;
    private MultipartFile photo;
}
