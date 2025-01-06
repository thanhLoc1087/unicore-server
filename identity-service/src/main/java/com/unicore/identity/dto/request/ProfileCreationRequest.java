package com.unicore.identity.dto.request;

import java.time.LocalDate;

import com.unicore.identity.validator.DateOfBirthConstraint;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreationRequest {
    String userId;
    String username;
    String email;
    String firstName;
    String lastName;

    @DateOfBirthConstraint(min = 6, message = "INVALID_DOB")
    LocalDate dob;

    String city;
}
