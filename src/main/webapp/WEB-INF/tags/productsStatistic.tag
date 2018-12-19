<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="productList" type="com.es.phoneshop.model.cart.LimitedSizeList" required="true" %>
<%@attribute name="title" type="java.lang.String" required="true" %>

<div>
    <h2>${title}</h2>
    <table>
        <tr>
            <c:forEach var="product" items="${productList}">
                <td style="text-align: center">
                    <p><img class="product-tile"
                            src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </p>
                    <a href="<c:url value="/products/${product.id}"/>">
                            ${product.description}
                    </a>
                    <p><fmt:formatNumber value="${product.price}" type="currency"
                                         currencySymbol="${product.currency.symbol}"/></p>
                </td>
            </c:forEach>
        </tr>
    </table>
</div>
