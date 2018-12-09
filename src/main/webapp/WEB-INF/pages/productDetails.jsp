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
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="${product.description}" pageClass="product-details">

    <form method="get" action="<c:url value="/cart"/>">
        <button>Go to cart</button>
    </form>
<img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
<br>
<table>
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

<jsp:include page="/WEB-INF/pages/recentlyViewedProducts.jsp"/>
</tags:master>