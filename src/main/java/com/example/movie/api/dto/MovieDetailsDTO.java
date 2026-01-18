package com.example.movie.api.dto;

import com.example.movie.api.domain.entity.Movie;

import java.time.LocalDate;
import java.util.List;

public record MovieDetailsDTO(
        String posterImage,
        String title,
        String overview,
        Integer runTimeMinutes,
        List<String> genres,
        LocalDate releaseDate,
        Short rating
) {
    public MovieDetailsDTO(Movie movie){
        this(
                null,
                movie.getTitle(),
                movie.getOverview(),
                movie.getRuntimeMinutes(),
                movie.getGenres(),
                movie.getReleaseDate(),
                movie.getRating()
        );
    }
}
