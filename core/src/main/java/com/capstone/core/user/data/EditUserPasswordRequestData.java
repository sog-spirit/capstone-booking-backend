package com.capstone.core.user.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserPasswordRequestData {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
