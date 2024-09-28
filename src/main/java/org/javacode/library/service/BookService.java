package org.javacode.library.service;

import org.javacode.library.model.dto.create.BookCreateEditDto;
import org.javacode.library.model.dto.response.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<BookResponseDto> findById(Integer id);

    Page<BookResponseDto> findAll(Pageable pageable);

    BookResponseDto create(BookCreateEditDto user);

    Optional<BookResponseDto> update(Integer id, BookCreateEditDto user);

    boolean delete(Integer id);
}
