package com.ecom.dto;

import com.ecom.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Schema(description = "User creation request")
public class RegisterRequest {
    @NotBlank
    @Schema(example = "john")
    private String name;
    @Email
    @NotBlank private String email;
    @NotBlank private String password;
    private String phone;
    private LocalDate dateOfBirth;

}
