<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entidades.Usuarios"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
    <jsp:include page="Css/MenuPrincipal.css"></jsp:include>
</style>
<title>Menu Administrador</title>
</head>
<body>
<%
    // Obtenemos el usuario desde la sesión
    Usuarios usuario = (Usuarios) session.getAttribute("usuario");
    String mensaje2;

    // Verificamos si el usuario está en la sesión
    if (usuario != null) {
        mensaje2 = "Bienvenido/a " + usuario.getNombreUsuario();
    } else {
        mensaje2 = "Por favor, inicie sesión.";
    }
%>
<div class="active" style="float:right;">
    <%= mensaje2 %>
</div>

<h1>MENU ADMINISTRADOR</h1>
	<br>
	<div class="menu-container">

			<button class="boton" type="button" value="Menu Cliente" onclick="window.location.href='servletSubMenuCliente?Param=1'">Menú Clientes</button>
			<br><br>
			<button class="boton" type="button" value="Menu Cuenta" onclick="window.location.href='servletSubMenuCuenta?Param=1'">Menú Cuentas</button>
			<br><br>
			<button class="boton" type="button" value="Menu Prestamos" onclick="window.location.href='servletAutorizarPrestamos?Param=1'">Menú Prestamos</button>
			<br><br>
			<button class="boton" type="button" value="Menu Informes" onclick="window.location.href='servletSubMenuInformes?Param=1'">Menú Informes</button>
			
	</div>
</body>
    <footer>
    	<p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
	</footer>

</head>
</html>