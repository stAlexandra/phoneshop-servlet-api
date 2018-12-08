<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.11.2018
  Time: 1:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<html>
<head>
    <title>${product.description}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="<c:url value="/styles/main.css"/>">
</head>
<body class="product-list">
<div>
    <jsp:include page="/WEB-INF/pages/header.jsp"/>
</div>
<main>
    <br>cart:${cart}<br>
    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
    <br>
    <table width="320">
        <tr>
            <td>Description</td>
            <td style="text-align: right">${product.description}</td>
        </tr>
        <tr>
            <td>Price</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
    </table>
    <form method="post" action="<c:url value="/products/${product.id}"/>">
        <br>Quantity: <input class="number" name="quantity" value="${not empty param.quantity ? param.quantity : 1}"/>
        <button>Add to cart</button>
        <c:if test="${not empty param.message}">
            <p class="success">Added to cart successfully!</p>
        </c:if>
        <c:if test="${not empty quantityError}">
            <p class="error">${quantityError}</p>
        </c:if>
    </form>
</main>
<jsp:include page="/WEB-INF/pages/recentlyViewedProducts.jsp"/>
<div>
    <jsp:include page="/WEB-INF/pages/footer.jsp"/>
</div>
</body>
</html>