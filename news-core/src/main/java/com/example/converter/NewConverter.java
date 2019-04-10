package com.example.converter;

import com.example.dto.NewDTO;
import com.example.entity.NewEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewConverter {
    @Autowired
    private ModelMapper modelMapper;

    public NewDTO convertToDto(NewEntity newEntity){
        NewDTO result = modelMapper.map(newEntity, NewDTO.class);
        return result;
    }

    public NewEntity convertToEntity(NewDTO newDTO){
        NewEntity result = modelMapper.map(newDTO, NewEntity.class);
        return result;
    }

}
