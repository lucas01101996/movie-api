package com.example.movie.api.controller;

import com.example.movie.api.dto.MovieDetailsDTO;
import com.example.movie.api.dto.MovieListDTO;
import com.example.movie.api.dto.UpdateMovieRequest;
import com.example.movie.api.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService service;

    @Test
    void getMovies_shouldReturn200_withoutAuth() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<MovieListDTO> page = new PageImpl<>(
                List.of(new MovieListDTO(1L, null, "The Matrix", 1999, (short) 9)),
                pageable,
                1
        );

        Mockito.when(service.listMovies(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        Mockito.verify(service).listMovies(any(Pageable.class));
    }

    @Test
    void putMovie_shouldReturn401_whenNoAuth() throws Exception {
        String body = """
                {
                  "title": "The Matrix (Updated)",
                  "overview": "Updated overview",
                  "runtimeMinutes": 136,
                  "genres": ["Action","Sci-Fi"],
                  "releaseDate": "1999-03-31",
                  "rating": 9
                }
                """;

        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void putMovie_shouldReturn403_whenUserRole() throws Exception {
        String body = """
                {
                  "title": "The Matrix (Updated)",
                  "overview": "Updated overview",
                  "runtimeMinutes": 136,
                  "genres": ["Action","Sci-Fi"],
                  "releaseDate": "1999-03-31",
                  "rating": 9
                }
                """;

        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void putMovie_shouldReturn200_whenAdminRole() throws Exception {
        String body = """
                {
                  "title": "The Matrix (Updated)",
                  "overview": "Updated overview",
                  "runtimeMinutes": 136,
                  "genres": ["Action","Sci-Fi"],
                  "releaseDate": "1999-03-31",
                  "rating": 9
                }
                """;

        MovieDetailsDTO dto = Mockito.mock(MovieDetailsDTO.class);

        Mockito.when(service.updateMovie(eq(1L), any(UpdateMovieRequest.class))).thenReturn(dto);

        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        Mockito.verify(service).updateMovie(eq(1L), any(UpdateMovieRequest.class));
    }
}
