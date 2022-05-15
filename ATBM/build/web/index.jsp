<%-- 
    Document   : index
    Created on : 13 Apr, 2022, 5:12:52 PM
    Author     : tom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Login</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <c:if test="${sqlStatement == null}">
            <c:set var="sqlStatement" value="select * from users" />
        </c:if>
        <h1>Login</h1>
        <p><b>SQL statement: ${query}</b></p>
        <form action="login" method="post">
            <div>
                <label for="username">Username</label>
                <input style="margin-left: 10px; margin-bottom: 5px;" type="username" value="${username}" name="username">
            </div>
            <div>
                <label for="password">Password</label>
                <input style="margin-left: 14px; margin-bottom: 5px;" type="password" value="${password}" name="password">
            </div>
            <input type="submit" value="Execute">
        </form>
        <p><b>SQL result: </b></p>
        ${sqlResult}
        <c:if test="${isLoggedIn == true}"><a href="<c:url value='/product' />">Go to product page</a></c:if>
    </body>
</html>
