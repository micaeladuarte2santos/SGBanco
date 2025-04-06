<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="entidades.Clientes"%>
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
    		$('#clientes_id').DataTable({
      			"searching": false, // Disables the search box
                "lengthMenu": [ [5, 10, 25, 50], [5, 10, 25, 50] ] // Custom entries per page
            });
    	});
	</script>
	

    <script type="text/javascript">        
    	function confirmarEliminacion(form) {
    		if (confirm("¿Está seguro que desea eliminar este cliente?")) {
    			form.submit(); // Envía el formulario si el usuario confirma
    		}
    	}
    </script>
    
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
<div class="active" style="float:right;">
    <%= mensaje2 %>
</div></div>

<div class="contenido">
    <address class="parteIzq"><br><br><br><br><br><br><br>
        <input class="btn" name="btnAgregarCliente" type="button" value="Agregar Cliente" onclick="window.location.href='servletAgregarClientes?Param=1'"><br><br><br>
        <input class="btn" name ="btnListarCliente" type="button" value="Listar Cliente" onclick="window.location.href='ServletListarCliente?Param=1'"><br><br><br>
        <input class="btn" name ="btnListarCliente" type="button" value="Eliminar Cliente" onclick="window.location.href='servletEliminarCliente?Param=1'"><br><br><br>
    </address>

<div class="parteDer">
<h2>Eliminar Clientes</h2>
<br>
		
        
<form action="servletEliminarCliente" method="get">
<br>
<div class="info-columns">
<div class="columna">
    <label class="label-agregar">Buscar por DNI:</label> <input type="text" name="txtBuscarDni" pattern="\d+"  style="height: 30px" required title="Solo puede ingresar números">
    <input class="btnFiltrar" type="submit" name="btnBuscarDni"  value="Filtrar" style="width: 120px; height: 50px">
</div> 
	<div class="columna">
    <input class="btnMostrar" type="submit" name="btnMostrarTodo" value="Mostrar Todo" style="width: 180px; height: 50px" formnovalidate>
    </div> 
</div>  
</form>

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
    ArrayList<Clientes> listaClientes = null;
    if (request.getAttribute("listaU") != null) {
        listaClientes = (ArrayList<Clientes>) request.getAttribute("listaU");
    }
%>
<table id="clientes_id" class="display">
    <thead>
        <tr>
            <th>DNI</th>
            <th>CUIL</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Sexo</th>
            <th>Nacionalidad</th>
            <th>Fecha de Nacimiento</th>
            <th>Direccion</th>
            <th>Localidad</th>
            <th>Provincia</th>
            <th>Correo</th>
            <th>Telefono</th>
            <th>Usuario</th>
            <th>Contraseña</th>
            <th></th> 
        </tr>
    </thead>
    <tbody>
        <% if (listaClientes != null) {
            for (Clientes cliente : listaClientes) { %>
                <tr>   
                        <td><%= cliente.getDni() %></td>
                        <td><%= cliente.getCuil() %></td>
                        <td><%= cliente.getNombre() %></td>
                        <td><%= cliente.getApellido() %></td>
                        <td><%= cliente.getSexo() %></td>
                        <td><%= cliente.getNacionalidad() %></td>
                        <td><%= cliente.getFechaNacimiento() %></td>
                        <td><div style="width: 140px;"><%= cliente.getDireccion() %></div></td>
                        <td><div style="width: 130px;"><%= cliente.getLocalidad() %></div></td>
                        <td><div style="width: 130px;"><%= cliente.getProvincia() %></div></td>
                        <td><div style="width: 150px;"><%= cliente.getCorreo() %></div></td>
                        <td><%= cliente.getTelefono() %></td>
                        <td><%= cliente.getUsuario().getIdUsuario() %></td>
                        <td><%= cliente.getUsuario().getContraseña() %></td>   
                        <td>
                        	<form action="servletEliminarCliente" method="post" onsubmit="return confirmarEliminacion(this);">
                            	<input type="hidden" name="dni" value="<%= cliente.getDni() %>">
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
    <footer class="eliminarCliente">
        <input class="btn-volver" type="button" value="Volver al Menú Principal" onclick="window.location.href='MenuAdministrador.jsp'">
        <p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
    </footer>
</body>
</html>