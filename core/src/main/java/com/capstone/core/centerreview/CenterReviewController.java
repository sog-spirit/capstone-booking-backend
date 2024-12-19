package com.capstone.core.centerreview;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.centerreview.data.request.AddNewCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.CenterOwnerCenterReviewCenterFilterListRequestData;
import com.capstone.core.centerreview.data.request.CenterOwnerCenterReviewUserFilterListRequestData;
import com.capstone.core.centerreview.data.request.CenterOwnerReviewListRequestData;
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
}
