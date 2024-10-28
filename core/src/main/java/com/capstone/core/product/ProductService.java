package com.capstone.core.product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.image.ImageRepository;
import com.capstone.core.product.data.CreateProductRequestData;
import com.capstone.core.product.data.EditProductRequestData;
import com.capstone.core.product.projection.ProductListDropdownProjection;
import com.capstone.core.product.projection.ProductListProjection;
import com.capstone.core.productimage.ProductImageRepository;
import com.capstone.core.table.ImageTable;
import com.capstone.core.table.ProductImageTable;
import com.capstone.core.table.ProductTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.consts.ProductImageType;
import com.capstone.core.util.file.FileUtils;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private static final long DEFAULT_DISPLAY_ORDER = 1;
    private static final String BASE_FOLDER_NAME = "product";

    private ProductRepository productRepository;
    private ImageRepository imageRepository;
    private ProductImageRepository productImageRepository;

    @Transactional
    ResponseEntity<Object> createProduct(String jwtToken, CreateProductRequestData createCustomerServiceRequestData) throws IOException {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductTable newProduct = new ProductTable();
        newProduct.setName(createCustomerServiceRequestData.getName());
        newProduct.setPrice(createCustomerServiceRequestData.getPrice());

        UserTable user = new UserTable();
        user.setId(userId);
        newProduct.setUser(user);

        ProductTable addedProduct = productRepository.save(newProduct);

        ImageTable image = new ImageTable();
        image.setFileName(FileUtils.getImageFilename(addedProduct.getId()));
        image.setFilePath(FileUtils.getImageFilePath(addedProduct.getId(), BASE_FOLDER_NAME));
        image.setAltText(BASE_FOLDER_NAME);
        image.setContentType(FileUtils.PHOTO_FILE_EXTENSION);
        ImageTable addImage = imageRepository.save(image);

        ProductImageTable productImage = new ProductImageTable();
        productImage.setType(ProductImageType.THUMBNAIL.getValue());
        productImage.setDisplayOrder(DEFAULT_DISPLAY_ORDER);
        productImage.setProduct(addedProduct);
        productImage.setImage(addImage);
        productImageRepository.save(productImage);

        FileUtils.writeFile(createCustomerServiceRequestData.getPhoto(), addedProduct.getId(), BASE_FOLDER_NAME);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getProductList(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<ProductListProjection> productList = productRepository.findByUserId(userId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @Transactional
    ResponseEntity<Object> editProduct(String jwtToken, EditProductRequestData editProductRequestData) throws IOException {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<ProductTable> productQuery = productRepository.findById(editProductRequestData.getId());
        if (productQuery.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProductTable product = productQuery.get();
        product.setName(editProductRequestData.getName());
        product.setPrice(editProductRequestData.getPrice());
        productRepository.save(product);

        FileUtils.writeFile(editProductRequestData.getPhoto(), product.getId(), BASE_FOLDER_NAME);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getProductListDropdown(String jwtToken, String query) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<ProductListDropdownProjection> productListDropdownResult = productRepository.findByUserIdAndNameContaining(userId, query);
        return new ResponseEntity<>(productListDropdownResult, HttpStatus.OK);
    }
}
