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
            <input type="text" name="name" style="width: 500px; margin-bottom: 10px;"/>
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
                    <td>${(currentPage-1)* 10 + loop.index + 1}</td>
                    <td>${item.name}</td>
                    <td>${item.description}</td>
                    <td>
                        <fmt:setLocale value = "en_US"/>
                        <fmt:formatNumber maxFractionDigits="2" value = "${item.price}" type = "currency"/>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <%--For displaying Previous link except for the 1st page --%>
        <c:if test="${currentPage != 1}">
        <td><a href="<c:url value='/product?page=${currentPage - 1}' />">Previous</a></td>
    </c:if>

    <%--For displaying Page numbers. 
    The when condition does not display a link for the current page--%>
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="<c:url value='/product?page=${i}' />">${i}</a></td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
        </tr>
    </table>

    <%--For displaying Next link --%>
    <c:if test="${currentPage != 10}">
        <td><a href="<c:url value='/product?page=${currentPage + 1}' />">Next</a></td>
    </c:if>
    <p><b>SQL result: ${msg}</b></p>
    ${sqlResult}
</body>
</html>
