package com.example.movie.api.service;

import com.example.movie.api.domain.entity.Movie;
import com.example.movie.api.dto.MovieDetailsDTO;
import com.example.movie.api.dto.MovieListDTO;
import com.example.movie.api.dto.UpdateMovieRequest;
import com.example.movie.api.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public Page<MovieListDTO> listMovies(Pageable pageable){
        Page<Movie> listMovie = movieRepository.findAll(pageable);
        return listMovie.map(MovieListDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<MovieListDTO> findByTitleOrOverview(String query, Pageable pageable){
        if (query == null || query.isBlank()){
            return listMovies(pageable);
        }
        Page<Movie> movies = movieRepository.findByTitleContainingIgnoreCaseOrOverviewContainingIgnoreCase(query, query,pageable);

        return  movies.map(MovieListDTO::new);
    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO findDetailsMovieById(Long id){
        Optional<Movie> obj = movieRepository.findById(id);
        Movie entity = obj.orElseThrow(() -> new RuntimeException("Movie not found: " + id));
        return new MovieDetailsDTO(entity);
    }
    @Transactional
    public MovieDetailsDTO updateMovie(Long id, UpdateMovieRequest req) {
        Movie entity = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found: " + id));

        entity.setTitle(req.title());
        entity.setOverview(req.overview());
        entity.setRuntimeMinutes(req.runtimeMinutes());
        entity.setReleaseDate(req.releaseDate());
        entity.setRating(req.rating());

        entity.getGenres().clear();
        entity.getGenres().addAll(req.genres());

        Movie saved = movieRepository.save(entity);
        return new MovieDetailsDTO(saved);
    }


}
