<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="space-y-6">
    <div class="flex justify-between items-center">
        <h1 class="text-3xl font-bold">Books</h1>
        <a href="${pageContext.request.contextPath}/books/new"
           class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
            Add New Book
        </a>
    </div>

    <form method="GET" action="${pageContext.request.contextPath}/books" class="flex gap-4">
        <input type="text"
               name="query"
               value="${query}"
               placeholder="Search books..."
               class="flex-1 border rounded px-4 py-2">
        <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
            Search
        </button>
    </form>

    <div class="bg-white shadow rounded-lg overflow-hidden">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
            <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ISBN</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Author(s)
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Type</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Price</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            <c:forEach items="${books}" var="book">
                <tr>
                    <td class="px-6 py-4 whitespace-nowrap">${book.title}</td>
                    <td class="px-6 py-4 whitespace-nowrap">${book.isbn}</td>
                    <td class="px-6 py-4 whitespace-nowrap">${book.author}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                                ${book.bookType == 'HARD_COVER' ? 'bg-purple-100 text-purple-800' :
                                  book.bookType == 'SOFT_COVER' ? 'bg-green-100 text-green-800' :
                                  book.bookType == 'E_BOOK' ? 'bg-blue-100 text-blue-800' :
                                  'bg-yellow-100 text-yellow-800'}">
                                    ${book.bookType}
                            </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                        $${book.price / 100}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap space-x-2 relative">
                        <button class="action-button w-8 h-8 text-gray-500 hover:text-gray-700"
                                data-target="actionButtonContainer${book.id}">
                            <svg class="w-6 h-6" viewBox="0 0 24 24">
                                <circle cx="17.5" cy="12" r="1.5"/>
                                <circle cx="12" cy="12" r="1.5"/>
                                <circle cx="6.5" cy="12" r="1.5"/>
                            </svg>
                        </button>
                        <div id="actionButtonContainer${book.id}"
                             class="hidden absolute right-0 mt-2 w-48 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 z-10">
                            <div class="py-1" role="menu">
                                <a href="${pageContext.request.contextPath}/books/${book.id}"
                                   class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">View</a>
                                <a href="${pageContext.request.contextPath}/books/${book.id}/edit"
                                   class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Edit</a>
                                <form method="POST" action="${pageContext.request.contextPath}/books/${book.id}/delete">
                                    <input type="hidden" name="_method" value="DELETE">
                                    <button type="submit"
                                            class="block w-full text-left px-4 py-2 text-sm text-red-700 hover:bg-gray-100"
                                            onclick="return confirm('Are you sure?')">
                                        Delete
                                    </button>
                                </form>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="px-6 py-4 bg-white border-t border-gray-200">
            <div class="flex items-center justify-between">
                <div class="flex-1 flex justify-between items-center">
                    <div>
                        <p class="text-sm text-gray-700">
                            Showing page <span class="font-medium">${currentPage + 1}</span> of
                            <span class="font-medium">${totalPages}</span>
                        </p>
                    </div>
                    <div class="flex space-x-2">
                        <a href="${pageContext.request.contextPath}/books?page=${currentPage - 1}&perPage=${perPage}${query != null ? '&query='.concat(query) : ''}"
                           class="${currentPage == 0 ? 'opacity-50 cursor-not-allowed' : ''} px-3 py-2 border rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
                        ${currentPage == 0 ? 'disabled' : ''}>
                            Previous
                        </a>

                        <c:forEach begin="0" end="${totalPages - 1}" var="pageNum">
                            <c:if test="${pageNum == currentPage || pageNum == 0 || pageNum == totalPages - 1 || (pageNum >= currentPage - 1 && pageNum <= currentPage + 1)}">
                                <a href="${pageContext.request.contextPath}/books?page=${pageNum}&perPage=${perPage}${query != null ? '&query='.concat(query) : ''}"
                                   class="${pageNum == currentPage ? 'bg-blue-50 border-blue-500 text-blue-600' : 'bg-white'} px-3 py-2 border rounded-md text-sm font-medium hover:bg-gray-50">
                                        ${pageNum + 1}
                                </a>
                            </c:if>
                        </c:forEach>

                        <a href="${pageContext.request.contextPath}/books?page=${currentPage + 1}&perPage=${perPage}${query != null ? '&query='.concat(query) : ''}"
                           class="${currentPage >= totalPages - 1 ? 'opacity-50 cursor-not-allowed' : ''} px-3 py-2 border rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
                        ${currentPage >= totalPages - 1 ? 'disabled' : ''}>
                            Next
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                document.addEventListener('click', function (e) {
                    const allPopups = document.querySelectorAll('[id^="actionButtonContainer"]');
                    allPopups.forEach(popup => {
                        const button = document.querySelector(`button[data-target="${popup.id}"]`);
                        if (e.target !== button && !popup.contains(e.target)) {
                            popup.classList.add('hidden');
                        }
                    });
                });

                const actionButtons = document.querySelectorAll('.action-button');
                actionButtons.forEach(button => {
                    button.addEventListener('click', function (e) {
                        e.stopPropagation();
                        const targetId = this.getAttribute('data-target');
                        const popup = document.getElementById(targetId);

                        const allPopups = document.querySelectorAll('[id^="actionButtonContainer"]');
                        allPopups.forEach(p => {
                            if (p.id !== targetId) {
                                p.classList.add('hidden');
                            }
                        });

                        popup.classList.toggle('hidden');
                    });
                });
            });
        </script>
    </div>
</div>