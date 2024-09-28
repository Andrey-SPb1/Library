package org.javacode.library.mapper.response;

import lombok.RequiredArgsConstructor;
import org.javacode.library.mapper.Mapper;
import org.javacode.library.model.dto.response.BookResponseDto;
import org.javacode.library.model.entity.Book;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookResponseMapper implements Mapper<Book, BookResponseDto> {

    private final AuthorResponseMapper authorResponseMapper;

    @Override
    public BookResponseDto map(Book book) {
        return new BookResponseDto(
                book.getTitle(),
                authorResponseMapper.map(book.getAuthor()),
                book.getDescription()
        );
    }
}
