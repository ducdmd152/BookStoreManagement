<%-- 
    Document   : login
    Created on : Mar 11, 2022, 9:02:11 PM
    Author     : hd
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login Page</h1>
        
        <!--<form action="DispatchController" method="POST">-->
        <form action="loginAction" method="POST">
            Username
            <input type="text" name="txtUsername" value="" />
            <br>
            
            <br>
            Password
            <input type="password" name="txtPassword" value="" />
            <br>
            
            <font color="red" style="font-style: italic">
            ${requestScope.ERROR_MESSAGE}
            </font>
            
            <br>
            <input type="submit" value="Login" name="btAction"/>            
            <input type="reset" value="Reset" />
        </form>
        
        <br>
        <!--Need a new account? <a href="register.html">Register here</a>-->
        Need a new account? <a href="registerPage">Register here</a>
        <br>
        <br>
        <!--<a href="DispatchController?btAction=Shopping">Click here to get more knowledge</a>-->
        <a href="shoppingAction">Click here to get more knowledge</a>
    </body>  
</html>

