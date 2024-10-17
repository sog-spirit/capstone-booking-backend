package com.capstone.core.util.consts;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class MessageConstsUtil {
    public static Map<String, String> createValidationResultMessageMap(BindingResult bindingResult) {
        Map<String, String> validationResult = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            validationResult.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return validationResult;
    }
}
