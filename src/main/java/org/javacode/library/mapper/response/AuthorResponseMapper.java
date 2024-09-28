package org.javacode.library.mapper.response;

import lombok.RequiredArgsConstructor;
import org.javacode.library.mapper.Mapper;
import org.javacode.library.model.dto.response.AuthorResponseDto;
import org.javacode.library.model.entity.Author;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorResponseMapper implements Mapper<Author, AuthorResponseDto> {

    @Override
    public AuthorResponseDto map(Author author) {
        return new AuthorResponseDto(author.getName());
    }
}
