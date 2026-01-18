package com.example.movie.api.controller;

import com.example.movie.api.dto.CreateMovieRequest;
import com.example.movie.api.dto.MovieDetailsDTO;
import com.example.movie.api.dto.MovieListDTO;
import com.example.movie.api.dto.UpdateMovieRequest;
import com.example.movie.api.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDetailsDTO> findMovieDetailsById (@PathVariable Long id){
        MovieDetailsDTO movieDetailsDTO = service.findDetailsMovieById(id);
        return ResponseEntity.ok().body(movieDetailsDTO);
    }

    @PostMapping
    public ResponseEntity<MovieDetailsDTO> create(@RequestBody @Valid CreateMovieRequest request) {
        MovieDetailsDTO dto = service.createMovie(request);
        return ResponseEntity.status(201).body(dto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovieDetailsDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMovieRequest request
    ) {
        MovieDetailsDTO dto = service.updateMovie(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteMovie(id);
        return ResponseEntity.noContent().build(); // 204
    }


}
