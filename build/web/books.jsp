<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Book Library</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        form { margin-bottom: 20px; }
        input, textarea { margin: 5px; padding: 5px; }
    </style>
</head>
<body>
    <h2>Book Library</h2>
    <form action="books" method="post">
        <label>Title:</label><input type="text" name="title" required><br>
        <label>Author:</label><input type="text" name="author" required><br>
        <input type="submit" value="Add Book">
    </form>
    <h3>Books</h3>
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
        </tr>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>${book.id}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>