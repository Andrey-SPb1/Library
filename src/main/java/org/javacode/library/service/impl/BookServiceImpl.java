package org.javacode.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.javacode.library.mapper.create.BookCreateEditMapper;
import org.javacode.library.mapper.response.BookResponseMapper;
import org.javacode.library.model.dto.create.BookCreateEditDto;
import org.javacode.library.model.dto.response.BookResponseDto;
import org.javacode.library.model.entity.Book;
import org.javacode.library.repository.BookRepository;
import org.javacode.library.service.AuthorService;
import org.javacode.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookResponseMapper bookResponseMapper;
    private final BookCreateEditMapper bookCreateEditMapper;

    @Override
    public Optional<BookResponseDto> findById(Integer id) {
        return bookRepository.findById(id)
                .map(bookResponseMapper::map);
    }

    @Override
    public Page<BookResponseDto> findAll(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return new PageImpl<BookResponseDto>(page.getContent().stream()
                .map(bookResponseMapper::map)
                .toList(), pageable, page.getTotalElements());
    }

    @Override
    public BookResponseDto create(BookCreateEditDto bookDto) {
        return Optional.of(bookDto)
                .map(bookCreateEditMapper::map)
                .map(book -> {
                    book.setAuthor(authorService.findOrCreateAuthor(book.getAuthor().getName()));
                    return book;
                })
                .map(bookRepository::save)
                .map(bookResponseMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<BookResponseDto> update(Integer id, BookCreateEditDto bookDto) {
        return bookRepository.findById(id)
                .map(book -> bookCreateEditMapper.map(bookDto, book))
                .map(book -> {
                    book.setAuthor(authorService.findOrCreateAuthor(book.getAuthor().getName()));
                    return book;
                })
                .map(bookRepository::saveAndFlush)
                .map(bookResponseMapper::map);
    }

    @Override
    public boolean delete(Integer id) {
        return bookRepository.findById(id)
                .map(entity -> {
                    bookRepository.deleteById(id);
                    bookRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
