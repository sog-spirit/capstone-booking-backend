package com.capstone.core.center;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.center.data.AddNewCenterRequestData;
import com.capstone.core.center.data.EditCenterRequestData;

import jakarta.transaction.Transactional;
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

    @GetMapping(value = "/list", params = {"pageNo", "pageSize"})
    public ResponseEntity<Object> getCenterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize) {
        return centerService.getCenterList(jwtToken, pageNo, pageSize);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getCenterList(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return centerService.getCenterList(jwtToken);
    }

    @GetMapping(value = "/list", params = {"query"})
    public ResponseEntity<Object> getCenterDropdownList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestParam String query) {
        return centerService.getCenterDropdownList(jwtToken, query);
    }
}
