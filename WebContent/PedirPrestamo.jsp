<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="entidades.Usuarios"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="Css/Global.css">
<link rel="stylesheet" type="text/css" href="Css/SubMenu.css">
<link rel="stylesheet" type="text/css" href="Css/Agregar_Pedir.css">
<title>Insert title here</title>
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
   </div>
    <%
        boolean agregado = false;
        if(request.getAttribute("agregar") != null){
            agregado = (boolean)request.getAttribute("agregar");    
        }
    %>
<br>

<div class="contenido">
    <address class="parteIzq">
    	<br><br><br><br><br><br>
    	<input class="btn" type="button" value="Transferir" onclick="window.location.href='servletTransferir?Param=1'"><br><br>
	    <input class="btn" type="button" value="PagoPrestamos" onclick="window.location.href='servletPagoPrestamos?Param=1'"><br><br>
	    <input class="btn" type="button" value="PedirPrestamos" onclick="window.location.href='servletPedirPrestamos?Param=1'"><br><br>
	    <input class="btn" type="button" value="InfoPersonal" onclick="window.location.href='servletInfoPersonal?Param=1'"><br><br>
    </address>
    
    <div class="parteDer"> 
        <h2>Pedir Prestamo</h2>
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
        <form action="servletPedirPrestamos" method="get">
        <input type="hidden" name="Param" value="1">
	        <div class="form-group">
	            <label class="label-prestamo">ID Préstamo:</label>
	            <!-- <input type="text"> -->
	            <span class="auto-id">1</span>
	        </div>
	        <div class="form-group">
	            <label class="label-prestamo">DNI del Cliente:</label>
	            <input type="text" name="txtDni" pattern="\d+" required title="Solo puede ingresar números">
	        </div>
	        <div class="form-group">
	            <label class="label-prestamo">Nro de Cuenta:</label>
	            <input type="text" name="txtNroCuenta" pattern="\d+" required title="Solo puede ingresar números">
	        </div>
	        <div class="form-group">
	            <label class="label-prestamo">Fecha del Préstamo:</label>
	            <input type="date" name="Fecha" required>
	        </div>
	        <div class="form-group">
	            <label class="label-prestamo">Importe con Intereses:</label>
	            <input type="text" name="txtImporteIntereses" pattern="\d+" required title="Solo puede ingresar números">
	        </div>
	        <div class="form-group">
	            <label class="label-prestamo">Importe del Pedido del Cliente:</label>
	            <input type="text" name="txtImportePedido" pattern="\d+" required title="Solo puede ingresar números">
	        </div>
	        <div class="form-group">
	            <label class="label-prestamo">Plazo de Pago:</label>
	            <input type="text" name="txtPlazo" required>
	        </div>
	        <div class="form-group">
	            <label class="label-prestamo">Monto por Mes:</label>
	            <input type="text" name="txtMonto" pattern="\d+" required title="Solo puede ingresar números">
	        </div><br>
	        <div class="button-container">
	        	<button class="BtnAceptar" name= "btnPedirPrestamo" value="btnPedirPrestamo" type="submit">Pedir</button>
    		</div><br>
	    </form>
    </div>
</div>

<footer>
	<input class="btn-volver" type="button" value="Volver al Menú de Clientes" onclick="window.location.href='MenuCliente.jsp'">
	<p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
</footer>
</body>
    
</html>