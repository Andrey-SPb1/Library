package org.javacode.library.service;

import org.javacode.library.model.dto.response.AuthorResponseDto;
import org.javacode.library.model.entity.Author;

import java.util.Optional;

public interface AuthorService {

    Optional<AuthorResponseDto> findById(Integer id);

    Author findOrCreateAuthor(String name);
}
