package com.capstone.core.court;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.court.data.request.AddNewCourtRequestData;
import com.capstone.core.court.data.request.CenterOwnerCourtListRequestData;
import com.capstone.core.court.data.request.EditCourtRequestData;
import com.capstone.core.court.data.request.UserCenterCourtListRequestData;
import com.capstone.core.court.data.request.UserCourtDropdownListRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/court")
@AllArgsConstructor
public class CourtController {

    private CourtService courtService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> addNewCourt(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewCourtRequestData addNewCourtRequestData) {
        return courtService.addNewCourt(jwtToken, addNewCourtRequestData);
    }

    @GetMapping(value = "/user/center/list")
    public ResponseEntity<Object> getUserCenterCourtList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCenterCourtListRequestData requestData) {
        return courtService.getUserCenterCourtList(jwtToken, requestData);
    }

    @GetMapping(value = "/user/dropdown/list")
    ResponseEntity<Object> getUserCourtDropdownList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCourtDropdownListRequestData requestData) {
        return courtService.getUserCourtDropdownList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/list")
    ResponseEntity<Object> getCenterOwnerCourtList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtListRequestData requestData) {
        return courtService.getCenterOwnerCourtList(jwtToken, requestData);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> editCourt(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid EditCourtRequestData editCourtRequestData) {
        return courtService.editCourt(jwtToken, editCourtRequestData);
    }
}
