package com.beulah.bookmanagementservice.controller;

import com.beulah.bookmanagementservice.dto.GenericResponse;
import com.beulah.bookmanagementservice.dto.NewBookRequest;
import com.beulah.bookmanagementservice.exceptions.BookAlreadyExistsException;
import com.beulah.bookmanagementservice.exceptions.BookDeletionException;
import com.beulah.bookmanagementservice.exceptions.InvalidBookTypeException;
import com.beulah.bookmanagementservice.exceptions.InvalidDateFormatException;
import com.beulah.bookmanagementservice.models.Book;
import com.beulah.bookmanagementservice.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "Create a new book", notes = "Adds a new book to the catalog")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book successfully created"),
            @ApiResponse(code = 400, message = "Invalid input or book type"),
            @ApiResponse(code = 409, message = "Book with same ISBN already exists"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<GenericResponse<Book>> createBook(
            @Valid @RequestBody NewBookRequest newBookRequest,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(
                        new GenericResponse<>("Failed to Create Book", errorMessages)
                );
            }

            return bookService.createNewBook(newBookRequest)
                    .map(b -> ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>("Book created successfully", b)))
                    .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        } catch (InvalidBookTypeException | InvalidDateFormatException e) {
            return ResponseEntity.badRequest().body(
                    new GenericResponse<>("Failed to Create Book", Collections.singletonList(e.getMessage()))
            );
        } catch (BookAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new GenericResponse<>("Duplicate Book Found", Collections.singletonList(e.getMessage()))
            );
        }
    }

    @ApiOperation(value = "Update an existing book", notes = "Updates a book's information by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully updated"),
            @ApiResponse(code = 400, message = "Invalid input or book type"),
            @ApiResponse(code = 404, message = "Book not found"),
            @ApiResponse(code = 409, message = "ISBN conflict with existing book"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<Book>> updateBook(
            @PathVariable int id,
            @Valid @RequestBody NewBookRequest newBookRequest,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(
                        new GenericResponse<>("Failed to Update Book", errorMessages)
                );
            }

            newBookRequest.setId(id);

            return bookService.updateBook(newBookRequest)
                    .map(b -> ResponseEntity.ok(new GenericResponse<>("Book updated successfully", b)))
                    .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        } catch (InvalidDateFormatException | InvalidBookTypeException e) {
            return ResponseEntity.badRequest().body(
                    new GenericResponse<>("Failed to Update Book", Collections.singletonList(e.getMessage()))
            );
        } catch (BookAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new GenericResponse<>("Duplicate Book Found", Collections.singletonList(e.getMessage()))
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new GenericResponse<>("Book Not Found", Collections.singletonList(e.getMessage()))
            );
        }
    }

    @ApiOperation(value = "List all books", notes = "Returns a paginated list of books with optional search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved books")
    })
    @GetMapping
    public ResponseEntity<GenericResponse<List<Book>>> listBooks(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int perPage
    ) {
        Page<Book> books = bookService.listAllBooks(PageRequest.of(page, perPage), query);
        return ResponseEntity.ok(
                new GenericResponse<>(
                        books.hasContent() ? "Books retrieved successfully" : "No books found",
                        books.getContent(),
                        books
                )
        );
    }

    @ApiOperation(value = "Delete a book", notes = "Removes a book from the catalog by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully deleted"),
            @ApiResponse(code = 404, message = "Book not found"),
            @ApiResponse(code = 500, message = "Error during deletion")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteBook(@PathVariable int id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok(
                    new GenericResponse<>("Book deleted successfully", null)
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new GenericResponse<>("Book Not Found", Collections.singletonList(e.getMessage()))
            );
        } catch (BookDeletionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new GenericResponse<>("Failed to Delete Book", Collections.singletonList(e.getMessage()))
            );
        }
    }

    @ApiOperation(value = "Get a book by ID", notes = "Returns a single book by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<Book>> getBookById(@PathVariable int id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok(
                        new GenericResponse<>("Book retrieved successfully", book)
                ))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new GenericResponse<>("Book Not Found",
                                Collections.singletonList("No book found with ID: " + id))
                ));
    }
}
