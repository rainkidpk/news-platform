package com.example.service.impl;

import com.example.converter.NewConverter;
import com.example.dto.NewDTO;
import com.example.entity.NewEntity;
import com.example.repository.CategoryRepository;
import com.example.repository.NewRepository;
import com.example.service.INewService;
import com.example.utils.StringGenerate;
import com.example.utils.UploadFileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewService implements INewService {

    @Autowired
    private NewRepository newRepository;

    @Autowired
    private NewConverter newConverter;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<NewDTO> getAll() {
        List<NewDTO> newDTOS = new ArrayList<>();
        List<NewEntity> newEntities = newRepository.findAll();
        for (NewEntity item : newEntities) {
            NewDTO newDTO = new NewDTO();
            newDTO.setTitle(item.getTitle());
            newDTO.setContent(item.getContent());
            newDTOS.add(newDTO);
        }
        return newDTOS;
    }

    @Override
    public List<NewDTO> getNews(String searchValue, Pageable pageable) {
        List<NewDTO> result = new ArrayList<>();
        Page<NewEntity> newsPage = null;
        if(searchValue != null){
            newsPage = newRepository.findByTitleContainingIgnoreCase(searchValue, pageable);
        } else {
            newsPage = newRepository.findAll(pageable);
        }
        for (NewEntity item : newsPage.getContent()) {
            NewDTO newDTO = newConverter.convertToDto(item);
            result.add(newDTO);
        }
        return result;
    }

    @Override
    public int getTotalItems(String searchValue) {
        int totalItems = 0;
        if(searchValue != null){
            totalItems = (int) newRepository.countByTitleContainingIgnoreCase(searchValue);
        } else {
            totalItems = (int) newRepository.count();
        }
        return totalItems;
    }

    @Override
    @Transactional
    public NewDTO insert(NewDTO newDTO) {
        String thumbnail = "/fileupload" + File.separator + "thumbnail" + File.separator + newDTO.getImageName();
        newDTO.setThumbnail(thumbnail);
        UploadFileUtils.writeOrUpdateFileByBase64("thumbnail", newDTO.getThumbnailBase64(), newDTO.getImageName());
        NewEntity newEntity = newConverter.convertToEntity(newDTO);
        newEntity.setCode(StringGenerate.generateValue(5));
        newEntity.setCategory(categoryRepository.findOneByCode(newDTO.getCategoryCode()));
        newEntity.setView(0);
        newEntity = newRepository.save(newEntity);
        return newConverter.convertToDto(newEntity);
    }

    @Override
    public NewDTO update(NewDTO updateNew, long id) {
        NewEntity oldNew = newRepository.findOne(id);
        oldNew.setTitle(updateNew.getTitle());
        oldNew.setContent(updateNew.getContent());
        oldNew.setShortDescription(updateNew.getShortDescription());
        oldNew.setCategory(categoryRepository.findOneByCode(updateNew.getCategoryCode()));
        if (StringUtils.isNotEmpty(updateNew.getImageName())){
            String thumbnail = "/fileupload" + File.separator + "thumbnail" + File.separator + updateNew.getImageName();
            oldNew.setThumbnail(thumbnail);
            UploadFileUtils.writeOrUpdateFileByBase64("thumbnail", updateNew.getThumbnailBase64(), updateNew.getImageName());
        }
        oldNew = newRepository.save(oldNew);
        return newConverter.convertToDto(oldNew);
    }

    @Override
    public NewDTO findNewById(long id) {
        NewEntity newEntity = newRepository.findOne(id);
        NewDTO newDTO = newConverter.convertToDto(newEntity);
        newDTO.setCategoryCode(newEntity.getCategory().getCode());
        newDTO.setCategories(categoryService.getCategories());
        return newDTO;
    }

    @Override
    public void delete(long[] ids) {
        for (long item : ids){
            newRepository.delete(item);
        }
    }


}
