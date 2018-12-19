<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="OrderOverview">
    <h3>You've ordered: </h3>
    <table>
        <thead>
        <tr>
            <td></td>
            <td>Description</td>
            <td class="price">Price</td>
            <td class="number">Quantity</td>
        </tr>
        </thead>
        <c:forEach var="item" items="${order.cart.cartItems}" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                </td>
                <td>
                        ${item.product.description}
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
            <td class="price"><fmt:formatNumber value="${order.cart.totalPrice}" type="currency"
                                                currencySymbol="${order.cart.currency.symbol}"/></td>
        </tr>
    </table>
    <h3>Contact details: </h3>
    First name: ${order.details.firstName}
    <br>
    Last name: ${order.details.lastName}
    <br><br>
    Delivery address: ${order.details.deliveryAddress}
    <br><br>
    Phone: ${order.details.phone}

</tags:master>