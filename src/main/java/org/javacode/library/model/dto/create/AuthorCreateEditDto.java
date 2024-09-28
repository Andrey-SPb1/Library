package org.javacode.library.model.dto.create;

import jakarta.validation.constraints.Size;

public record AuthorCreateEditDto(
        @Size(min = 5, max = 50)
        String name) {
}
