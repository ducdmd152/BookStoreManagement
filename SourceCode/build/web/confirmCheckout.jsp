<%-- 
    Document   : confirmCheckout
    Created on : Oct 26, 2022, 8:52:58 AM
    Author     : MSI
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Confirm Page</title>
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

        <h1>Checkout Confirm</h1>

        <c:set var = "preparedOrder" value = "${requestScope.PREPARED_ORDER}"/>
        <c:set var = "userErrors" value = "${requestScope.USER_ERROR}"/>

        <c:if test="${not empty preparedOrder}">

            <%-- Input infor for contacting & delivery--%>
            <!--<form action="DispatchController" METHOD="POST">-->
            <form action="checkOutAction" METHOD="POST">
                Name* (4-30 chars)<br>
                <input type="text" name="txtName" 
                       value="${param.txtName}" />
                <br>
                <c:if test="${not empty userErrors}">                    
                    <font color="red"  style="font-style: italic">
                    ${userErrors.nameLengthError}
                    </font>
                </c:if>
                <br>

                Phone number* (10-12 digits)<br>
                <input type="text" name="txtPhoneNumber" 
                       value="${param.txtPhoneNumber}" />
                <br>
                <c:if test="${not empty userErrors}">                    
                    <font color="red" style="font-style: italic">
                    ${userErrors.phoneNumberIsNotValid}
                    </font>
                </c:if>
                <br>

                Address*<br>
                <input type="text" name="txtAddress"
                       value="${param.txtAddress}" width="300"/>
                <br>
                <c:if test="${not empty userErrors}">                    
                    <font color="red"  style="font-style: italic">
                    ${userErrors.addressLengthError}
                    </font>
                </c:if>
                <br>

                <input type="hidden" name="preparedOrderID"
                       value="${preparedOrder.id}" />

                <input type="submit" value="Checkout" name="btAction" />
                <br>
                <br>
            </form>

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

                            <td>
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
            </table>
        </c:if>

        <c:if test="${empty preparedOrder}">
            <h2>No item for checkout</h2>
            <a href="shoppingAction" style="color: blue">Go to shopping right now</a>            
        </c:if>
    </body>
</html>
