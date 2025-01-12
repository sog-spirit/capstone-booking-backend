package com.capstone.core.center.data.request;

import java.time.LocalTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCenterRequestData {
    private Long id;
    private String name;
    private String address;
    private Long courtFee;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private List<OldPhotoItem> oldPhotos;
    private MultipartFile newThumbnail;
    private List<MultipartFile> newShowcasePhotos;

    @Data
    public static class OldPhotoItem {
        private Long id;
        private Long type;
        private Boolean delete;
    }
}
