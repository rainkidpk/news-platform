package com.example.service;

import com.example.dto.NewDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INewService {
    List<NewDTO> getAll();
    List<NewDTO> getNews(String searchValue, Pageable pageable);
    int getTotalItems(String searchValue);
    NewDTO insert(NewDTO newDTO);
    NewDTO update(NewDTO updateNew, long id);
    NewDTO findNewById(long id);
    void delete(long[] ids);
}
