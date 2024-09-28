package org.javacode.library.model.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookCreateEditDto(
        @Size(min = 1, max = 50)
        String title,
        @NotNull
        AuthorCreateEditDto author,
        @Size(max = 200)
        String description) {

}
