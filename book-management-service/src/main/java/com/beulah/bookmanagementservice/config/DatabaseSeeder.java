package com.beulah.bookmanagementservice.config;

import com.beulah.bookmanagementservice.enums.BookType;
import com.beulah.bookmanagementservice.models.Book;
import com.beulah.bookmanagementservice.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            List<Book> books = Arrays.asList(
                    createBook(
                            "The Great Gatsby",
                            "978-0743273565",
                            LocalDate.of(1925, 4, 10),
                            2999,
                            BookType.HARD_COVER,
                            "F. Scott Fitzgerald"
                    ),
                    createBook(
                            "1984",
                            "978-0451524935",
                            LocalDate.of(1949, 6, 8),
                            1999,
                            BookType.SOFT_COVER,
                            "George Orwell"
                    ),
                    createBook(
                            "The Hobbit",
                            "978-0547928227",
                            LocalDate.of(1937, 9, 21),
                            2499,
                            BookType.E_BOOK,
                            "J.R.R. Tolkien"
                    ),
                    createBook(
                            "Pride and Prejudice",
                            "978-0141439518",
                            LocalDate.of(1813, 1, 28),
                            1599,
                            BookType.AUDIO_BOOK,
                            "Jane Austen"
                    ),
                    createBook(
                            "To Kill a Mockingbird",
                            "978-0446310789",
                            LocalDate.of(1960, 7, 11),
                            1899,
                            BookType.SOFT_COVER,
                            "Harper Lee"
                    ),
                    createBook(
                            "The Catcher in the Rye",
                            "978-0316769488",
                            LocalDate.of(1951, 7, 16),
                            2199,
                            BookType.HARD_COVER,
                            "J.D. Salinger"
                    ),
                    createBook(
                            "Lord of the Rings",
                            "978-0618645619",
                            LocalDate.of(1954, 7, 29),
                            3499,
                            BookType.E_BOOK,
                            "J.R.R. Tolkien"
                    ),
                    createBook(
                            "Brave New World",
                            "978-0060850524",
                            LocalDate.of(1932, 1, 1),
                            1799,
                            BookType.AUDIO_BOOK,
                            "Aldous Huxley"
                    ),
                    createBook(
                            "The Da Vinci Code",
                            "978-0307474278",
                            LocalDate.of(2003, 3, 18),
                            2299,
                            BookType.SOFT_COVER,
                            "Dan Brown"
                    ),
                    createBook(
                            "The Hunger Games",
                            "978-0439023481",
                            LocalDate.of(2008, 9, 14),
                            2399,
                            BookType.HARD_COVER,
                            "Suzanne Collins"
                    )
            );

            repository.saveAll(books);
        };
    }

    private Book createBook(String title, String isbn, LocalDate publishDate, int price, BookType bookType, String author) {
        return Book.builder()
                .title(title)
                .isbn(isbn)
                .publishDate(publishDate)
                .price(price)
                .bookType(bookType)
                .author(author)
                .build();
    }
}
