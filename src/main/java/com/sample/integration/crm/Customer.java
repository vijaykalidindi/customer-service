package com.sample.integration.crm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@ApiModel("Customer")
public class Customer {
    @Id
    @GeneratedValue
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private long id;

    @ApiModelProperty("User id")
    @NotBlank
    private String userId;

    @ApiModelProperty("First Name")
    @NotBlank
    @Size(max = 50)
    private String firstName;

    @ApiModelProperty("Last Name")
    @NotBlank
    @Size(max = 50)
    private String lastName;

    @ApiModelProperty(value = "Date of birth. example: 1953-10-29", example = "1953-10-29")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @ApiModelProperty("Residential address")
    @NotBlank
    @Size(max = 150)
    private String residentialAddress;

    @ApiModelProperty("Residential phone number")
    @NotBlank
    @Size(max = 15)
    private String residentialPhone;

    @ApiModelProperty("Email address")
    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    @ApiModelProperty("Work address")
    @NotBlank
    @Size(max = 150)
    private String workAddress;

    @ApiModelProperty("Work phone number")
    @NotBlank
    @Size(max = 15)
    private String workPhone;

}
