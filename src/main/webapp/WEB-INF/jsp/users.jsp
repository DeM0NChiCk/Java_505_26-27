<%--
  Created by IntelliJ IDEA.
  User: karp2
  Date: 22.09.2026
  Time: 8:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- Подключение JSTL --%>

<html>
<head>
    <title>Список пользователей</title>
</head>
<body>
<h1>Пользователи системы:</h1>

<%-- Использование JSTL цикла forEach --%>
<ul>
    <c:forEach var="user" items="${users}">
        <li>${user}</li>
    </c:forEach>
</ul>

<%-- Использование JSTL условия --%>
<c:if test="${isAdmin}">
    <button style="color: red;">Удалить всех (Кнопка админа)</button>
</c:if>

</body>
</html>
