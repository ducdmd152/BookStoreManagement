<%-- 
    Document   : ViewCart
    Created on : Oct 14, 2022, 9:05:23 AM
    Author     : MSI
--%>

<%--<%@page import="java.util.Map"%>
<%@page import="ducdmd.cart.CartObject"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart</title>
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
        
        <h1>Your Cart</h1>

        <c:if test="${not empty requestScope.ITEMS_IN_CART}">
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
                        <th>Remove</th>
                        <th>Checkout</th>
                    </tr>
                </thead>
                <!--<form action="DispatchController">-->                            
                <form action="cartAction" method="POST">                            
                    <tbody>                  
                        <c:forEach var="item" items="${requestScope.ITEMS_IN_CART}" varStatus="counter">
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
                                                                                                
                                <td>
                                    <input type="checkbox" name="chkItem"
                                           value="${item.sku}" />
                                </td>
                                
                                <td>
                                    <input type="checkbox" name="checkOutItem"
                                           value="${item.sku}" />
                                </td>
                            </tr>

                        </c:forEach>
                        <tr>
                            
                            <td colspan="6" align="center">
                                <!--<a href="DispatchController?btAction=Shopping">Add more items to Your Cart</a>-->
                                <a href="shoppingAction">Add more items to Your Cart</a>
                            </td>
                            
                            <td>
                                <input type="submit"
                                       value="Remove Selected Items"
                                       name="btAction" />
                            </td>

                            <td colspan="4" align="center">
                                <input type="submit"
                                       value="Checkout Selected Items"
                                       name="btAction" />
                            </td>                                                       
                        </tr>
<!--                        <tr>
                            <td colspan="8" align="center">
                                <a href="DispatchController?btAction=Check out">Check out all items</a>
                            </td>
                        </tr>-->
                    </tbody>
                </form>
            </table>
        </c:if>

        <c:if test="${empty requestScope.ITEMS_IN_CART}">
            <h2>No item exist in your cart</h2>
            <a href="shoppingAction" style="color: blue">Add more items to Your Cart right now</a>
        </c:if>

        <%-- <h1>Your Cart</h1>
        <%
            //1. Cust goes to his/her cart placement
            if (session != null) {
                //2. Cust takes his/her cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                if(cart!=null) {                    
                    //3. Cust takes items
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        //4. System traverses items to show
                        %>
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Name</th>
                                    <th>Quantity</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <form action="DispatchController">                            
                                <tbody>
                                    <%
                                        int count = 0;
                                        for(String key : items.keySet()) {
                                            int value = items.get(key);
                                            %>
                                            <tr>                                            
                                                <td>
                                                    <%= ++count %>
                                                .</td>

                                                <td>
                                                    <%= key %>
                                                </td>

                                                <td>
                                                    <%= value %>
                                                </td>

                                                <td>
                                                    <input type="checkbox" name="chkItem"
                                                           value="<%= key%>" />
                                                </td>
                                            </tr>
                                    <%
                                        }
                                    %>
                                    <tr>                                        
                                        <td colspan="3">
                                            <a href="DispatchController?btAction=Shopping">Add more items to Your Cart</a>
                                        </td>
                                        <td>
                                            <input type="submit"
                                                   value="Remove Selected Items"
                                                   name="btAction" />
                                        </td>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="4" align="center">
                                            <a href="DispatchController?btAction=Check out">Check out</a>
                                        </td>
                                    </tr>
                                </tbody>
                            </form>
                        </table>

        <%
                        return;                        
                    }
                }
            }            
        %>
        
        <h2>
            No cart is existed!!!            
        </h2>
        <a href="DispatchController?btAction=Shopping">Add more items to Your Cart</a> --%>
    </body>
</html>
