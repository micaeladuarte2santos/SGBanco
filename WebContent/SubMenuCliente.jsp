<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="entidades.Usuarios"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="Css/Global.css">
    <link rel="stylesheet" type="text/css" href="Css/SubMenu.css">
    <title>Submenú Cliente</title>
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
        <div class="mensaje-contenedor">
            <div class="active">
                <%= mensaje2 %>
            </div>
        </div>
        
    <br>

    <div class="contenido">
        <div class="parteIzq" >
            <br><br><br><br><br>
            <input class="btn" type="button" value="Agregar Cliente" onclick="window.location.href='servletAgregarClientes?Param=1'"><br><br>
            <input class="btn" type="button" value="Listar Cliente" onclick="window.location.href='ServletListarCliente?Param=1'"><br><br>
            <input class="btn" type="button" value="Eliminar Cliente" onclick="window.location.href='servletEliminarCliente?Param=1'"><br><br>
        </div>


        <div class="parteDer">
            <h1>Submenú del Cliente</h1>
        </div>
    </div>

    <footer>
        <!-- Botón para volver al menú principal -->
        <input class="btn-volver" type="button" value="Volver al Menú Principal" onclick="window.location.href='MenuAdministrador.jsp'">
        
        <!-- Cerrar sesión -->
        <p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
    </footer>
</body>
</html>