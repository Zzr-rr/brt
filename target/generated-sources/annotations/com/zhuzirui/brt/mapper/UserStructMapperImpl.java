package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.UserDTO;
import com.zhuzirui.brt.model.entity.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T00:00:28+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UserStructMapperImpl implements UserStructMapper {

    @Override
    public UserDTO entityToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setAddress( user.getAddress() );
        userDTO.setBio( user.getBio() );
        userDTO.setCity( user.getCity() );
        userDTO.setCountry( user.getCountry() );
        userDTO.setCreatedAt( user.getCreatedAt() );
        userDTO.setDateOfBirth( user.getDateOfBirth() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setGender( user.getGender() );
        userDTO.setIsActive( user.getIsActive() );
        userDTO.setIsDeleted( user.getIsDeleted() );
        userDTO.setIsVerified( user.getIsVerified() );
        userDTO.setLastLogin( user.getLastLogin() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setPhoneNumber( user.getPhoneNumber() );
        userDTO.setPostalCode( user.getPostalCode() );
        userDTO.setPreferences( user.getPreferences() );
        userDTO.setProfileImage( user.getProfileImage() );
        userDTO.setUpdatedAt( user.getUpdatedAt() );
        userDTO.setUserId( user.getUserId() );
        userDTO.setUserRole( user.getUserRole() );
        userDTO.setUsername( user.getUsername() );

        return userDTO;
    }

    @Override
    public User dtoToEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setAddress( dto.getAddress() );
        user.setBio( dto.getBio() );
        user.setCity( dto.getCity() );
        user.setCountry( dto.getCountry() );
        user.setCreatedAt( dto.getCreatedAt() );
        user.setDateOfBirth( dto.getDateOfBirth() );
        user.setEmail( dto.getEmail() );
        user.setFirstName( dto.getFirstName() );
        user.setGender( dto.getGender() );
        user.setIsActive( dto.getIsActive() );
        user.setIsDeleted( dto.getIsDeleted() );
        user.setIsVerified( dto.getIsVerified() );
        user.setLastLogin( dto.getLastLogin() );
        user.setLastName( dto.getLastName() );
        user.setPhoneNumber( dto.getPhoneNumber() );
        user.setPostalCode( dto.getPostalCode() );
        user.setPreferences( dto.getPreferences() );
        user.setProfileImage( dto.getProfileImage() );
        user.setUpdatedAt( dto.getUpdatedAt() );
        user.setUserId( dto.getUserId() );
        user.setUserRole( dto.getUserRole() );
        user.setUsername( dto.getUsername() );

        return user;
    }
}
