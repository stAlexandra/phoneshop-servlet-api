<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart">
    <jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>

    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <button>Update</button>
        <br>
        <c:if test="${not empty quantityErrors}">
            <p class="error">Failed to update!</p>
        </c:if>
        <c:if test="${not empty param.deletionError}">
            <p class="error">Failed to delete!</p>
        </c:if>
        <c:if test="${not empty param.message}">
            <p class="success">${param.message}</p>
        </c:if>
        <table>
            <thead>
            <tr>
                <td></td>
                <td>Description</td>
                <td class="price">Price</td>
                <td class="number">Quantity</td>
                <td></td>
            </tr>
            </thead>
            <c:forEach var="item" items="${cart.cartItems}" varStatus="status">
                <tr>
                    <td>
                        <a href="<c:url value="/products/${item.product.id}"/>">
                            <img class="product-tile"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="/products/${item.product.id}"/>">
                                ${item.product.description}
                        </a>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/>
                    </td>
                    <td>
                        <input name="quantity"
                               value="${not empty quantityErrors[item.product.id] ? paramValues["quantity"][status.index] : item.quantity}"
                               class="number"
                        />
                        <input type="hidden" name="productId" value="${item.product.id}"/>
                        <c:if test="${not empty quantityErrors[item.product.id]}">
                            <p class="error">${quantityErrors[item.product.id]}</p>
                        </c:if>
                    </td>
                    <td>
                        <button formaction="${pageContext.servletContext.contextPath}/cart/delete/${item.product.id}"
                                formmethod="post">Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <button>Update</button>
    </form>
</tags:master>
