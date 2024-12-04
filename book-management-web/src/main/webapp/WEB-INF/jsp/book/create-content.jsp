<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold">Create New Book</h1>
    </div>

    <form method="POST" action="${pageContext.request.contextPath}/books" class="space-y-6">
        <div class="space-y-4">
            <div>
                <label for="title" class="block text-sm font-medium text-gray-700">Title</label>
                <input type="text"
                       id="title"
                       name="title"
                       required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-4 py-2">
            </div>

            <div>
                <label for="isbn" class="block text-sm font-medium text-gray-700">ISBN</label>
                <input type="text"
                       id="isbn"
                       name="isbn"
                       required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-4 py-2">
            </div>

            <div>
                <label for="publishDate" class="block text-sm font-medium text-gray-700">Publish Date</label>
                <input type="date"
                       id="publishDate"
                       name="publishDate"
                       required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-4 py-2">
            </div>

            <div>
                <label for="price" class="block text-sm font-medium text-gray-700">Price (in cents)</label>
                <input type="number"
                       id="price"
                       name="price"
                       required
                       min="0"
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-4 py-2">
            </div>

            <div>
                <label for="bookType" class="block text-sm font-medium text-gray-700">Book Type</label>
                <select id="bookType"
                        name="bookType"
                        required
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-4 py-2">
                    <option value="HARD_COVER">Hard Cover</option>
                    <option value="SOFT_COVER">Soft Cover</option>
                    <option value="E_BOOK">E-Book</option>
                    <option value="AUDIO_BOOK">Audio Book</option>
                </select>
            </div>

            <div>
                <label for="author" class="block text-sm font-medium text-gray-700">Author(s)</label>
                <input type="text"
                       id="author"
                       name="author"
                       required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm px-4 py-2">
            </div>
        </div>

        <div class="flex justify-end space-x-3">
            <a href="${pageContext.request.contextPath}/books"
               class="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50">
                Cancel
            </a>
            <button type="submit"
                    class="px-4 py-2 text-sm font-medium text-white bg-blue-600 border border-transparent rounded-md hover:bg-blue-700">
                Create Book
            </button>
        </div>
    </form>
</div>