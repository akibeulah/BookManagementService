<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<header class="bg-gray-800 text-white shadow-lg">
<nav class="container mx-auto px-6 py-4">
    <div class="flex items-center justify-between">
        <div class="flex items-center">
            <a href="${pageContext.request.contextPath}/" class="text-xl font-bold">Book Management</a>
        </div>
        <div class="flex items-center space-x-4">
            <a href="${pageContext.request.contextPath}/" class="hover:text-gray-300">Home</a>
            <a href="${pageContext.request.contextPath}/books" class="hover:text-gray-300">Books</a>
            <a href="${pageContext.request.contextPath}/books/new" class="bg-blue-600 font-medium text-sm rounded-lg px-4 py-2 hover:text-gray-300">Add Book</a>
        </div>
    </div>
</nav>
</header>