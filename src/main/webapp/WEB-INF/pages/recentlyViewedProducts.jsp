<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div>
    <h2>Recently viewed:</h2>
    <table>
        <tr>
            <c:forEach var="viewedProduct" items="${viewedProducts}">
                <td style="text-align: center">
                    <p><img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewedProduct.imageUrl}"></p>
                    <a href="<c:url value="/products/${viewedProduct.id}"/>">
                            ${viewedProduct.description}
                    </a>
                    <p><fmt:formatNumber value="${viewedProduct.price}" type="currency" currencySymbol="${viewedProduct.currency.symbol}"/></p>
                </td>
            </c:forEach>
        </tr>
    </table>
</div>