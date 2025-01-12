package com.capstone.core.centerimage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstone.core.centerimage.projection.CenterImageInfoListProjection;
import com.capstone.core.centerimage.projection.CenterShowcaseInfoProjection;
import com.capstone.core.centerimage.projection.CenterThumbnailImageProjection;
import com.capstone.core.table.CenterImageTable;
import com.capstone.core.util.consts.CenterImageType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CenterImageService {
    private CenterImageRepository centerImageRepository;

    ResponseEntity<Object> getCenterImage(Long id) throws IOException {
        CenterImageTable centerImageQuery = centerImageRepository.getReferenceById(id);
        String filePath = centerImageQuery.getImage().getFilePath();
        File imageFile = new File(filePath);
        try (InputStream inputStream = new FileInputStream(imageFile)) {
            return new ResponseEntity<>(inputStream.readAllBytes(), HttpStatus.OK);
        }
    }

    ResponseEntity<Object> getCenterImageInfoList(Long centerId) {
        List<CenterImageInfoListProjection> centerImageInfoList = centerImageRepository.findCenterImageInfoListByCenterId(centerId);
        return new ResponseEntity<>(centerImageInfoList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterThumbnailImage(Long centerId) {
        CenterThumbnailImageProjection centerThumbnail = centerImageRepository.findCenterThumbnailImageByCenterIdAndType(centerId, CenterImageType.THUMBNAIL.getValue());
        return new ResponseEntity<>(centerThumbnail, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterShowcaseInfo(Long centerId) {
        List<CenterShowcaseInfoProjection> centerShowcase = centerImageRepository.findCenterShowcaseInfoByCenterIdAndType(centerId, CenterImageType.SHOWCASE.getValue());
        return new ResponseEntity<>(centerShowcase, HttpStatus.OK);
    }
}
