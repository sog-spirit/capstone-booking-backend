package com.capstone.core.product;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.image.ImageRepository;
import com.capstone.core.product.data.request.CenterOwnerProductListRequestData;
import com.capstone.core.product.data.request.CreateProductRequestData;
import com.capstone.core.product.data.request.EditProductRequestData;
import com.capstone.core.product.data.request.ProductListDropdownRequestData;
import com.capstone.core.product.projection.CenterOwnerProductListProjection;
import com.capstone.core.product.projection.ProductListDropdownProjection;
import com.capstone.core.product.projection.ProductListProjection;
import com.capstone.core.product.specification.ProductSpecification;
import com.capstone.core.product.specification.criteria.ProductCriteria;
import com.capstone.core.productimage.ProductImageRepository;
import com.capstone.core.table.ImageTable;
import com.capstone.core.table.ProductImageTable;
import com.capstone.core.table.ProductTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.consts.ProductImageType;
import com.capstone.core.util.file.FileUtils;
import com.capstone.core.util.security.jwt.JwtUtil;
import com.capstone.core.util.time.TimeUtils;

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
    ResponseEntity<Object> createProduct(String jwtToken, CreateProductRequestData createProductRequestData) throws IOException {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductTable newProduct = new ProductTable();
        newProduct.setName(createProductRequestData.getName());
        newProduct.setPrice(createProductRequestData.getPrice());

        UserTable user = new UserTable();
        user.setId(userId);
        newProduct.setUser(user);

        ProductTable addedProduct = productRepository.save(newProduct);

        String requestPhotoFileName = createProductRequestData.getPhoto().getOriginalFilename();
        String photoFileExtension = FileUtils.getFileExtension(requestPhotoFileName);
        String newPhotoFileName = userId + "_" + TimeUtils.getCurrentDateTimeString(currentLocalDateTime) + "." + photoFileExtension;
        ImageTable image = new ImageTable();
        image.setFileName(newPhotoFileName);
        image.setFilePath(FileUtils.getImageFilePath(addedProduct.getId(), BASE_FOLDER_NAME, newPhotoFileName));
        image.setAltText(BASE_FOLDER_NAME);
        image.setContentType(photoFileExtension);
        image.setCreatedAt(currentLocalDateTime);
        ImageTable addImage = imageRepository.save(image);

        ProductImageTable productImage = new ProductImageTable();
        productImage.setType(ProductImageType.THUMBNAIL.getValue());
        productImage.setDisplayOrder(DEFAULT_DISPLAY_ORDER);
        productImage.setProduct(addedProduct);
        productImage.setImage(addImage);
        productImageRepository.save(productImage);

        FileUtils.writeFile(createProductRequestData.getPhoto(), addedProduct.getId(), BASE_FOLDER_NAME, newPhotoFileName);

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
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
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

        Optional<ProductImageTable> productImageQuery = productImageRepository.findByProductId(product.getId());
        if (productImageQuery.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProductImageTable productImage = productImageQuery.get();
        String oldPhotoFilePath = productImage.getImage().getFilePath();
        String requestPhotoFileName = editProductRequestData.getPhoto().getOriginalFilename();
        String photoFileExtension = FileUtils.getFileExtension(requestPhotoFileName);
        String newPhotoFileName = userId + "_" + TimeUtils.getCurrentDateTimeString(currentLocalDateTime) + "." + photoFileExtension;
        productImage.getImage().setFileName(newPhotoFileName);
        productImage.getImage().setContentType(photoFileExtension);
        productImage.getImage().setFilePath(FileUtils.getImageFilePath(product.getId(), BASE_FOLDER_NAME, newPhotoFileName));
        FileUtils.overwriteFile(editProductRequestData.getPhoto(), product.getId(), BASE_FOLDER_NAME, newPhotoFileName, oldPhotoFilePath);
        productImageRepository.save(productImage);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getProductListDropdown(String jwtToken, ProductListDropdownRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<ProductListDropdownProjection> productListDropdownResult = productRepository.findByUserIdAndNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(productListDropdownResult, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerProductList(String jwtToken, CenterOwnerProductListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductCriteria productCriteria = new ProductCriteria();
        productCriteria.setUserId(userId);

        List<Sort.Order> sortOrders = new ArrayList<>();

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(Integer.parseInt("0"), Integer.parseInt("5"), sort);

        Page<CenterOwnerProductListProjection> productList = productRepository.findBy(new ProductSpecification(productCriteria), q -> q.as(CenterOwnerProductListProjection.class).sortBy(pageable.getSort()).page(pageable));

        return new ResponseEntity<>(productList.getContent(), HttpStatus.OK);
    }
}
