package com.example.movie.api.service;

import com.example.movie.api.domain.entity.Movie;
import com.example.movie.api.dto.MovieListDTO;
import com.example.movie.api.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Page<MovieListDTO> listMovies(Pageable pageable){
        Page<Movie> listMovie = movieRepository.findAll(pageable);

        return listMovie.map(MovieListDTO::new);
    }

    public Page<MovieListDTO> findByTitleOrOverview(String query, Pageable pageable){
        if (query == null || query.isBlank()){
            return listMovies(pageable);
        }
        Page<Movie> movies = movieRepository.findByTitleContainingIgnoreCaseOrOverviewContainingIgnoreCase(query, query,pageable);

        return  movies.map(MovieListDTO::new);
    }
}
