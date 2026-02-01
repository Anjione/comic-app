package com.example.comicbe.controller.genre;

import com.example.comicbe.payload.dto.GenreDto;
import com.example.comicbe.payload.reponse.ResponseMessage;
import com.example.comicbe.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GengeController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseMessage getAll(){
        List<GenreDto> genreDtos = genreService.fetchAll();
        return new ResponseMessage<>(genreDtos);
    }


    @PostMapping
    public ResponseMessage insert(@Validated(GenreDto.Insert.class) GenreDto genreDto){
        GenreDto genreDtos = genreService.insert(genreDto);
        return new ResponseMessage<>("OK");
    }

    @PutMapping
    public ResponseMessage update(@Validated(GenreDto.Update.class) GenreDto genreDto){
        GenreDto genreDtos = genreService.update(genreDto);
        return new ResponseMessage<>("OK");
    }

}
