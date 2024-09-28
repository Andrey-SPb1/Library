package org.javacode.library.controller;

import lombok.RequiredArgsConstructor;
import org.javacode.library.model.entity.Author;
import org.javacode.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RequiredArgsConstructor
class AuthorControllerTest {

    private final MockMvc mockMvc;
    private final AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        loadTestData();
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/api/v1/author/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name").value("Marcel Proust"));
    }

    @Test
    void findByWrongId() throws Exception {
        mockMvc.perform(get("/api/v1/author/11"))
                .andExpect(status().isNotFound());
    }

    private void loadTestData() {
        if (authorRepository.count() != 0) return;

        Author author = Author.builder()
                .name("Marcel Proust")
                .build();

        authorRepository.save(author);
    }
}