package com.example.converter;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO converToDto(CategoryEntity categoryEntity){
        CategoryDTO result = modelMapper.map(categoryEntity, CategoryDTO.class);
        return result;
    }

    public CategoryEntity converToEntity(CategoryDTO categoryDTO){
        CategoryEntity result = modelMapper.map(categoryDTO, CategoryEntity.class);
        return result;
    }

}
