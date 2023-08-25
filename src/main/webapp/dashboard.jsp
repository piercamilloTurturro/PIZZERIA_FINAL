<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@page import="model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Pizzeria</title>
</head>
<script type="text/javascript" src="./js/pizzeria.js"></script>
<body>

<h1>Benvenuto nella tua Dashboard!</h1>

<%
	List<Pizza> userPizzas = (List<model.Pizza>) session.getAttribute("userPizzas");
	List<Dough> doughList = (List<Dough>)session.getAttribute("doughList");
	List<Ingredient> ingredients = (List<Ingredient>)session.getAttribute("ingredientList");
	
	if (userPizzas != null) {
%>

<h1>Le tue pizze:</h1>

<c:if test="${empty userPizzas}">
  <p>Non hai ancora pizze</p> 
</c:if>


<c:if test="${not empty userPizzas}">
<c:forEach var="pizza" items="${userPizzas}">
  <div class="pizza">
    <h2>${pizza.name}</h2>
    <p>
      <strong>Impasto:</strong>
      ${pizza.dough.name} 
    </p>
    <p>
      <strong>Ingredienti:</strong>
      <c:forEach var="ingredientselected" items="${pizza.ingredients}">
        ${ingredientselected.name},
      </c:forEach>
    </p>    	
    <form action="modifica_pizza.jsp" method="post">
	    <input type="hidden" name="pizzaId" value="${pizza.id}">
	    <input type="submit" name="action" value="Modifica">
	    <input type="submit" name="action" value="Cancella" 
	           onclick="messaInModifica();">
  	</form>
  </div>
</c:forEach>
</c:if>


<h1>Crea Nuova Pizza</h1>


<form action="Dashboard" method="post">
 <label for="namePizza">Nome:</label><br>
  <input type="text" id="namePizza" name="namePizza"><br>
  
  <label for="dough">Impasto:</label><br>
  <select name="dough">
    <c:forEach items="${doughList}" var="dough">
      <option value="${dough.id}">${dough.name}</option>
    </c:forEach>
  </select><br>

  <label for="ingredients">Ingredienti:</label><br>  
   <% for (Ingredient ingredient : ingredients) { %>
    <div>
      <input type="checkbox" id="<%= ingredient.getId() %>" name="ingredients" value="<%= ingredient.getId() %>">
      <label for="<%= ingredient.getId() %>"><%= ingredient.getName() %></label>
    </div>   
  <% } %>

  <input type="submit" value="Crea Pizza">
</form>
<% } else { %>
    <p>errroe utente di sessione</p>
<% } %>


</body>
</html>
