package org.javacode.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.javacode.library.mapper.response.AuthorResponseMapper;
import org.javacode.library.model.dto.response.AuthorResponseDto;
import org.javacode.library.model.entity.Author;
import org.javacode.library.repository.AuthorRepository;
import org.javacode.library.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorResponseMapper authorResponseMapper;

    @Override
    public Optional<AuthorResponseDto> findById(Integer id) {
        return authorRepository.findById(id)
                .map(authorResponseMapper::map);
    }

    @Override
    public Author findOrCreateAuthor(String name) {
        Author existingAuthor = authorRepository.findByName(name);
        if (existingAuthor != null) {
            return existingAuthor;
        } else {
            Author author = Author.builder()
                    .name(name)
                    .build();
            return authorRepository.save(author);
        }
    }
}
