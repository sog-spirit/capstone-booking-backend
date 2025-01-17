package com.capstone.core.center;

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
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.center.data.request.AddNewCenterRequestData;
import com.capstone.core.center.data.request.AdminStatisticsCenterListRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterDropdownRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterListRequestData;
import com.capstone.core.center.data.request.CenterOwnerStatisticsCenterListRequestData;
import com.capstone.core.center.data.request.DeleteCenterRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterInfoRequestData;
import com.capstone.core.center.data.request.EditCenterRequestData;
import com.capstone.core.center.data.request.EditCenterRequestData.OldPhotoItem;
import com.capstone.core.center.data.request.UserCenterListRequestData;
import com.capstone.core.center.data.request.UserCenterInfoRequestData;
import com.capstone.core.center.data.response.CenterOwnerCenterListResposneData;
import com.capstone.core.center.data.response.CenterOwnerCenterInfoResponseData;
import com.capstone.core.center.data.response.UserCenterListResponseData;
import com.capstone.core.center.data.response.UserCenterInfoResponseData;
import com.capstone.core.center.projection.CenterOwnerCenterListDropdownProjection;
import com.capstone.core.center.projection.AdminStatisticsCenterListProjection;
import com.capstone.core.center.projection.CenterListProjection;
import com.capstone.core.center.projection.CenterOwnerCenterListProjection;
import com.capstone.core.center.projection.CenterOwnerStatisticsCenterListProjection;
import com.capstone.core.center.projection.CenterOwnerCenterInfoProjection;
import com.capstone.core.center.projection.UserCenterListProjection;
import com.capstone.core.center.projection.UserCenterInfoProjection;
import com.capstone.core.center.specification.CenterSpecification;
import com.capstone.core.center.specification.criteria.CenterFilterCriteria;
import com.capstone.core.centerimage.CenterImageRepository;
import com.capstone.core.image.ImageRepository;
import com.capstone.core.table.CenterImageTable;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.ImageTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.userrole.UserRoleRepository;
import com.capstone.core.userrole.projection.UserRoleProjection;
import com.capstone.core.util.consts.CenterImageType;
import com.capstone.core.util.consts.CenterStatus;
import com.capstone.core.util.consts.UserRole;
import com.capstone.core.util.file.FileUtils;
import com.capstone.core.util.security.jwt.JwtUtil;
import com.capstone.core.util.time.TimeUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CenterService {
    private static final Long DEFAULT_FIELD_QUANTITY = (long) 0;
    private static final long DEFAULT_DISPLAY_ORDER = 1;
    private static final String BASE_FOLDER_NAME = "center";

    private CenterRepository centerRepository;
    private UserRoleRepository userRoleRepository;
    private ImageRepository imageRepository;
    private CenterImageRepository centerImageRepository;

    ResponseEntity<Object> addNewCenter(String jwtToken, AddNewCenterRequestData requestData) throws IOException {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        UserTable user = new UserTable();
        user.setId(userId);

        CenterTable newCenter = new CenterTable();
        newCenter.setName(requestData.getName());
        newCenter.setAddress(requestData.getAddress());
        newCenter.setUser(user);
        newCenter.setFieldQuantity(DEFAULT_FIELD_QUANTITY);
        newCenter.setCourtFee(requestData.getCourtFee());
        newCenter.setOpeningTime(requestData.getOpeningTime());
        newCenter.setClosingTime(requestData.getClosingTime());
        newCenter.setStatus(CenterStatus.ACTIVE.getValue());

        CenterTable savedCenter = centerRepository.save(newCenter);

        String requestPhotoFileName;
        String photoFileExtension;
        String newPhotoFileName;
        ImageTable image;
        ImageTable addImage;
        CenterImageTable centerImage;
        if (requestData.getThumbnailPhoto() != null) {
            requestPhotoFileName = requestData.getThumbnailPhoto().getOriginalFilename();
            photoFileExtension = FileUtils.getFileExtension(requestPhotoFileName);
            newPhotoFileName = userId + "_" + TimeUtils.getCurrentDateTimeString(currentLocalDateTime) + "_" + CenterImageType.THUMBNAIL.toString() + "." + photoFileExtension;
            image = new ImageTable();
            image.setFileName(newPhotoFileName);
            image.setFilePath(FileUtils.getImageFilePath(savedCenter.getId(), BASE_FOLDER_NAME, newPhotoFileName));
            image.setAltText(BASE_FOLDER_NAME);
            image.setContentType(photoFileExtension);
            image.setCreatedAt(currentLocalDateTime);
            addImage = imageRepository.save(image);
            
            centerImage = new CenterImageTable();
            centerImage.setType(CenterImageType.THUMBNAIL.getValue());
            centerImage.setDisplayOrder(DEFAULT_DISPLAY_ORDER);
            centerImage.setCenter(savedCenter);
            centerImage.setImage(addImage);
            centerImageRepository.save(centerImage);
            
            FileUtils.writeFile(requestData.getThumbnailPhoto(), savedCenter.getId(), BASE_FOLDER_NAME, newPhotoFileName);
        }

        if (requestData.getShowcasePhotos() != null) {
            long showcaseImageIndex = 2;
            for (MultipartFile imageItem : requestData.getShowcasePhotos()) {
                requestPhotoFileName = imageItem.getOriginalFilename();
                photoFileExtension = FileUtils.getFileExtension(requestPhotoFileName);
                newPhotoFileName = userId + "_" + TimeUtils.getCurrentDateTimeString(currentLocalDateTime) + "_" + CenterImageType.SHOWCASE.toString() + "." + photoFileExtension;
                image = new ImageTable();
                image.setFileName(newPhotoFileName);
                image.setFilePath(FileUtils.getImageFilePath(savedCenter.getId(), BASE_FOLDER_NAME, newPhotoFileName));
                image.setAltText(BASE_FOLDER_NAME);
                image.setContentType(photoFileExtension);
                image.setCreatedAt(currentLocalDateTime);
                addImage = imageRepository.save(image);
    
                centerImage = new CenterImageTable();
                centerImage.setType(CenterImageType.SHOWCASE.getValue());
                centerImage.setDisplayOrder(showcaseImageIndex);
                centerImage.setCenter(savedCenter);
                centerImage.setImage(addImage);
                centerImageRepository.save(centerImage);
    
                FileUtils.writeFile(imageItem, savedCenter.getId(), BASE_FOLDER_NAME, newPhotoFileName);
    
                showcaseImageIndex++;
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> editCenter(String jwtToken, EditCenterRequestData requestData) throws IOException {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<CenterTable> queryResult = centerRepository.findById(requestData.getId());
        if (queryResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CenterTable center = queryResult.get();
        center.setName(requestData.getName());
        center.setAddress(requestData.getAddress());
        center.setCourtFee(requestData.getCourtFee());
        center.setOpeningTime(requestData.getOpeningTime());
        center.setClosingTime(requestData.getClosingTime());

        centerRepository.save(center);

        CenterImageTable centerImage;
        ImageTable image;

        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        String requestPhotoFileName;
        String photoFileExtension;
        String newPhotoFileName;
        ImageTable addImage;
        if (requestData.getNewThumbnail() != null) {
            requestPhotoFileName = requestData.getNewThumbnail().getOriginalFilename();
            photoFileExtension = FileUtils.getFileExtension(requestPhotoFileName);
            newPhotoFileName = userId + "_" + TimeUtils.getCurrentDateTimeString(currentLocalDateTime) + "_" + CenterImageType.THUMBNAIL.toString() + "." + photoFileExtension;
            image = new ImageTable();
            image.setFileName(newPhotoFileName);
            image.setFilePath(FileUtils.getImageFilePath(center.getId(), BASE_FOLDER_NAME, newPhotoFileName));
            image.setAltText(BASE_FOLDER_NAME);
            image.setContentType(photoFileExtension);
            image.setCreatedAt(currentLocalDateTime);
            addImage = imageRepository.save(image);
            
            centerImage = new CenterImageTable();
            centerImage.setType(CenterImageType.THUMBNAIL.getValue());
            centerImage.setDisplayOrder(DEFAULT_DISPLAY_ORDER);
            centerImage.setCenter(center);
            centerImage.setImage(addImage);
            centerImageRepository.save(centerImage);
            
            FileUtils.writeFile(requestData.getNewThumbnail(), center.getId(), BASE_FOLDER_NAME, newPhotoFileName);
        }

        if (requestData.getNewShowcasePhotos() != null) {
            long showcaseImageIndex = 2;
            for (MultipartFile imageItem : requestData.getNewShowcasePhotos()) {
                requestPhotoFileName = imageItem.getOriginalFilename();
                photoFileExtension = FileUtils.getFileExtension(requestPhotoFileName);
                newPhotoFileName = userId + "_" + TimeUtils.getCurrentDateTimeString(currentLocalDateTime) + "_" + CenterImageType.SHOWCASE.toString() + "." + photoFileExtension;
                image = new ImageTable();
                image.setFileName(newPhotoFileName);
                image.setFilePath(FileUtils.getImageFilePath(center.getId(), BASE_FOLDER_NAME, newPhotoFileName));
                image.setAltText(BASE_FOLDER_NAME);
                image.setContentType(photoFileExtension);
                image.setCreatedAt(currentLocalDateTime);
                addImage = imageRepository.save(image);
                
                centerImage = new CenterImageTable();
                centerImage.setType(CenterImageType.SHOWCASE.getValue());
                centerImage.setDisplayOrder(showcaseImageIndex);
                centerImage.setCenter(center);
                centerImage.setImage(addImage);
                centerImageRepository.save(centerImage);
                
                FileUtils.writeFile(imageItem, center.getId(), BASE_FOLDER_NAME, newPhotoFileName);
                
                showcaseImageIndex++;
            }
        }

        if (requestData.getOldPhotos() != null) {
            for (OldPhotoItem oldPhoto : requestData.getOldPhotos()) {
                if (oldPhoto.getDelete()) {
                    centerImage = centerImageRepository.getReferenceById(oldPhoto.getId());
                    FileUtils.deleteFile(centerImage.getImage().getFilePath());
                    centerImageRepository.delete(centerImage);
                    imageRepository.delete(centerImage.getImage());
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> deleteCenter(String jwtToken, DeleteCenterRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterTable center = centerRepository.getReferenceById(requestData.getId());
        center.setStatus(CenterStatus.CLOSED.getValue());
        centerRepository.save(center);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterList(String jwtToken, UserCenterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterFilterCriteria filterCriteria = new CenterFilterCriteria();
        filterCriteria.setName(requestData.getName());
        filterCriteria.setAddress(requestData.getAddress());
        filterCriteria.setPriceFrom(requestData.getPriceFrom());
        filterCriteria.setPriceTo(requestData.getPriceTo());
        filterCriteria.setStatus(CenterStatus.ACTIVE.getValue());

        List<Sort.Order> sortOrders = new ArrayList<>();

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        Page<UserCenterListProjection> centerList = centerRepository.findBy(new CenterSpecification(filterCriteria), q -> q.as(UserCenterListProjection.class).sortBy(pageable.getSort()).page(pageable));

        UserCenterListResponseData responseData = new UserCenterListResponseData();
        responseData.setTotalPage(centerList.getTotalPages());
        responseData.setCenterList(centerList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCenterDropdownList(String jwtToken, CenterOwnerCenterDropdownRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCenterListDropdownProjection> centerListResult = centerRepository.findCenterOwnerCenterListDropdown(requestData.getQuery(), userId, requestData.getProductId());
        return new ResponseEntity<>(centerListResult, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCenterList(String jwtToken, CenterOwnerCenterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterFilterCriteria filterCriteria = new CenterFilterCriteria();
        filterCriteria.setCenterOwnerId(userId);
        filterCriteria.setStatus(CenterStatus.ACTIVE.getValue());

        List<Sort.Order> sortOrders = new ArrayList<>();

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        Page<CenterOwnerCenterListProjection> centerList = centerRepository.findBy(new CenterSpecification(filterCriteria), q -> q.as(CenterOwnerCenterListProjection.class).sortBy(pageable.getSort()).page(pageable));

        CenterOwnerCenterListResposneData responseData = new CenterOwnerCenterListResposneData();
        responseData.setTotalPage(centerList.getTotalPages());
        responseData.setCenterList(centerList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCenterInfo(String jwtToken, CenterOwnerCenterInfoRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterOwnerCenterInfoProjection centerInfo = centerRepository.findCenterOwnerCenterInfoById(requestData.getCenterId());

        CenterOwnerCenterInfoResponseData responseData = new CenterOwnerCenterInfoResponseData();
        responseData.setOpeningTime(centerInfo.getOpeningTime());
        responseData.setClosingTime(centerInfo.getClosingTime());
        responseData.setName(centerInfo.getName());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterInfo(String jwtToken, UserCenterInfoRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserCenterInfoProjection centerInfo = centerRepository.findUserCenterInfoById(requestData.getCenterId());

        UserCenterInfoResponseData responseData = new UserCenterInfoResponseData();
        responseData.setOpeningTime(centerInfo.getOpeningTime());
        responseData.setClosingTime(centerInfo.getClosingTime());
        responseData.setName(centerInfo.getName());
        responseData.setCourtFee(centerInfo.getCourtFee());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerStatisticsCenterList(String jwtToken, CenterOwnerStatisticsCenterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerStatisticsCenterListProjection> centerList = centerRepository.findCenterOwnerStatisticsCenterListByUserIdAndNameContaining(userId, requestData.getQuery());

        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }

    ResponseEntity<Object> getAdminStatisticsCenterList(String jwtToken, AdminStatisticsCenterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<AdminStatisticsCenterListProjection> centerList = centerRepository.findAdminStatisticsCenterListByNameContaining(requestData.getQuery());

        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }
}
