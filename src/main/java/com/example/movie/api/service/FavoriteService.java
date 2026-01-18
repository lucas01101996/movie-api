package com.example.movie.api.service;

import com.example.movie.api.domain.entity.Movie;
import com.example.movie.api.domain.entity.User;
import com.example.movie.api.dto.MovieListDTO;
import com.example.movie.api.exception.ResourceNotFoundException;
import com.example.movie.api.repository.MovieRepository;
import com.example.movie.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;

    private String currentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private User currentUserOrThrow() {
        String username = currentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    @Transactional(readOnly = true)
    public List<MovieListDTO> listMyFavorites() {
        User user = currentUserOrThrow();
        return user.getFavoriteMovies()
                .stream()
                .map(MovieListDTO::new)
                .toList();
    }

    @Transactional
    public void addMyFavorite(Long movieId) {
        User user = currentUserOrThrow();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found: " + movieId));

        user.addFavorite(movie);
        userRepository.save(user);
    }

    @Transactional
    public void removeMyFavorite(Long movieId) {
        User user = currentUserOrThrow();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found: " + movieId));

        user.removeFavorite(movie);
        userRepository.save(user);
    }
}
