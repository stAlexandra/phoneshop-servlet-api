<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart">
    <jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
    <table>
        <thead>
        <tr>
            <td></td>
            <td>Description</td>
            <td class="price">Price</td>
            <td class="number">Quantity</td>
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
                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                </td>
                <td>
                        ${item.quantity}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td>Total:</td>
            <td class="price"><fmt:formatNumber value="${cart.totalPrice}" type="currency"
                                                currencySymbol="${cart.currency.symbol}"/></td>
        </tr>
    </table>
    <form method="post" action="<c:url value="/checkout"/>">
        <h3>Please enter following fields:</h3>
        <input name="firstName" placeholder="First name"/>
        <br><br>
        <input name="lastName" placeholder="Last name"/>
        <br><br>
        <input name="deliveryAddress" placeholder="Delivery address"/>
        <br><br>
        <input name="phone" placeholder="Phone"/>
        <br><br>
        <c:if test="${not empty param.error}">
            <p class="error">All fields should be filled!</p>
        </c:if>
        <button>Place order</button>
    </form>
</tags:master>