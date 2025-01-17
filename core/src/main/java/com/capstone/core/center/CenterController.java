package com.capstone.core.center;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.center.data.request.AddNewCenterRequestData;
import com.capstone.core.center.data.request.AdminStatisticsCenterListRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterDropdownRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterListRequestData;
import com.capstone.core.center.data.request.CenterOwnerStatisticsCenterListRequestData;
import com.capstone.core.center.data.request.DeleteCenterRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterInfoRequestData;
import com.capstone.core.center.data.request.EditCenterRequestData;
import com.capstone.core.center.data.request.UserCenterListRequestData;
import com.capstone.core.center.data.request.UserCenterInfoRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/center")
@AllArgsConstructor
public class CenterController {

    private CenterService centerService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Object> addNewCenter(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @ModelAttribute @Valid AddNewCenterRequestData requestData) throws IOException {
        return centerService.addNewCenter(jwtToken, requestData);
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Object> editCenter(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @ModelAttribute @Valid EditCenterRequestData editCenterRequestData) throws IOException {
        return centerService.editCenter(jwtToken, editCenterRequestData);
    }

    @DeleteMapping(value = "/center-owner/closed")
    @Transactional
    ResponseEntity<Object> deleteCenter(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @ModelAttribute @Valid DeleteCenterRequestData requestData) {
        return centerService.deleteCenter(jwtToken, requestData);
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

    @GetMapping(value = "/center-owner/info")
    public ResponseEntity<Object> getCenterOwnerCenterInfo(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCenterInfoRequestData requestData) {
        return centerService.getCenterOwnerCenterInfo(jwtToken, requestData);
    }

    @GetMapping(value = "/user/info")
    public ResponseEntity<Object> getUserCenterInfo(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCenterInfoRequestData requestData) {
        return centerService.getUserCenterInfo(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/statistics/center/list")
    ResponseEntity<Object> getCenterOwnerStatisticsCenterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerStatisticsCenterListRequestData requestData) {
        return centerService.getCenterOwnerStatisticsCenterList(jwtToken, requestData);
    }

    @GetMapping(value = "/admin/statistics/center/list")
    ResponseEntity<Object> getAdminStatisticsCenterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            AdminStatisticsCenterListRequestData requestData) {
        return centerService.getAdminStatisticsCenterList(jwtToken, requestData);
    }
}
