<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="max-w-4xl mx-auto space-y-8">
    <div class="text-center space-y-4 pb-8 border-b border-gray-200">
        <h1 class="text-4xl font-bold text-gray-900">Book Management System</h1>
        <p class="text-xl text-gray-600">A comprehensive catalogue solution for book collectors</p>
    </div>

    <div class="space-y-4">
        <h2 class="text-2xl font-semibold text-gray-900">Project Overview</h2>
        <p class="text-gray-600">
            This is a catalogue solution that allows for maintenance of an inventory of books.
            Built as a distributed system with two Spring Boot services, it provides a seamless experience for managing
            books.
        </p>

        <div class="pb-8">
            <div class="text-center text-xl font-semibold">Web Interface</div>
            <div class="grid md:grid-cols-2 gap-6 mt-4 border-b pb-8">
                <div class="bg-white p-6 rounded-lg shadow-sm border border-gray-200">
                    <h3 class="text-lg font-semibold text-gray-900 mb-2">Features</h3>
                    <ul class="list-disc list-inside space-y-2 text-gray-600">
                        <li>View and search complete book catalogue</li>
                        <li>Add new books to collection</li>
                        <li>Update existing book information</li>
                        <li>Remove books from catalogue</li>
                    </ul>
                </div>

                <div class="bg-white p-6 rounded-lg shadow-sm border border-gray-200">
                    <h3 class="text-lg font-semibold text-gray-900 mb-2">Technical Details</h3>
                    <ul class="list-disc list-inside space-y-2 text-gray-600">
                        <li>Spring Boot 2.7.18</li>
                        <li>Java 1.8</li>
                        <li>Maven Project</li>
                        <li>JSP Templates</li>
                        <li>RESTful API Integration</li>
                    </ul>
                </div>
            </div>
        </div>

        <div>
            <div class="text-center text-xl font-semibold">Book Management Service (API)</div>
            <div class="grid md:grid-cols-2 gap-6 mt-4 border-b pb-8">
                <div class="bg-white p-6 rounded-lg shadow-sm border border-gray-200">
                    <h3 class="text-lg font-semibold text-gray-900 mb-2">Features</h3>
                    <ul class="list-disc list-inside space-y-2 text-gray-600">
                        <li>Index and search all books in database</li>
                        <li>Add new books to database</li>
                        <li>Update existing book information in database</li>
                        <li>Delete books from Database</li>
                    </ul>
                </div>

                <div class="bg-white p-6 rounded-lg shadow-sm border border-gray-200">
                    <h3 class="text-lg font-semibold text-gray-900 mb-2">Technical Details</h3>
                    <ul class="list-disc list-inside space-y-2 text-gray-600">
                        <li>Spring Boot 2.7.18</li>
                        <li>Java 1.8</li>
                        <li>Maven Project</li>
                        <li>H2 Database</li>
                        <li>Spring Data JPA</li>
                        <li>RESTful API</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="space-y-4">
        <h2 class="text-2xl font-semibold text-gray-900">Book Model Fields</h2>
        <div class="bg-white p-6 rounded-lg shadow-sm border border-gray-200">
            <ul class="space-y-2 text-gray-600">
                <li><span class="font-medium">Title:</span> Name of the book</li>
                <li><span class="font-medium">ISBN:</span> Unique identifier for each book</li>
                <li><span class="font-medium">Publish Date:</span> Publication date</li>
                <li><span class="font-medium">Price:</span> Book value (ZAR)</li>
                <li><span class="font-medium">Type:</span> Format (hardcover, softcover, ebook, audiobook)</li>
                <li><span class="font-medium">Authors:</span> Book's author(s)</li>
            </ul>
        </div>
    </div>

    <div class="text-center space-y-4 py-8">
        <a href="${pageContext.request.contextPath}/books"
           class="inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
            View Book Catalogue
        </a>
    </div>

    <div class="text-center text-sm text-gray-500 border-t border-gray-200 pt-8">
        <p>This project was developed as part of the PayU assessment process.</p>
        <p class="mt-2">Special thanks to the PayU team for this opportunity.</p>
    </div>
</div>