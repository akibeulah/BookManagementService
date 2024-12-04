<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-3xl mx-auto">
    <div class="bg-white shadow overflow-hidden sm:rounded-lg">
        <div class="px-4 py-5 sm:px-6 flex justify-between items-center">
            <h1 class="text-3xl font-bold text-gray-900">${book.title}</h1>
            <div class="space-x-3">
                <a href="${pageContext.request.contextPath}/books/${book.id}/edit"
                   class="inline-flex items-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md hover:bg-blue-700">
                    Edit Book
                </a>
                <form method="POST" action="${pageContext.request.contextPath}/books/${book.id}/delete" class="inline">
                    <input type="hidden" name="_method" value="DELETE">
                    <button type="submit"
                            class="inline-flex items-center px-4 py-2 text-sm font-medium text-white bg-red-600 rounded-md hover:bg-red-700"
                            onclick="return confirm('Are you sure you want to delete this book?')">
                        Delete Book
                    </button>
                </form>
            </div>
        </div>
        <div class="border-t border-gray-200">
            <dl>
                <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">ISBN</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">${book.isbn}</dd>
                </div>
                <div class="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Author(s)</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">${book.author}</dd>
                </div>
                <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Book Type</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
                        <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                            ${book.bookType == 'HARD_COVER' ? 'bg-purple-100 text-purple-800' :
                              book.bookType == 'SOFT_COVER' ? 'bg-green-100 text-green-800' :
                              book.bookType == 'E_BOOK' ? 'bg-blue-100 text-blue-800' :
                              'bg-yellow-100 text-yellow-800'}">
                            ${book.bookType}
                        </span>
                    </dd>
                </div>
                <div class="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Price</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">$${book.price / 100.0}</dd>
                </div>
                <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Publish Date</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">${book.publishDate}</dd>
                </div>
            </dl>
        </div>
    </div>

    <div class="mt-6">
        <a href="${pageContext.request.contextPath}/books"
           class="text-blue-600 hover:text-blue-900">
            ← Back to Books
        </a>
    </div>
</div>