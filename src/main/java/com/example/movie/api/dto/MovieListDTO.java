package com.example.movie.api.dto;

import com.example.movie.api.domain.entity.Movie;

public record MovieListDTO(
        Long id,
        String posterImage,
        String title,
        Integer releaseYear,
        Short rating

){
    public MovieListDTO(Movie movie){
        this(
                movie.getId(),
                null,
                movie.getTitle(),
                movie.getReleaseDate() != null ? movie.getReleaseDate().getYear() : null,
                movie.getRating()

        );
    }
}
