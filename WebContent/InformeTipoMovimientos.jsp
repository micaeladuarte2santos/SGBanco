<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="entidades.Usuarios"%>
<!DOCTYPE html>
<html>
<head>
    <title>Gráfico de Tipos de movimientos</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="Css/Global.css">
<link rel="stylesheet" type="text/css" href="Css/SubMenu.css">

</head>
<body>
<div class="encabezado">
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
	<div class="mensaje-contenedor">
            <div class="active">
                <%= mensaje2 %>
            </div>
    </div>

	</div>
	
	
	<div class="contenido">
		<address class="parteIzq">
	    	<br><br><br><br><br><br><br><br>
	        <input class="btn" type="button" value="Informe 1" onclick="window.location.href='servletInformePrestamos?Param=1'"><br><br><br>
		    <input class="btn" name="btnInforme2" type="submit" value="Informe 2" onclick="window.location.href='servletsInformeMovimiento?Param=1'"><br><br>
		</address>
		
		<div class="parteDer">
			<h2>Porcentaje de movimientos según su tipo</h2>
			<form method="get" action="servletsInformeMovimiento">
			    <label for="fechaInicio">Fecha Inicio:</label>
			    <input type="date" id="fechaInicio" name="fechaInicio" required>
			    <label for="fechaFin">Fecha Fin:</label>
			    <input type="date" id="fechaFin" name="fechaFin" required>
		    	<input class="btnGenerarGrafico" type="submit" name="btnGenerarGrafico" value="Generar Grafico">
			</form>
		
			<h3>Resultado</h3>
		    <img src="servletsInformeMovimiento?fechaInicio=<%= request.getParameter("fechaInicio") %>&fechaFin=<%= request.getParameter("fechaFin") %>" alt="Gráfico de movimientos">

		</div>
	</div>

    <footer>
        <input class="btn-volver" type="button" value="Volver al Menú Principal" onclick="window.location.href='MenuAdministrador.jsp'">
        <p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
    </footer>


</body>
</html>