package com.caioantonio.demo_park_api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordDto {

    private String currentPassword;

    private String newPassword;
    
    private String confirmPassword;

}
