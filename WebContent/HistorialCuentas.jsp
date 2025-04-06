<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="entidades.Usuarios"%>
<%@ page import="entidades.Cuentas"%>
<%@ page import="entidades.Movimientos"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="ISO-8859-1">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    
    <link rel="stylesheet" href="Css/UnoEnGeneral.css" type="text/css">

	<style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) { background-color: #f2f2f2; }

        th {
            background-color: #04AA6D;
            color: white;
        }
    </style>
    
     <style type="text/css">
		<jsp:include page="Css/AutorizarPrestamos.css"></jsp:include>
	</style>
	

    <script type="text/javascript">
        $(document).ready(function() {
            $('#movimientos_id').DataTable({
                "searching": false // Desactiva la búsqueda
            });
        });
       
    </script>
</head>
<body>


<div class="encabezado"><br><%
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
</div>


<div class="contenido">

<h2>Historial de Movimientos</h2>
<form action="servletHistorialCuentas" method="get">
<br>
<div class="info-columns">
<div class="columna">
 <label class="label-agregar">Filtrar por Nro.de Cuenta:</label>
		<select name="numeroCuenta" required>
            <option value="">--Seleccione Nro.de Cuenta--</option>
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
     </div> 
	<div class="columna">
		<input class="btnMostrarTodo" type="submit" name="btnMostrarTodo" value="Mostrar Todo" style="width: 120px; height: 50px">
		<input class="btnFiltrar" type="submit" name="btnFiltrar" value="Filtrar" style="width: 120px; height: 50px">
	</div> 
</div>  
</form><br>
<br>
<% 
    ArrayList<Movimientos> listaMovimientos = null;
    if (request.getAttribute("listaU") != null) {
        listaMovimientos = (ArrayList<Movimientos>) request.getAttribute("listaU");
    }
%>

<table id="movimientos_id" class="display">
    <thead>
        <tr>
            <th>ID de Movimiento</th>
            <th>Número de Cuenta</th>
            <th>Fecha del Movimiento</th>
            <th>Detalle</th>
            <th>Importe</th>
            <th>Tipo de Movimiento</th>

        </tr>
    </thead>
    <tbody>
    	<% if (listaMovimientos != null) {
            for (Movimientos mov : listaMovimientos) { %>
                <tr>   
                        <td><%= mov.getIdMovimiento() %></td>
                        <td><%= mov.getNroCuenta_M() %></td>
		                <td><%= mov.getFecha() %></td>
		                <td><%= mov.getDetalle() %></td>
		                <td><%= mov.getImporte() %></td>
		                <td><%= mov.getTipoDeMovimiento().getNombre() %></td>
  
                </tr>
      	 <% } 
           } %>
        
    </tbody>
</table>
</div>

	<footer>
        <input class="btn-volver" type="button" value="Volver al Menú de Clientes" onclick="window.location.href='MenuCliente.jsp'">
        <p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
    </footer>

</body>
</html>