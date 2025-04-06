<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="entidades.Cuentas"%>
<%@ page import="entidades.Usuarios"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />
	
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>

 
	<style type="text/css">
		<jsp:include page="Css/Eliminar.css"></jsp:include>
	</style>
    
    <script type="text/javascript">
    	$(document).ready(function() {
    		$('#cuentas_id').DataTable({
      			"searching": false, // Disables the search box
                "lengthMenu": [ [5, 10, 25, 50], [5, 10, 25, 50] ] // Custom entries per page
            });
    	});
	</script>
	

    <script type="text/javascript">        
    	function confirmarEliminacion(form) {
    		if (confirm("¿Está seguro que desea eliminar esta cuenta?")) {
    			form.submit(); // Envía el formulario si el usuario confirma
    		}
    	}
    </script>
    
</head>
<body>
<div class="encabezadoCuenta">
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
</div></div>

<div class="contenido">
    <address class="parteIzq"> <br><br><br><br><br><br><br><br>
            <input class="btn" type="button" value="Agregar Cuenta" onclick="window.location.href='servletAgregarCuentas?Param=1'"><br><br><br>
            <input class="btn" type="button" value="Listar Cuentas" onclick="window.location.href='ServletListarCuenta?Param=1'"><br><br><br>
            <input class="btn" type="button" value="Eliminar Cuentas" onclick="window.location.href='servletEliminarCuenta?Param=1'"><br><br><br>
    </address>

<div class="parteDer">
<h2>Eliminar Cuenta</h2>

      
        
<form action="servletEliminarCuenta" method="get">
<br>
<div class="info-columnss">
<div class="columnas">
    <div class="form-group"><label style="width: 155px;" class="label-agregar">Buscar por DNI:</label> <input type="text" name="txtBuscarDni"  pattern="\d+" style="height: 30px" required title="Solo puede ingresar números">
    <input class="btnFiltrar" type="submit" name="btnBuscarDni" value="Filtrar" style="width: 120px; height: 50px; margin-left: 30px"></div>
</div> 
	<div class="columnas">
    <input class="btnMostrar" type="submit" name="btnMostrarTodo" value="Mostrar Todo" style="width: 180px; height: 50px" formnovalidate>
    </div> 
</div>  
</form><br>

		<% 
            // Mensaje de acción
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) { 
        %>
            <div class="mensaje-accion"><%= mensaje %></div>
        <% 
            } 
        %>
        
        
<% 
    ArrayList<Cuentas> listaCuentas = null;
    if (request.getAttribute("listaU") != null) {
        listaCuentas = (ArrayList<Cuentas>) request.getAttribute("listaU");
    }
%>
<table id="cuentas_id" class="display">
    <thead>
        <tr>
            <th>Nro de Cuenta</th>
		    <th>DNI del Cliente</th>
		    <th>Tipo de Cuenta</th>
		    <th>Fecha de Creacion</th>
		    <th>CBU</th>
		    <th>Saldo</th>
            <th></th> 
        </tr>
    </thead>
    <tbody>
        <% if (listaCuentas!= null) {
            for (Cuentas cuenta : listaCuentas) { %>
                <tr>   
                        <td><div style="width: 68px;"><%= cuenta.getNroCuenta() %></div></td>
                        <td><div style="width: 90px;"><%= cuenta.getDni() %></div></td>
                        <td><div style="width: 138px;"><%= cuenta.getTipoDeCuenta().getNombreCuenta() %></div></td>
                        <td><div style="width: 100px;"><%= cuenta.getFechaCreacion() %></div></td>
                        <td><%= cuenta.getCbu() %></td>
                        <td><div style="width: 90px;"><%= cuenta.getSaldo() %></div></td>
                        <td>
                        	<form action="servletEliminarCuenta" method="post" onsubmit="return confirmarEliminacion(this);">
                            	<input type="hidden" name="nroCuenta" value="<%= cuenta.getNroCuenta() %>">
                            	<input class="btnEliminar" type="submit" name="btnEliminar" value="Eliminar">
                        	</form>
                        </td>
                </tr>
      	 <% } 
           } %>
    </tbody>
</table>
<br><br>
</div>
</div>
    <footer>
        <input class="btn-volver" type="button" value="Volver al Menú Principal" onclick="window.location.href='MenuAdministrador.jsp'">
        <p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
    </footer>
</body>
</html>