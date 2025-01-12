package com.capstone.core.center.data.request;

import java.time.LocalTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewCenterRequestData {
    @NotEmpty(message = "{error.name.required}")
    private String name;
    @NotEmpty(message = "{error.address.required}")
    private String address;
    private Long courtFee;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private MultipartFile thumbnailPhoto;
    private List<MultipartFile> showcasePhotos;
}
