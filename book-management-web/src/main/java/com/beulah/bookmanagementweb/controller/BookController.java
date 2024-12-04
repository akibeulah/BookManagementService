package com.beulah.bookmanagementweb.controller;

import com.beulah.bookmanagementweb.dto.Book;
import com.beulah.bookmanagementweb.dto.BookForm;
import com.beulah.bookmanagementweb.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String index(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer perPage,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Map<String, Object> responseMap = bookService.getBooks(query, page, perPage).block();
            List<Book> books = (List<Book>) responseMap.get("data");

            Map<String, Object> meta = (Map<String, Object>) responseMap.get("metadata");
            model.addAttribute("books", books);
            model.addAttribute("currentPage", page);
            model.addAttribute("perPage", perPage);
            model.addAttribute("totalPages", meta.get("totalPages"));
            model.addAttribute("totalItems", meta.get("totalElements"));
            model.addAttribute("query", query);

            return "book/index";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastMessage", "Looks like something went wrong" + e.getMessage());
            redirectAttributes.addFlashAttribute("toastType", "error");
            return "redirect:/";
        }
    }

    @GetMapping("/{id}")
    public String viewOne(@PathVariable Long id, Model model) {
        Map<String, Object> responseMap = bookService.getBook(id).block();
        Object book = responseMap.get("data");
        model.addAttribute("book", book);
        return "book/viewOne";
    }

    @GetMapping("/new")
    public String createForm() {
        return "book/create";
    }

    @PostMapping
    public String create(@ModelAttribute BookForm bookForm, RedirectAttributes redirectAttributes) {
        try {
            Map<String, Object> bookMap = convertBookFormToMap(bookForm);
            bookService.createBook(bookMap).block();
            redirectAttributes.addFlashAttribute("toastMessage", "Book created successfully!");
            redirectAttributes.addFlashAttribute("toastType", "success");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastMessage", "Failed to create book: " + e.getMessage());
            redirectAttributes.addFlashAttribute("toastType", "error");
            return "redirect:/books";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Map<String, Object> responseMap = bookService.getBook(id).block();
        Object book = responseMap.get("data");
        model.addAttribute("book", book);
        return "book/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute BookForm bookForm, RedirectAttributes redirectAttributes) {
        try {
            Map<String, Object> bookMap = convertBookFormToMap(bookForm);
            bookService.updateBook(id, bookMap).block();
            redirectAttributes.addFlashAttribute("toastMessage", "Book updated successfully!");
            redirectAttributes.addFlashAttribute("toastType", "success");
            return "redirect:/books/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastMessage", "Failed to update book: " + e.getMessage());
            redirectAttributes.addFlashAttribute("toastType", "error");
            return "redirect:/books";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id).block();
            redirectAttributes.addFlashAttribute("toastMessage", "Book deleted successfully!");
            redirectAttributes.addFlashAttribute("toastType", "success");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastMessage", "Failed to delete book: " + e.getMessage());
            redirectAttributes.addFlashAttribute("toastType", "error");
            return "redirect:/books";
        }
    }

    private Map<String, Object> convertBookFormToMap(BookForm form) {
        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("title", form.getTitle());
        bookMap.put("isbn", form.getIsbn());
        bookMap.put("publishDate", form.getPublishDate());
        bookMap.put("price", form.getPrice());
        bookMap.put("bookType", form.getBookType());
        bookMap.put("author", form.getAuthor());
        return bookMap;
    }
}