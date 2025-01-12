package com.capstone.core.centerreview;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.centerreview.data.request.AddNewCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.AdminApproveCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.AdminCenterReviewListRequestData;
import com.capstone.core.centerreview.data.request.AdminDeniedCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.CenterOwnerCenterReviewCenterFilterListRequestData;
import com.capstone.core.centerreview.data.request.CenterOwnerCenterReviewUserFilterListRequestData;
import com.capstone.core.centerreview.data.request.CenterOwnerReviewListRequestData;
import com.capstone.core.centerreview.data.request.DeleteCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.EditCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.UserCenterCenterReviewListRequestData;
import com.capstone.core.centerreview.data.request.UserCenterReviewCenterFilterListRequestData;
import com.capstone.core.centerreview.data.request.UserCenterReviewListRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/center-review")
@AllArgsConstructor
public class CenterReviewController {
    private CenterReviewService centerReviewService;

    @PostMapping
    @Transactional
    ResponseEntity<Object> addNewCenterReview(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewCenterReviewRequestData addNewCenterReviewRequestData) {
        return centerReviewService.addNewCenterReview(jwtToken, addNewCenterReviewRequestData);
    }

    @PutMapping
    @Transactional
    ResponseEntity<Object> editCenterReview(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid EditCenterReviewRequestData requestData) {
        return centerReviewService.editCenterReview(jwtToken, requestData);
    }

    @DeleteMapping
    @Transactional
    ResponseEntity<Object> deleteCenterReview(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid DeleteCenterReviewRequestData requestData) {
        return centerReviewService.deleteCenterReview(jwtToken, requestData);
    }

    @GetMapping(value = "/user/list")
    ResponseEntity<Object> getUserCenterReviewList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCenterReviewListRequestData requestData) {
        return centerReviewService.getUserCenterReviewList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/list")
    ResponseEntity<Object> getCenterOwnerReviewList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerReviewListRequestData requestData) {
        return centerReviewService.getCenterOwnerReviewList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/user/filter/list")
    ResponseEntity<Object> getCenterOwnerCenterReviewUserFilterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCenterReviewUserFilterListRequestData requestData) {
        return centerReviewService.getCenterOwnerCenterReviewUserFilterList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/center/filter/list")
    ResponseEntity<Object> getCenterOwnerCenterReviewCenterFilterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCenterReviewCenterFilterListRequestData requestData) {
        return centerReviewService.getCenterOwnerCenterReviewCenterFilterList(jwtToken, requestData);
    }

    @GetMapping(value = "/user/center/filter/list")
    ResponseEntity<Object> getUserCenterReviewCenterFilterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCenterReviewCenterFilterListRequestData requestData) {
        return centerReviewService.getUserCenterReviewCenterFilterList(jwtToken, requestData);
    }

    @GetMapping(value = "/user/center/list")
    ResponseEntity<Object> getUserCenterCenterReviewList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCenterCenterReviewListRequestData requestData) {
        return centerReviewService.getUserCenterCenterReviewList(jwtToken, requestData);
    }

    @GetMapping(value = "/admin/list")
    ResponseEntity<Object> getAdminCenterReviewList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            AdminCenterReviewListRequestData requestData) {
        return centerReviewService.getAdminCenterReviewList(jwtToken, requestData);
    }

    @PutMapping(value = "/admin/approve")
    @Transactional
    ResponseEntity<Object> adminApproveCenterReview(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody AdminApproveCenterReviewRequestData requestData) {
        return centerReviewService.adminApproveCenterReview(jwtToken, requestData);
    }

    @PutMapping(value = "/admin/denied")
    @Transactional
    ResponseEntity<Object> adminDeniedCenterReview(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody AdminDeniedCenterReviewRequestData requestData) {
        return centerReviewService.adminDeniedCenterReview(jwtToken, requestData);
    }
}
