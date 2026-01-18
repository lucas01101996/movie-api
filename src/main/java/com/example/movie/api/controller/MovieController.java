package com.example.movie.api.controller;

import com.example.movie.api.dto.MovieListDTO;
import com.example.movie.api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/movies")
public class MovieController{

    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<Page<MovieListDTO>> findAll(Pageable pageable){
        Page<MovieListDTO> movieListDTOPage = service.listMovies(pageable);
        return ResponseEntity.ok().body(movieListDTOPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieListDTO>> findByTitleOrOverview(
            @RequestParam(required = false) String query
            ,Pageable pageable){
        Page<MovieListDTO> movieListDTOPage = service.findByTitleOrOverview(query, pageable);
        return ResponseEntity.ok().body(movieListDTOPage);
    }
}
