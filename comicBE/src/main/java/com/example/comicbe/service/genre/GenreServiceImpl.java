package com.example.comicbe.service.genre;

import com.example.comicbe.jpa.entity.MangaGenre;
import com.example.comicbe.jpa.repository.GenreRepository;
import com.example.comicbe.jpa.repository.MangaRepository;
import com.example.comicbe.payload.dto.GenreDto;
import com.example.comicbe.service.GenreService;
import com.example.comicbe.utils.ConvertUtils;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Override
    public List<GenreDto> fetchAll(){
        List<MangaGenre> genres = genreRepository.findAll();
        return genres.stream().map(mangaGenre -> new GenreDto(mangaGenre)).toList();
    }

    @Override
    public GenreDto insert(GenreDto genreDto){
        if (genreRepository.existsByCodeIgnoreCase(genreDto.getCode().strip())){
            throw new ValidationException("code is duplicate");
        }
        MangaGenre mangaGenre = new MangaGenre();
        ConvertUtils.trimAllStringFields(genreDto);
        BeanUtils.copyProperties(genreDto, mangaGenre);
        mangaGenre = genreRepository.save(mangaGenre);
        return new GenreDto(mangaGenre);
    }

    @Override
    public GenreDto update(GenreDto genreDto){
        ConvertUtils.trimAllStringFields(genreDto);
        if (StringUtils.hasText(genreDto.getCode()) && genreRepository.existsByCodeIgnoreCase(genreDto.getCode().strip())){
            List<MangaGenre> mangaGenres = genreRepository.findAllByCodeIgnoreCase(genreDto.getCode().strip());
            if (mangaGenres.stream().anyMatch(mangaGenre -> mangaGenre.getId() != genreDto.getId() && genreDto.getCode().strip().equalsIgnoreCase(mangaGenre.getCode()))){
                throw new ValidationException("code is duplicate");
            }
        }
        MangaGenre mangaGenre = new MangaGenre();
        BeanUtils.copyProperties(genreDto, mangaGenre);
        mangaGenre = genreRepository.save(mangaGenre);
        return new GenreDto(mangaGenre);
    }

    @Override
    public void delete(Long id){
        Optional<MangaGenre> mangaGenre = genreRepository.findById(id);
        mangaGenre.ifPresent(mangaGenre1 -> {
            if (mangaRepository.existsByGenres(Set.of(mangaGenre1))){
                throw new ValidationException("hava manga use genre");
            }
            genreRepository.deleteById(id);
        });

    }




}
