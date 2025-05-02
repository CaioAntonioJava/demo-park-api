package com.caioantonio.demo_park_api.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordDto {

    @NotBlank
    @Size(min = 6, max = 6)
    private String currentPassword;

    @NotBlank
    @Size(min = 6, max = 6)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 6)
    private String confirmPassword;

}
