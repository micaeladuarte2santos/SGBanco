<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import= "entidades.Usuarios" %>
<%@ page import= "entidades.TipoCuentas" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agregar Cuenta</title>
<link rel="stylesheet" type="text/css" href="Css/Global.css">
<link rel="stylesheet" type="text/css" href="Css/Agregar_Pedir.css">
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
    <%
        boolean agregado = false;
        if(request.getAttribute("agregar") != null){
            agregado = (boolean)request.getAttribute("agregar");    
        }
    %>
    
    <div class="contenido">
        <address class="parteIzq">
            <br><br><br><br><br><br><br><br>
            <input class="btn" type="button" value="Agregar Cuenta" onclick="window.location.href='servletAgregarCuentas?Param=1'"><br><br><br>
            <input class="btn" type="button" value="Listar Cuentas" onclick="window.location.href='ServletListarCuenta?Param=1'"><br><br><br>
            <input class="btn" type="button" value="Eliminar Cuentas" onclick="window.location.href='servletEliminarCuenta?Param=1'"><br><br><br>
        </address>

        <div class="parteDer"> 
            <h2>Agregar Cuenta</h2>
            <br>
            <% 
		    // Mensaje de acción
		    	String mensaje = (String) request.getAttribute("mensaje");
			    if (mensaje != null) { 
			%>
			    <div class="mensaje-accion"><%= mensaje %></div>
			<% 
			    } 
			%>
			<br>
		
            <form action="servletAgregarCuentas" method="get">		    
                <div class="form-group">
                    <label class="label-agregar">CBU:</label>
                    <input type="text" name ="txtCbu" required>
                </div>
                <div class="form-group">
                    <label class="label-agregar">DNI del cliente:</label>
                    <input type="text" name ="txtDni" required>
                </div> <br>
                <div class="form-group">
                    <label class="label-agregar">Tipo de cuenta:</label>
                    <select  style="height: 35px" name="ddlTipoCuenta" required>
                        <option value="">Seleccione un Tipo de cuenta</option>
                        <% 
                            ArrayList<TipoCuentas> ddlTiposCuentas = (ArrayList<TipoCuentas>) request.getAttribute("tiposCuentas");
                        	if(ddlTiposCuentas != null){
                            for (TipoCuentas tipo : ddlTiposCuentas) {
                        %>
                            <option value="<%= tipo.getIdTipoCuenta() %>"><%= tipo.getNombreCuenta() %></option>
                        <% } 
                        }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label class="label-agregar">Fecha de creacion:</label>
                    <input type="date" id="fecha" name ="FechaCreacion" required>
                </div>
                <div class="form-group">
                    <label class="label-agregar">Numero de cuenta:</label>
                    <input type="text" name ="txtNroCuenta" pattern="\d+" required title="Solo puede ingresar números">
                </div><br>
                <div class="button-container">
                    <button class="BtnAceptar" name="btnAgregar" type="submit">Agregar</button>
                </div><br>
            </form>
        </div>
    </div>
    <footer>
        <input class="btn-volver" type="button" value="Volver al Menú Principal" onclick="window.location.href='MenuAdministrador.jsp'">
        <p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
    </footer>
</body>
</html>
