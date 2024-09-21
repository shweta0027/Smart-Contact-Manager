package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
     @NotBlank(message = "Name is required")
     @Size(min=2, message="Min 2 Characters are required")
     private String name;

     @NotBlank(message = "Email is required")
     @Email(message = "Invalid Email Address")
     private String email;

     @NotBlank(message = "Password id required")
     @Size(min=6, message = "Min 6 Characters are required")
     private String password;

     private String about;

     @Size(min=9,max=12, message = "Invalid Phone Number")
     private String phoneNumber;
}
