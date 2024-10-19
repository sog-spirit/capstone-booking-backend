package com.capstone.core.center;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.center.data.AddNewCenterRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/center")
@AllArgsConstructor
public class CenterController {

    private CenterService centerService;

    @PostMapping
    public ResponseEntity<Object> addNewCenter(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewCenterRequestData requestData) {
        return centerService.addNewCenter(jwtToken, requestData);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getCenterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize) {
        return centerService.getCenterList(jwtToken, pageNo, pageSize);
    }
}
