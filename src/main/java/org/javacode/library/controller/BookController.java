package org.javacode.library.controller;

import lombok.RequiredArgsConstructor;
import org.javacode.library.exception.ResourceNotFoundException;
import org.javacode.library.model.dto.create.BookCreateEditDto;
import org.javacode.library.model.dto.response.BookResponseDto;
import org.javacode.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public BookResponseDto getBook(@PathVariable Integer id) {
        return bookService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }

    @GetMapping("/all")
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto create(@Validated @RequestBody BookCreateEditDto book) {
        return bookService.create(book);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookResponseDto update(@PathVariable("id") Integer id,
                                  @Validated @RequestBody BookCreateEditDto book) {
        return bookService.update(id, book)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return bookService.delete(id) ? noContent().build() : notFound().build();
    }
}
