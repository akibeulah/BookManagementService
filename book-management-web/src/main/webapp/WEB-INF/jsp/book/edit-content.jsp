<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold">Edit ${book.title}</h1>
    </div>

    <form method="POST" action="${pageContext.request.contextPath}/books/${book.id}" class="space-y-6">
        <div class="space-y-4">
            <div>
                <label for="title" class="block text-sm font-medium text-gray-700">Title</label>
                <input type="text"
                       id="title"
                       name="title"
                       placeholder="${book.title}"
                       value="${book.title}"
                       required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm outline-none px-2 py-2">
            </div>

            <div>
                <label for="isbn" class="block text-sm font-medium text-gray-700">ISBN</label>
                <input type="text"
                       id="isbn"
                       name="isbn"
                       placeholder="${book.isbn}"
                       value="${book.isbn}"
                       required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm outline-none px-2 py-2">
            </div>

            <div>
                <label for="publishDate" class="block text-sm font-medium text-gray-700">Publish Date</label>
                <input type="date"
                       id="publishDate"
                       name="publishDate"
                       placeholder="${book.publishDate}"
                       value="${book.publishDate}"
                       required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm outline-none px-2 py-2">
            </div>

            <div>
                <label for="price" class="block text-sm font-medium text-gray-700">Price (in cents)</label>
                <input type="number"
                       id="price"
                       name="price"
                       placeholder="${book.price}"
                       value="${book.price}"
                       required
                       min="0"
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm outline-none px-2 py-2">
            </div>

            <div>
                <label for="bookType" class="block text-sm font-medium text-gray-700">Book Type</label>
                <select id="bookType"
                        name="bookType"
                        required
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm outline-none px-2 py-2">
                    <option value="HARD_COVER" ${book.bookType == 'HARD_COVER' ? 'selected' : ''}>Hard Cover</option>
                    <option value="SOFT_COVER" ${book.bookType == 'SOFT_COVER' ? 'selected' : ''}>Soft Cover</option>
                    <option value="E_BOOK" ${book.bookType == 'E_BOOK' ? 'selected' : ''}>E-Book</option>
                    <option value="AUDIO_BOOK" ${book.bookType == 'AUDIO_BOOK' ? 'selected' : ''}>Audio Book</option>
                </select>
            </div>

            <div>
                <label for="author" class="block text-sm font-medium text-gray-700">Author(s)</label>
                <input type="text"
                       id="author"
                       name="author"
                       value="${book.author}"
                       placeholder="${book.author}"
                       required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm outline-none px-2 py-2">
            </div>
        </div>

        <div class="flex justify-end space-x-3">
            <a href="${pageContext.request.contextPath}/books/${book.id}"
               class="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50">
                Cancel
            </a>
            <button type="submit"
                    class="px-4 py-2 text-sm font-medium text-white bg-blue-600 border border-transparent rounded-md hover:bg-blue-700">
                Update Book
            </button>
        </div>
    </form>
</div>
