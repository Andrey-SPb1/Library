package org.javacode.library.controller;

import lombok.RequiredArgsConstructor;
import org.javacode.library.exception.ResourceNotFoundException;
import org.javacode.library.model.dto.response.AuthorResponseDto;
import org.javacode.library.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{id}")
    public AuthorResponseDto findById(@PathVariable Integer id) {
        return authorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));
    }

}
