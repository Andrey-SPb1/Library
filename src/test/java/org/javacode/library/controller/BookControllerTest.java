package org.javacode.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.library.model.dto.create.AuthorCreateEditDto;
import org.javacode.library.model.dto.create.BookCreateEditDto;
import org.javacode.library.model.entity.Book;
import org.javacode.library.repository.BookRepository;
import org.javacode.library.service.AuthorService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerTest {

    private final MockMvc mockMvc;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        loadTestData();
    }

    @Test
    @Order(1)
    void getBook() throws Exception {
        mockMvc.perform(get("/api/v1/book/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("title").value("Anna Karenina"))
                .andExpect(jsonPath("description").value("Set in 19th-century Russia"))
                .andExpect(jsonPath("author.name").value("Leo Tolstoy"));
    }

    @Test
    @Order(2)
    void getAllBooks() throws Exception {
        mockMvc.perform(get("/api/v1/book/all?page=1&size=2"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("content.length()").value(2))
                .andExpect(jsonPath("content[0].title").value("The Hobbit"))
                .andExpect(jsonPath("content[0].description").value("Fantasy novel"))
                .andExpect(jsonPath("content[1].author.name").value("Lewis Carroll"));
    }


    @Test
    void create() throws Exception {
        BookCreateEditDto book =
                new BookCreateEditDto("The Great Gatsby",
                        new AuthorCreateEditDto("F. Scott Fitzgerald"),
                        "Set in the summer of 1922");

        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title").value("The Great Gatsby"));
    }

    @Test
    void updateWithInvalidParam() throws Exception {
        BookCreateEditDto book =
                new BookCreateEditDto("The Great Gatsby",
                        new AuthorCreateEditDto("F. Scott Fitzgerald"),
                        "Set in the summer of 1922");

        mockMvc.perform(put("/api/v1/book/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        String newDescription = """
                This epic high-fantasy novel centers around a modest hobbit
                who is entrusted with the task of destroying a powerful ring
                that could enable the dark lord to conquer the world.
                """;

        BookCreateEditDto book =
                new BookCreateEditDto("The Lord of the Rings",
                        new AuthorCreateEditDto("J.R.R. Tolkien"),
                        newDescription);

        mockMvc.perform(put("/api/v1/book/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("title").value("The Lord of the Rings"))
                .andExpect(jsonPath("author.name").value("J.R.R. Tolkien"))
                .andExpect(jsonPath("description").value(newDescription));
    }

    @Test
    void deleteTest() throws Exception {
        Book book = Book.builder()
                .title("test")
                .description("test")
                .author(authorService.findOrCreateAuthor("test"))
                .build();

        Integer bookId = bookRepository.save(book).getId();

        mockMvc.perform(delete("/api/v1/book/" + bookId))
                .andExpect(status().isNoContent());

    }

    private void loadTestData() {
        if (bookRepository.count() != 0) return;

        Book book1 = Book.builder()
                .title("Anna Karenina")
                .description("Set in 19th-century Russia")
                .author(authorService.findOrCreateAuthor("Leo Tolstoy"))
                .build();

        Book book2 = Book.builder()
                .title("The Lord of the Rings")
                .description("Epic high-fantasy")
                .author(authorService.findOrCreateAuthor("J.R.R. Tolkien"))
                .build();

        Book book3 = Book.builder()
                .title("The Hobbit")
                .description("Fantasy novel")
                .author(authorService.findOrCreateAuthor("J.R.R. Tolkien"))
                .build();

        Book book4 = Book.builder()
                .title("Alice's Adventures in Wonderland")
                .description("This novel follows the story of a young girl named Alice")
                .author(authorService.findOrCreateAuthor("Lewis Carroll"))
                .build();

        Book book5 = Book.builder()
                .title("The Odyssey")
                .description("Epic poem")
                .author(authorService.findOrCreateAuthor("Homer"))
                .build();

        bookRepository.saveAll(List.of(book1, book2, book3, book4, book5));
    }
}