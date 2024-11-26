package com.capstone.core.product.data.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProductRequestData {
    private Long id;
    private String name;
    private Long price;
    private MultipartFile photo;
}
