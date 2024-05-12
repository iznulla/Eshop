package com.eshop.dto.user;



import com.eshop.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
    @NotEmpty
    private List<Role> roles;
}
