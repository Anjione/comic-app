package com.example.comicbe.controller.category;

import com.example.comicbe.payload.dto.CategoryDto;
import com.example.comicbe.payload.dto.GenreDto;
import com.example.comicbe.payload.reponse.ResponseMessage;
import com.example.comicbe.service.CategoryService;
import com.example.comicbe.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseMessage getAll(){
        List<CategoryDto> categoryDtos = service.fetchAll();
        return new ResponseMessage<>(categoryDtos);
    }


    @PostMapping
    public ResponseMessage insert(@Validated(CategoryDto.Insert.class) CategoryDto categoryDto){
        CategoryDto categoryDto1 = service.insert(categoryDto);
        return new ResponseMessage<>("OK");
    }

    @PutMapping
    public ResponseMessage update(@Validated(CategoryDto.Update.class) CategoryDto categoryDto){
        CategoryDto categoryDto1 = service.update(categoryDto);
        return new ResponseMessage<>("OK");
    }

}
