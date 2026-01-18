package com.example.movie.api.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record UpdateMovieRequest (
    @NotBlank
    @Size(max = 250) String title,
    @Size(max = 3000) String overview,
    @NotNull
    @Min(1) Integer runtimeMinutes,
    @NotNull @Size(min = 1)
    List<@NotBlank String> genres,
    @NotNull
    LocalDate releaseDate,
    @Min(0) @Max(10) Short rating
){}
