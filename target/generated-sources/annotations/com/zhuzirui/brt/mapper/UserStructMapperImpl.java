package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.UserDTO;
import com.zhuzirui.brt.model.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T22:54:20+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class UserStructMapperImpl implements UserStructMapper {

    @Override
    public UserDTO entityToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUserId( user.getUserId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPhoneNumber( user.getPhoneNumber() );
        userDTO.setProfileImage( user.getProfileImage() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setDateOfBirth( user.getDateOfBirth() );
        userDTO.setGender( user.getGender() );
        userDTO.setAddress( user.getAddress() );
        userDTO.setCity( user.getCity() );
        userDTO.setCountry( user.getCountry() );
        userDTO.setPostalCode( user.getPostalCode() );
        userDTO.setCreatedAt( user.getCreatedAt() );
        userDTO.setUpdatedAt( user.getUpdatedAt() );
        userDTO.setLastLogin( user.getLastLogin() );
        userDTO.setIsActive( user.getIsActive() );
        userDTO.setIsVerified( user.getIsVerified() );
        userDTO.setUserRole( user.getUserRole() );
        userDTO.setPreferences( user.getPreferences() );
        userDTO.setBio( user.getBio() );
        userDTO.setIsDeleted( user.getIsDeleted() );

        return userDTO;
    }

    @Override
    public User dtoToEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( dto.getUserId() );
        user.setUsername( dto.getUsername() );
        user.setEmail( dto.getEmail() );
        user.setPhoneNumber( dto.getPhoneNumber() );
        user.setProfileImage( dto.getProfileImage() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setDateOfBirth( dto.getDateOfBirth() );
        user.setGender( dto.getGender() );
        user.setAddress( dto.getAddress() );
        user.setCity( dto.getCity() );
        user.setCountry( dto.getCountry() );
        user.setPostalCode( dto.getPostalCode() );
        user.setCreatedAt( dto.getCreatedAt() );
        user.setUpdatedAt( dto.getUpdatedAt() );
        user.setLastLogin( dto.getLastLogin() );
        user.setIsActive( dto.getIsActive() );
        user.setIsVerified( dto.getIsVerified() );
        user.setUserRole( dto.getUserRole() );
        user.setPreferences( dto.getPreferences() );
        user.setBio( dto.getBio() );
        user.setIsDeleted( dto.getIsDeleted() );

        return user;
    }
}
