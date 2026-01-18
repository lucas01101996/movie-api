package com.example.movie.api.controller;

import com.example.movie.api.dto.MovieListDTO;
import com.example.movie.api.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/me/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService service;

    @GetMapping
    public ResponseEntity<List<MovieListDTO>> listMyFavorites() {
        return ResponseEntity.ok(service.listMyFavorites());
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<Void> addMyFavorite(@PathVariable Long movieId) {
        service.addMyFavorite(movieId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> removeMyFavorite(@PathVariable Long movieId) {
        service.removeMyFavorite(movieId);
        return ResponseEntity.noContent().build();
    }
}
