<%-- 
    Document   : product
    Created on : Apr 24, 2022, 10:55:29 PM
    Author     : tom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product</title>
    </head>
    <body>
        <h1>Product list</h1>
        <form action="product" method="post">
            <label for="name">Search</label>
            <input value="${name}" type="text" name="name" style="width: 500px; margin-bottom: 10px;"/>
            <button type="submit" value="Search">Search</button>
        </form>
        <p><b>SQL statement: ${query}</b></p>
        <table border="1" cellpadding="5" cellspacing="5">
            <tr>
                <th>STT</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
            </tr>

            <c:forEach var="item" items="${list}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${item.name}</td>
                    <td>${item.description}</td>
                    <td>
                        <fmt:setLocale value = "en_US"/>
                        <fmt:formatNumber maxFractionDigits="2" value = "${item.price}" type = "currency"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    <p><b>SQL result: ${msg}</b></p>
    ${sqlResult}
</body>
</html>
