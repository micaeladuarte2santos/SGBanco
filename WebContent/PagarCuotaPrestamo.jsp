<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="entidades.Usuarios"%>
<%@ page import="entidades.Cuotas" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Prestamo</title>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<link rel="stylesheet" type="text/css" href="Css/Global.css">
	<link rel="stylesheet" type="text/css" href="Css/SubMenu.css">
	<link rel="stylesheet" type="text/css" href="Css/Agregar_Pedir.css">
	<style type="text/css">
		<jsp:include page="Css/Prestamo.css"></jsp:include>
	</style>
	
    <script>
    
    	function confirmarPago(form){
    		if(confirm("¿Está seguro que desea pagar la cuota?")){
    			form.submit();
    		}
    	}
    </script>
    
    <script type="text/javascript">
        $(document).ready(function() {
            $('#cuotas_id').DataTable({
                "searching": false, // Desactiva la búsqueda
                "lengthMenu": [ [5, 10, 25, 50], [5, 10, 25, 50] ]
            });
        });    
    </script>
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
	    <address class="parteIzq">
	    	<br><br><br>
	    	<input class="btn" type="button" value="Transferir" onclick="window.location.href='servletTransferir?Param=1'"><br><br>
		    <input class="btn" type="button" value="PagoPrestamos" onclick="window.location.href='servletPagoPrestamos?Param=1'"><br><br>
		    <input class="btn" type="button" value="PedirPrestamos" onclick="window.location.href='servletPedirPrestamos?Param=1'"><br><br>
		    <input class="btn" type="button" value="InfoPersonal" onclick="window.location.href='servletInfoPersonal?Param=1'"><br><br>
	    </address>
	 <div class="parteDer"> 
		<h2>Pagos de Prestamos</h2>
		<br><br>
		<% 
		    // Verifica si hay un mensaje y lo muestra
		    String mensaje = (String) request.getAttribute("mensaje");
		    if (mensaje != null) { 
		%>
		        <p style="color: green; font-weight: bold;"><%= mensaje %></p>
		<% 
		    } 
		%>
		
		<% 
		    ArrayList<Cuotas> listaCuotas = null;
		    if (request.getAttribute("listaU") != null) {
		        listaCuotas = (ArrayList<Cuotas>) request.getAttribute("listaU");
		    }
		%>
		<form action="servletPagoPrestamos" method="get">
			<br>
			<div class="info-columns">
			<div class="columna">
                    <label class="label-agregar">Buscar por Id Prestamo:</label>
                    <input type="text" name="txtBuscarPrestamo" pattern="\d+" style="height: 35px; border: 1px solid #ccc;border-radius: 5px;" required title="Solo puede ingresar números">  
                    <input class="btnFiltrarI" type="submit" name="btnBuscarPrestamo" value="Buscar" >
            </div>
            <div class="columna">
	             <input class="btnMostrar" type="submit" name="btnMostrarTodo" value="Mostrar Todo" style="width: 180px; height: 60px; margin-left: 70px;" formnovalidate>
      		  </div> 
			</div>  
		</form><br>
		
		<table id="cuotas_id" class="display">
		    <thead>
		        <tr>
		            <th>ID Cuota</th>
		            <th>ID Prestamo</th>
		            <th>Numero de Cuota</th>
		            <th>Fecha de vencimiento</th>
		            <th>Monto</th>	
		            <th></th>          
		        </tr>
		    </thead>
		    <tbody>   
		    	<% if (listaCuotas != null) {
		    		int filaId = 0;
		    		for (Cuotas cuota : listaCuotas){ %>
		    			<tr>
		    				<td><%= cuota.getIdCuota() %></td>
		    				<td><%= cuota.getIdPrestamo() %></td>
		    				<td><%= cuota.getNumeroCuota() %></td>
		    				<td><%= cuota.getFechaVencimientoCuota() %></td>
		    				<td><%= cuota.getMonto() %></td>
		    				<td>
		    				<form action="servletPagoPrestamos" method="post" onsubmit="return confirmarPago(this);">
		    					<select name="numeroCuenta">
						            <% 
						                List<Integer> cargarDDL = (List<Integer>) request.getAttribute("cargaDDL");
						                if (cargarDDL != null) {
						                    for (Integer nroCuenta : cargarDDL) { 
						            %>
						                <option value="<%= nroCuenta %>"><%= nroCuenta %></option>
						            <% 
						                    }
						                }
						            %>
						        </select>
                            	<input type="hidden" name="idCuota" value="<%= cuota.getIdCuota() %>">
                            	<input type="hidden" name="numCuota" value="<%= cuota.getNumeroCuota() %>">
                            	<input type="hidden" name="idPrestamo" value="<%= cuota.getIdPrestamo() %>">
                            	<input type="hidden" name="monto" value="<%= cuota.getMonto() %>">
                            	<input class="BtnPagar" type="submit" name="btnPagar" value="Pagar cuota">
                        	</form>
		    				</td>
		    			</tr>
		    			
		        <% filaId++; }
		    	 }
		    	%>   
		    </tbody>
		</table>
	    </div>
	</div>

	<footer>
		<input class="btn-volver" type="button" value="Volver al Menú de Clientes" onclick="window.location.href='MenuCliente.jsp'">
	    <p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
	</footer>
</body>
</html>