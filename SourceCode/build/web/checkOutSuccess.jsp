<%-- 
    Document   : checkOutSuccess
    Created on : Oct 26, 2022, 2:31:49 PM
    Author     : MSI
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Success</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.USER}">
            <font color="green">
            Welcome, ${ sessionScope.USER.fullName }
            </font>
            <br>
            <br>
        </c:if>
            
        <header style="background: lightgray">
            <c:forEach var="featureNav" items="${sessionScope.FEATURE_NAVS}">
                <a style="padding: 4px 16px" href="${featureNav.value}">${featureNav.key}</a>
            </c:forEach>
        </header>

        <h1 style='color: green'>Checkout Success</h1>

        <c:set var = "preparedOrder" value = "${requestScope.PREPARED_ORDER}"/>
        <c:set var = "orderDTO" value = "${requestScope.ORDER_DTO}"/>
        
        <c:if test="${not empty preparedOrder}">
            
            <c:if test="${not empty orderDTO}">                
            <%-- Print infor for contacting & delivery--%>
            <h3>Order ID: ${ orderDTO.orderID }</h3>
            <h3>Date: ${ orderDTO.dateBuy }</h3>
            <h3>Customer's Name: ${ orderDTO.name }</h3>
            <h3>Customer's Phone: ${ orderDTO.phone }</h3>
            <h3>Customer's Address: ${ orderDTO.address }</h3>
            </c:if>
            
            <%-- Print products in order's queue --%>
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>SKU</th>
                        <th>Name</th>
                        <!--<th>Description</th>-->
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>                        
                    </tr>
                </thead>
                <!--<form action="DispatchController">-->                            
                <form action="checkOutAction">                            
                    <tbody>                  
                        <c:forEach var="item" items="${preparedOrder.productsInOrder}" varStatus="counter">
                            <tr>                                            
                                <td>
                                    ${counter.count}
                                    .</td>
                                <td>
                                    ${item.sku}
                                </td>

                                <td>
                                    ${item.name}
                                </td>                                

                                <td>
                                    ${item.price}$
                                </td>
                                
                                <td align='center'>
                                    ${item.quantity}
                                </td>

                                <td>
                                    ${item.total}$
                                </td>                                                                                                
                            </tr>

                        </c:forEach>


                        <tr style="font-weight: bold">                                        
                            <td colspan="3" align="center">
                                Total amount:
                            </td>

                            <td colspan="3" align="center">
                                ${preparedOrder.total}$
                            </td>
                    </tbody>
                </form>
            </table>
        </c:if>

        <%--<c:url var="urlViewYourCart" value="DispatchController">--%>
            <%--<c:param name="btAction" value="View Your Cart"/>--%>
        <%--</c:url>--%> 
        <!--<a href="${urlViewYourCart}" style="color: blue">Click to view your cart</a>-->        
        <a href="viewCartAction" style="color: blue">Click to view your cart</a>        
        
        
        <br>
        <%--<c:url var="urlShopping" value="DispatchController">--%>
            <%--<c:param name="btAction" value="Shopping"/>--%>
        <%--</c:url>--%> 
        <!--<a href="${urlShopping}" style="color: blue">Click to go to shopping</a>-->
        <a href="shoppingAction" style="color: blue">Click to go to shopping</a>

    </body>
</html>
