package com.capstone.core.court;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.court.data.AddNewCourtRequestData;
import com.capstone.core.court.data.EditCourtRequestData;

import jakarta.transaction.Transactional;
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

    @GetMapping
    public ResponseEntity<Object> getCourtList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestParam Long centerId) {
        return courtService.getCourtList(jwtToken, centerId);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> editCourt(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid EditCourtRequestData editCourtRequestData) {
        return courtService.editCourt(jwtToken, editCourtRequestData);
    }
}
