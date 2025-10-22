package com.renaudk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class UserDto {


    @JsonProperty("first_name")
    private String firstName;



    @JsonProperty("last_name")
    private String lastName;

    private String email;
}
