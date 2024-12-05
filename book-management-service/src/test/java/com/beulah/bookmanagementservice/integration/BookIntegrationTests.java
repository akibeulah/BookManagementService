package com.beulah.bookmanagementservice.integration;

import com.beulah.bookmanagementservice.dto.NewBookRequest;
import com.beulah.bookmanagementservice.enums.BookType;
import com.beulah.bookmanagementservice.models.Book;
import com.beulah.bookmanagementservice.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class BookIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Book testBook;
    private NewBookRequest validBookRequest;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        testBook = Book.builder()
                .title("Effective Java")
                .isbn("232323232323223")
                .bookType(BookType.HARD_COVER)
                .price(5274)
                .publishDate(LocalDate.now())
                .author("Joshua Bloch")
                .build();

        validBookRequest = new NewBookRequest();
        validBookRequest.setTitle("Effective Java");
        validBookRequest.setIsbn("232323232323224");
        validBookRequest.setBookType("HARD_COVER");
        validBookRequest.setPrice(5274);
        validBookRequest.setPublishDate(LocalDate.now().toString());
        validBookRequest.setAuthor("Joshua Bloch");
    }

    @Test
    void shouldCreateBook() throws Exception {
        NewBookRequest request = new NewBookRequest();
        request.setTitle("New Test Book");
        request.setIsbn("0987654321");
        request.setBookType("HARD_COVER");
        request.setPrice(5274);
        request.setPublishDate(LocalDate.now().toString());
        request.setAuthor("New Test Author");

        MvcResult result = mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Book created successfully"))
                .andExpect(jsonPath("$.data.title").value("New Test Book"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains("New Test Book"));
    }

    @Test
    void shouldUpdateExistingBook() throws Exception {
        Book savedBook = bookRepository.save(testBook);

        NewBookRequest updateRequest = new NewBookRequest();
        updateRequest.setId(savedBook.getId());
        updateRequest.setTitle("Updated Book Title");
        updateRequest.setIsbn(savedBook.getIsbn());
        updateRequest.setBookType("SOFT_COVER");
        updateRequest.setPrice(5500);
        updateRequest.setPublishDate(LocalDate.now().toString());
        updateRequest.setAuthor("Updated Author");

        mockMvc.perform(put("/api/v1/books/{id}", savedBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book updated successfully"))
                .andExpect(jsonPath("$.data.title").value("Updated Book Title"));
    }

    @Test
    void shouldRetrieveBooksWithPagination() throws Exception {
        List<Book> books = Arrays.asList(
                Book.builder().title("Book 1").isbn("111").price(1000).bookType(BookType.E_BOOK).publishDate(LocalDate.parse("2003-05-05")).build(),
                Book.builder().title("Book 2").isbn("222").price(1000).bookType(BookType.E_BOOK).publishDate(LocalDate.parse("2003-05-05")).build(),
                Book.builder().title("Book 3").isbn("333").price(1000).bookType(BookType.E_BOOK).publishDate(LocalDate.parse("2003-05-05")).build()
        );
        bookRepository.saveAll(books);

        mockMvc.perform(get("/api/v1/books")
                        .param("page", "0")
                        .param("perPage", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.metadata.totalElements").value(3))
                .andExpect(jsonPath("$.metadata.totalPages").value(2));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        Book savedBook = bookRepository.save(testBook);

        mockMvc.perform(delete("/api/v1/books/{id}", savedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book deleted successfully"));

        assertFalse(bookRepository.existsById(savedBook.getId()));
    }

    @Test
    void shouldHandleValidationErrors() throws Exception {
        NewBookRequest invalidRequest = new NewBookRequest();

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to Create Book"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void shouldSearchBooksByTitle() throws Exception {
        bookRepository.saveAll(Arrays.asList(
                Book.builder()
                        .title("Spring Boot: Up and Running")
                        .bookType(BookType.E_BOOK)
                        .isbn("111")
                        .price(5555)
                        .publishDate(LocalDate.parse("2024-10-10"))
                        .author("Mark Heckler")
                        .build(),
                Book.builder()
                        .title("Spring Boot 3 and Spring Framework 6")
                        .bookType(BookType.E_BOOK)
                        .isbn("222")
                        .price(5555)
                        .publishDate(LocalDate.parse("2024-10-10"))
                        .author("Christian Ullenboom")
                        .build()
        ));

        mockMvc.perform(get("/api/v1/books")
                        .param("query", "Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    void shouldFailToCreateBookWithDuplicateISBN() throws Exception {
        String duplicateIsbn = "232323232323223";
        Book existingBook = bookRepository.save(
                Book.builder()
                        .title("Existing Book")
                        .isbn(duplicateIsbn)
                        .bookType(BookType.SOFT_COVER)
                        .price(4000)
                        .publishDate(LocalDate.now())
                        .author("Some Author")
                        .build()
        );
        validBookRequest.setIsbn(duplicateIsbn);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Duplicate Book Found"))
                .andExpect(jsonPath("$.errors[0]").exists());
    }

    @Test
    void shouldFailToCreateBookWithInvalidBookType() throws Exception {
        validBookRequest.setBookType("INVALID_TYPE");

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to Create Book"))
                .andExpect(jsonPath("$.errors[0]").exists());
    }

    @Test
    void shouldFailToCreateBookWithInvalidDateFormat() throws Exception {
        validBookRequest.setPublishDate("invalid-date");

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to Create Book"))
                .andExpect(jsonPath("$.errors[0]").exists());
    }

    @Test
    void shouldFailToUpdateNonExistentBook() throws Exception {
        int nonExistentBookId = 999;

        mockMvc.perform(put("/api/v1/books/{id}", nonExistentBookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book Not Found"))
                .andExpect(jsonPath("$.errors[0]").exists());
    }

    @Test
    void shouldFailToDeleteNonExistentBook() throws Exception {
        int nonExistentBookId = 999;

        mockMvc.perform(delete("/api/v1/books/{id}", nonExistentBookId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book Not Found"))
                .andExpect(jsonPath("$.errors[0]").exists());
    }

    @Test
    void shouldFailToCreateBookWithMissingRequiredFields() throws Exception {
        NewBookRequest incompleteRequest = new NewBookRequest();
        incompleteRequest.setTitle("Incomplete Book");

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incompleteRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to Create Book"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(greaterThan(0)));
    }

    @Test
    void shouldFailToCreateBookWithNegativePrice() throws Exception {
        validBookRequest.setPrice(-100);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to Create Book"))
                .andExpect(jsonPath("$.errors[0]").exists());
    }

    @Test
    void shouldFailToRetrieveBookWithInvalidId() throws Exception {
        int invalidBookId = -1;

        mockMvc.perform(get("/api/v1/books/{id}", invalidBookId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book Not Found"))
                .andExpect(jsonPath("$.errors[0]").exists());
    }

    @Test
    void shouldHandlePaginationOutOfBounds() throws Exception {
        bookRepository.deleteAll();

        mockMvc.perform(get("/api/v1/books")
                        .param("page", "10000")
                        .param("perPage", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("No books found"));
    }
}