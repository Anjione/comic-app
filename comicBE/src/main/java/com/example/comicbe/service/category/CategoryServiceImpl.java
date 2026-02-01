package com.example.comicbe.service.category;

import com.example.comicbe.jpa.entity.MangaCategory;
import com.example.comicbe.jpa.repository.CategoryRepository;
import com.example.comicbe.jpa.repository.MangaRepository;
import com.example.comicbe.payload.dto.CategoryDto;
import com.example.comicbe.service.CategoryService;
import com.example.comicbe.utils.ConvertUtils;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Override
    public List<CategoryDto> fetchAll(){
        List<MangaCategory> mangaCategories = categoryRepository.findAll();
        return mangaCategories.stream().map(mangaCategory -> new CategoryDto(mangaCategory)).toList();
    }

    @Override
    public CategoryDto insert(CategoryDto categoryDto){
        if (categoryRepository.existsByCodeIgnoreCase(categoryDto.getCode().strip())){
            throw new ValidationException("code is duplicate");
        }
        MangaCategory mangaCategory = new MangaCategory();
        ConvertUtils.trimAllStringFields(mangaCategory);
        BeanUtils.copyProperties(categoryDto, mangaCategory);
        mangaCategory = categoryRepository.save(mangaCategory);
        return new CategoryDto(mangaCategory);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto){
        ConvertUtils.trimAllStringFields(categoryDto);
        if (StringUtils.hasText(categoryDto.getCode()) && categoryRepository.existsByCodeIgnoreCase(categoryDto.getCode().strip())){
            List<MangaCategory> mangaCategories = categoryRepository.findAllByCodeIgnoreCase(categoryDto.getCode().strip());
            if (mangaCategories.stream().anyMatch(mangaCategory -> mangaCategory.getId() != categoryDto.getId() && categoryDto.getCode().strip().equalsIgnoreCase(mangaCategory.getCode()))){
                throw new ValidationException("code is duplicate");
            }
        }
        MangaCategory mangaCategory = new MangaCategory();
        BeanUtils.copyProperties(categoryDto, mangaCategory);
        mangaCategory = categoryRepository.save(mangaCategory);
        return new CategoryDto(mangaCategory);
    }

    @Override
    public void delete(Long id){
        Optional<MangaCategory> mangaCategory = categoryRepository.findById(id);
        mangaCategory.ifPresent(category -> {
            if (mangaRepository.existsByCategory(category)){
                throw new ValidationException("hava manga use genre");
            }
            mangaRepository.deleteById(id);
        });

    }
}
