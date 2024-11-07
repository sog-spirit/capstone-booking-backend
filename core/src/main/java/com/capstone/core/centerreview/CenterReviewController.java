package com.capstone.core.centerreview;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.centerreview.data.AddNewCenterReviewRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/center-review")
@AllArgsConstructor
public class CenterReviewController {
    private CenterReviewService centerReviewService;

    @PostMapping
    ResponseEntity<Object> addNewCenterReview(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewCenterReviewRequestData addNewCenterReviewRequestData) {
        return centerReviewService.addNewCenterReview(jwtToken, addNewCenterReviewRequestData);
    }

    @GetMapping(value = "/list/user")
    ResponseEntity<Object> getUserCenterReviewList(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return centerReviewService.getUserCenterReviewList(jwtToken);
    }

    @GetMapping(value = "/list/center-owner")
    ResponseEntity<Object> getCenterOwnerReviewList(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return centerReviewService.getCenterOwnerReviewList(jwtToken);
    }
}
