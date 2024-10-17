package com.capstone.core.user.data;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFormRequestData {
    @NotEmpty(message = "{error.username.required}")
    private String username;

    @NotEmpty(message =  "{error.password.required}")
//    @Min(value = 8, message = "{error.password.length}")
    private String password;

    @NotEmpty(message = "{error.email.required}")
    @Email(message = "{error.email.format}")
    private String email;

    @NotEmpty(message = "{error.firstName.required}")
    private String firstName;

    @NotEmpty(message = "{error.lastName.required}")
    private String lastName;

    @NotEmpty(message = "{error.phone.required}")
    private String phone;

    @Digits(integer = 1, fraction = 0, message = "{error.role.required}")
    private Long role;
}
