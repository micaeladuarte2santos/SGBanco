<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px; 
}

form {
  border: 2px solid #f1f1f1; /* Reducimos el tamaño del borde */
}

input[type=text], input[type=password] {
  width: 100%;
  padding: 8px 12px; 
  margin: 6px 0; 
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

button {
  background-color: #04AA6D;
  color: white;
  padding: 10px 16px; 
  margin: 6px 0;
  border: none;
  cursor: pointer;
  width: 100%;
}

button:hover {
  opacity: 0.8;
}

.imgcontainer {
  text-align: center;
  margin: 16px 0 10px 0; 
}

/* Mensajes */
.mensaje-accion {
    position: relative;
    margin: 90px auto 20px; /* Espacio debajo del encabezado */
    padding: 15px 20px;
    background-color: #d4edda; /* Verde claro */
    color: #155724; /* Verde oscuro */
    border: 1px solid #c3e6cb; /* Borde verde */
    border-radius: 5px;
    font-weight: bold;
    text-align: center;
    max-width: 80%; /* Centrado y limitado en ancho */
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Sombra para destacar */
}

img.avatar {
  width: 20%; 
  border-radius: 50%;
}

.container {
  padding: 12px;
}

span.psw {
  float: right;
  padding-top: 12px; 
}

h2 {
  font-size: 18px; 
}


</style>
</head>
<body>

<h2>Iniciar Sesión</h2>

<% 
	// Mensaje de acción
	String mensaje = (String) request.getAttribute("mensaje");
	if (mensaje != null) { 
%>
	<div class="mensaje-accion"><%= mensaje %></div>
<% 
	} 
%>

<form action="servletUsuario" method="post">
  <div class="imgcontainer">
    <img src="imagenes/imagen4.jpg" alt="Avatar" class="avatar">
  </div>

  <div class="container">
    <label for="uname"><b>Usuario</b></label>
    <input type="text" placeholder="Ingrese nombre de usuario" name="usuario" required>

    <label for="psw"><b>Contraseña</b></label>
    <input type="password" placeholder="Ingrese contraseña" name="contraseña" required>
        
    <button type="submit" name="btnIngresar" value="Ingresar">Ingresar</button>
  </div>

</form>

</body>
</html>
