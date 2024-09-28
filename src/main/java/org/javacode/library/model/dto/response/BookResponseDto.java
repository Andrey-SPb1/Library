package org.javacode.library.model.dto.response;

public record BookResponseDto(
        String title,
        AuthorResponseDto author,
        String description) {
}
