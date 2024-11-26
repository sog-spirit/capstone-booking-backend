package com.capstone.core.center;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.center.data.request.AddNewCenterRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterDropdownRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterListRequestData;
import com.capstone.core.center.data.request.EditCenterRequestData;
import com.capstone.core.center.data.request.UserCenterListRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/center")
@AllArgsConstructor
public class CenterController {

    private CenterService centerService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> addNewCenter(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewCenterRequestData requestData) {
        return centerService.addNewCenter(jwtToken, requestData);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> editCenter(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid EditCenterRequestData editCenterRequestData) {
        return centerService.editCenter(jwtToken, editCenterRequestData);
    }

    @GetMapping(value = "/user/list")
    public ResponseEntity<Object> getUserCenterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCenterListRequestData requestData) {
        return centerService.getUserCenterList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/list")
    public ResponseEntity<Object> getCenterOwnerCenterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCenterListRequestData requestData) {
        return centerService.getCenterOwnerCenterList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/list/dropdown")
    public ResponseEntity<Object> getCenterOwnerCenterDropdownList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCenterDropdownRequestData requestData) {
        return centerService.getCenterOwnerCenterDropdownList(jwtToken, requestData);
    }
}
