package com.capstone.core.image;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImageController {

    private ImageService imageService;
}
