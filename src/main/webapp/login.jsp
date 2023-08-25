<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="UTF-8">
<title>Pagina di Login Pizzeria</title>
<style>
    .error-message {
        color: red;
    }
</style>
</head>

<body>

<h1>Benvenuto nella Pizzeria!</h1>

<%
String error = (String)request.getAttribute("error"); 
if(error != null && error.equals("true")){
%>

<p class = "error-message">Credenziali errate, riprova!</p>

<%
}
%>

<form action="LoginServlet" method="GET">

<label for="username">Username:</label><br>
<input type="text" id="username" name="username"><br>

<label for="password">Password:</label><br>
<input type="password" id="password" name="password"><br>

<input type="submit" value="Accedi">

</form>

</body>

</html>