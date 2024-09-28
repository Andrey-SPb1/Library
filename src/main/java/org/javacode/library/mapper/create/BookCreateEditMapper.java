package org.javacode.library.mapper.create;

import lombok.RequiredArgsConstructor;
import org.javacode.library.mapper.Mapper;
import org.javacode.library.model.dto.create.BookCreateEditDto;
import org.javacode.library.model.entity.Book;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookCreateEditMapper implements Mapper<BookCreateEditDto, Book> {

    private final AuthorCreateEditMapper authorCreateEditMapper;

    @Override
    public Book map(BookCreateEditDto book) {
        return Book.builder()
                .title(book.title())
                .author(authorCreateEditMapper.map(book.author()))
                .description(book.description())
                .build();
    }

    @Override
    public Book map(BookCreateEditDto bookCreateEditDto, Book book) {
        book.setTitle(bookCreateEditDto.title());
        book.setAuthor(authorCreateEditMapper.map(bookCreateEditDto.author()));
        book.setDescription(bookCreateEditDto.description());
        return book;
    }
}
