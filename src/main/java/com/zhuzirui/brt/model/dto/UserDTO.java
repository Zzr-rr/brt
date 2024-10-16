package com.zhuzirui.brt.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
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
@Getter
@Setter
@TableName("user")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增用户唯一ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名，唯一，用于登录
     */
    @TableField("username")
    private String username;

    /**
     * 邮箱，唯一，用于登录和用户通知
     */
    @TableField("email")
    private String email;

    /**
     * 密码的哈希值，用于安全登录
     */
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 手机号码，用于验证或通知
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 用户头像的URL路径
     */
    @TableField("profile_image")
    private String profileImage;

    /**
     * 用户名（名）
     */
    @TableField("first_name")
    private String firstName;

    /**
     * 用户名（姓）
     */
    @TableField("last_name")
    private String lastName;

    /**
     * 生日
     */
    @TableField("date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 用户地址，用于送货或联系
     */
    @TableField("address")
    private String address;

    /**
     * 用户所在城市
     */
    @TableField("city")
    private String city;

    /**
     * 用户所在国家
     */
    @TableField("country")
    private String country;

    /**
     * 邮编
     */
    @TableField("postal_code")
    private String postalCode;

    /**
     * 账户创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 账户最近一次更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 最近登录时间，用于活动监控
     */
    @TableField("last_login")
    private LocalDateTime lastLogin;

    /**
     * 账户是否激活
     */
    @TableField("is_active")
    private Boolean isActive;

    /**
     * 账户是否通过邮箱或手机验证
     */
    @TableField("is_verified")
    private Boolean isVerified;

    /**
     * 角色：管理员、普通用户、版主
     */
    @TableField("user_role")
    private String userRole;

    /**
     * 用户偏好设置（如语言、通知设置等）
     */
    @TableField("preferences")
    private String preferences;

    /**
     * 用户简介
     */
    @TableField("bio")
    private String bio;

    /**
     * 账户是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
