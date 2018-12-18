<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<span style="float: right">
    <a href="<c:url value="/cart"/>">
        ${cart.cartItems.size()} items; ${cart.totalPrice} total
    </a>
</span>