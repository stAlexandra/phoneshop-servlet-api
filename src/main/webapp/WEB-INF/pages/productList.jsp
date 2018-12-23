<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List" pageClass="product-list" showMiniCart="${true}">

    <jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="query" value="${param.query}"/>
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <a href="
                <c:url value="/products">
                    <c:param name="query" value="${param.query}"/>
                    <c:param name="sort" value="description"/>
                    <c:param name="order" value="desc"/>
                </c:url>">
                    <img src="<c:url value="/images/down.png"/>"/>
                </a>
                <a href="
                <c:url value="/products">
                    <c:param name="query" value="${param.query}"/>
                    <c:param name="sort" value="description"/>
                    <c:param name="order" value="asc"/>
                </c:url>">
                    <img src="<c:url value="/images/up.png"/>"/>
                </a>
            </td>
            <td class="price">Price
                <a href="
                <c:url value="/products">
                    <c:param name="query" value="${param.query}"/>
                    <c:param name="sort" value="price"/>
                    <c:param name="order" value="desc"/>
                </c:url>">
                    <img src="<c:url value="/images/down.png"/>"/>
                </a>
                <a href="
                <c:url value="/products">
                    <c:param name="query" value="${param.query}"/>
                    <c:param name="sort" value="price"/>
                    <c:param name="order" value="asc"/>
                </c:url>">
                    <img src="<c:url value="/images/up.png"/>"/>
                </a>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <a href="<c:url value="/products/${product.id}"/>">
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </a>
                </td>
                <td>
                    <a href="<c:url value="/products/${product.id}"/>">
                            ${product.description}
                    </a>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>

</tags:master>
