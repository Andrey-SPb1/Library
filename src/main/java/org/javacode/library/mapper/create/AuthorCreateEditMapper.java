package org.javacode.library.mapper.create;

import org.javacode.library.mapper.Mapper;
import org.javacode.library.model.dto.create.AuthorCreateEditDto;
import org.javacode.library.model.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorCreateEditMapper implements Mapper<AuthorCreateEditDto, Author> {
    @Override
    public Author map(AuthorCreateEditDto author) {
        return Author.builder()
                .name(author.name())
                .build();
    }
}
