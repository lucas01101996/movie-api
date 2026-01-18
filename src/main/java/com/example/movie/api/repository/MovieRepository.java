package com.example.movie.api.repository;

import com.example.movie.api.domain.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MovieRepository  extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContainingIgnoreCaseOrOverviewContainingIgnoreCase(
            String title,
            String overview,
            Pageable pageable
    );

}
