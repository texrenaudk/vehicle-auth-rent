package com.renaudk.user_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.renaudk.user_service.utils.AppConstants;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class UserDto {

@NotBlank(message = "vous devez definir un first name")
@JsonProperty("first_name")
    private String firstName;


    @NotBlank(message = "vous devez definir un last name")
    @JsonProperty("last_name")
    private String lastName;
    @Pattern(regexp = AppConstants.EMAIL_REG_EXPRESSION, message = "vous devez inserer un mail valide")
    private String email;
}
