package com.zhuzirui.brt.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Data
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String profileImage;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
    private Boolean isActive;
    private Boolean isVerified;
    private String userRole;
    private String preferences;
    private String bio;
    private Boolean isDeleted;
    private String code;
}
