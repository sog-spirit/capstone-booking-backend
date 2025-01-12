package com.capstone.core.centerimage;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/image/center")
@AllArgsConstructor
public class CenterImageController {
    private CenterImageService centerImageService;

    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE})
    ResponseEntity<Object> getCenterImage(@RequestParam(name = "id", required = true) Long id) throws IOException {
        return centerImageService.getCenterImage(id);
    }

    @GetMapping(value = "/info/list")
    ResponseEntity<Object> getCenterImageInfoList(@RequestParam(name = "centerId", required = true) Long centerId) {
        return centerImageService.getCenterImageInfoList(centerId);
    }

    @GetMapping(value = "/thumbnail-info")
    ResponseEntity<Object> getCenterThumbnailImage(@RequestParam(name = "centerId", required = true) Long centerId) {
        return centerImageService.getCenterThumbnailImage(centerId);
    }

    @GetMapping(value = "/showcase-info")
    ResponseEntity<Object> getCenterShowcaseInfo(@RequestParam(name = "centerId", required = true) Long centerId) {
        return centerImageService.getCenterShowcaseInfo(centerId);
    }
}
