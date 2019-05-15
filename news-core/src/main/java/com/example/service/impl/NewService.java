package com.example.service.impl;

import com.example.constant.SystemConstant;
import com.example.converter.CategoryConverter;
import com.example.converter.NewConverter;
import com.example.dto.CategoryDTO;
import com.example.dto.HomeDTO;
import com.example.dto.NewDTO;
import com.example.entity.CategoryEntity;
import com.example.entity.NewEntity;
import com.example.repository.CategoryRepository;
import com.example.repository.NewRepository;
import com.example.service.INewService;
import com.example.utils.StringGenerate;
import com.example.utils.UploadFileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CategoryConverter categoryConverter;

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
        if (searchValue != null) {
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
        if (searchValue != null) {
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
        if (StringUtils.isNotEmpty(updateNew.getImageName())) {
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
        for (long item : ids) {
            newRepository.delete(item);
        }
    }

    @Override
    public Map<String, String> getMessageResponse(String message) {
        Map<String, String> result = new HashMap<>();
        if (message != null) {
            if (message.equals(SystemConstant.INSERT_SUCCESS)) {
                result.put(SystemConstant.ALERT, "success");
                result.put(SystemConstant.MESSAGE_RESPONSE, "Thêm thành công");
            } else if (message.equals(SystemConstant.UPDATE_SUCCESS)) {
                result.put(SystemConstant.ALERT, "success");
                result.put(SystemConstant.MESSAGE_RESPONSE, "Cập nhật thành công");
            } else if (message.equals(SystemConstant.DELETE_SUCCESS)) {
                result.put(SystemConstant.ALERT, "success");
                result.put(SystemConstant.MESSAGE_RESPONSE, "Xóa thành công");
            } else if (message.equals(SystemConstant.ERROR_SYSTEM)) {
                result.put(SystemConstant.ALERT, "danger");
                result.put(SystemConstant.MESSAGE_RESPONSE, "Có lỗi xảy ra");
            }
        }
        return result;
    }

    @Override
    public HomeDTO getHomeDetail() {
        HomeDTO result = new HomeDTO();
        result.setTopViews(getTopViews());
        result.setTopNewDates(getTopNewDates());
        result.setCategories(getCategories());
        return result;
    }

    @Override
    public NewDTO getNewByCategory(String title, long categoryId, String code) {
        NewDTO newDTO = new NewDTO();
        newDTO.setCategoryId(categoryId);
        newDTO.setCategoryCode(code);
        if(StringUtils.isNotBlank(title)){
            newDTO.setPage(1);
        }

        Pageable pageable = new PageRequest(newDTO.getPage() - 1, newDTO.getMaxPageItems());
        CategoryEntity categoryEntity = categoryRepository.findOne(categoryId);
        newDTO.setListResult(findNewByCategory(categoryEntity, pageable, title));
        newDTO.setTotalItems(getTotalItems(categoryEntity, title));
        newDTO.setTotalPages((int) Math.ceil((double) newDTO.getTotalItems()/newDTO.getMaxPageItems()));
        return newDTO;
    }

    @Override
    public NewDTO getNewDetail(long id) {
        NewEntity newEntity = newRepository.findOne(id);
        newEntity.setView(newEntity.getView() + 1);
        newEntity = newRepository.save(newEntity);
        return newConverter.convertToDto(newEntity);
    }

    private int getTotalItems(CategoryEntity categoryEntity, String title) {
        int total = 0;
        if(title != null) {
            total = (int) newRepository.countByCategoryAndTitleContainingIgnoreCase(categoryEntity, title);
        } else {
            total = (int) newRepository.countByCategory(categoryEntity);
        }
        return total;
    }

    private List<NewDTO> findNewByCategory(CategoryEntity categoryEntity, Pageable pageable, String title) {
        List<NewEntity> newEntities = new ArrayList<>();
        if(title != null){
            newEntities = newRepository.findByCategoryAndTitleContainingIgnoreCase(categoryEntity, title, pageable).getContent();
        } else {
            newEntities = newRepository.findByCategory(categoryEntity,  pageable).getContent();
        }
        List<NewDTO> newDTOs = new ArrayList<>();
        newEntities.forEach(item -> {
            newDTOs.add(newConverter.convertToDto(item));
        });
        return newDTOs;
    }

    private List<CategoryDTO> getCategories() {
        List<CategoryEntity> entities = categoryRepository.findAll();
        List<CategoryDTO> dtos = new ArrayList<>();
        entities.forEach(item -> {
            dtos.add(categoryConverter.converToDto(item));
        });
        return dtos;
    }

    private List<NewDTO> getTopNewDates() {
        return getNewByPropertyOrderDesc(3, "createdDate");
    }

    private List<NewDTO> getTopViews() {
        return getNewByPropertyOrderDesc(3, "view");
    }

    private List<NewDTO> getNewByPropertyOrderDesc(int maxItem, String field){
        Pageable pageable = new PageRequest(0, maxItem, Sort.Direction.DESC, field);
        //Pageable pageable1 = new PageRequest();
        Page<NewEntity> newsPage = newRepository.findAll(pageable);
        List<NewDTO> news = new ArrayList<>();
        newsPage.getContent().forEach(item -> {
            news.add(newConverter.convertToDto(item));
        });
        return news;
    }


}
