package com.capstone.core.center;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Object> addNewCenter(@RequestBody @Valid AddNewCenterRequestData requestData) {
        return centerService.addNewCenter(requestData);
    }
}
