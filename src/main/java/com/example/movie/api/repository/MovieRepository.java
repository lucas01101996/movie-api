package com.example.movie.api.repository;

import com.example.movie.api.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository  extends JpaRepository<Movie, Long> {
}
