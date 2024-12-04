package com.beulah.bookmanagementweb.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public String handleWebClientException(WebClientResponseException ex, RedirectAttributes redirectAttributes) {
        String errorMessage;
        switch (ex.getStatusCode().value()) {
            case 404:
                errorMessage = "Resource not found";
                break;
            case 400:
                errorMessage = "Invalid request: " + ex.getResponseBodyAsString();
                break;
            case 409:
                errorMessage = "A conflict occurred: " + ex.getResponseBodyAsString();
                break;
            case 500:
                errorMessage = "Server error occurred. Please try again later.";
                break;
            default:
                errorMessage = "An unexpected error occurred";
        }

        redirectAttributes.addFlashAttribute("toastMessage", errorMessage);
        redirectAttributes.addFlashAttribute("toastType", "error");
        return "redirect:/books";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("toastMessage", "An unexpected error occurred: " + ex.getMessage());
        redirectAttributes.addFlashAttribute("toastType", "error");
        return "redirect:/books";
    }
}