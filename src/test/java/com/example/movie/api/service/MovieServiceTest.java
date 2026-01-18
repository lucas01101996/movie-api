package com.example.movie.api.service;

import com.example.movie.api.domain.entity.Movie;
import com.example.movie.api.exception.ResourceNotFoundException;
import com.example.movie.api.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findDetailsMovieById_whenNotFound_shouldThrow() {
        Long id = 999L;
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findDetailsMovieById(id));

        verify(movieRepository, times(1)).findById(id);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    void findByTitleOrOverview_whenQueryBlank_shouldFallbackToListMovies() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        Movie m1 = new Movie("Matrix", "desc", 136, List.of("Action"), LocalDate.of(1999, 3, 31), (short) 9);
        Movie m2 = new Movie("Inception", "desc", 148, List.of("Sci-Fi"), LocalDate.of(2010, 7, 16), (short) 8);

        Page<Movie> page = new PageImpl<>(List.of(m1, m2), pageable, 2);
        when(movieRepository.findAll(pageable)).thenReturn(page);

        var result = service.findByTitleOrOverview("   ", pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(movieRepository, times(1)).findAll(pageable);
        verify(movieRepository, never()).findByTitleContainingIgnoreCaseOrOverviewContainingIgnoreCase(any(), any(), any());
    }

    @Test
    void listMovies_shouldMapToDtoPage() {
        Pageable pageable = PageRequest.of(0, 5);

        Movie m1 = new Movie("Matrix", "desc", 136, List.of("Action"), LocalDate.of(1999, 3, 31), (short) 9);
        Page<Movie> page = new PageImpl<>(List.of(m1), pageable, 1);

        when(movieRepository.findAll(pageable)).thenReturn(page);

        var dtoPage = service.listMovies(pageable);

        assertEquals(1, dtoPage.getTotalElements());
        assertEquals("Matrix", dtoPage.getContent().get(0).title());
        verify(movieRepository, times(1)).findAll(pageable);
    }

    @Test
    void deleteMovie_whenNotExists_shouldThrow() {
        Long id = 123L;
        when(movieRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteMovie(id));

        verify(movieRepository, times(1)).existsById(id);
        verify(movieRepository, never()).deleteById(anyLong());
    }
}
