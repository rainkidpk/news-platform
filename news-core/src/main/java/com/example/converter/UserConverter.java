package com.example.converter;

import com.example.dto.UserDTO;
import com.example.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToDto(UserEntity userEntity){
        UserDTO result = modelMapper.map(userEntity, UserDTO.class);
        return result;
    }

    public UserEntity convertToEntity(UserDTO userDTO){
        UserEntity result = modelMapper.map(userDTO, UserEntity.class);
        return result;
    }


}
