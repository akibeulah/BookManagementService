<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - Book Management</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="flex flex-col min-h-screen bg-gray-100">
<jsp:include page="/WEB-INF/jsp/components/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/components/toast.jsp"/>

<main class="container mx-auto px-6 py-8 flex-grow">
    <jsp:include page="${param.content}"/>
</main>

<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>