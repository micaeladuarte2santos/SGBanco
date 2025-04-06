<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="entidades.Usuarios" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidades.Prestamos" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    
    <style type="text/css">
		<jsp:include page="Css/AutorizarPrestamos.css"></jsp:include>
	</style>
 

	<script type="text/javascript">
		$(document).ready(function() {
		    $('#prestamos_id').DataTable({
                "lengthMenu": [ [5, 10, 25, 50], [5, 10, 25, 50] ]
		    });
		});
	</script>
	
</head>
<body>

    <header class="encabezado">
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
        
    </header> 

    <main class="contenido">
        <h2>Autorizar Préstamos</h2><br>
        
        <% 
            // Mensaje de acción
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) { 
        %>
            <div class="mensaje-accion"><%= mensaje %></div>
        <% 
            } 
        %>
        
        
        <form action="servletAutorizarPrestamos" method="get">
		<br>
		<div class="info-columnss">
		<div class="columnas">
		    <div class="form-group"><label style="width: 155px;" class="label-agregar">Buscar por número de cuenta:</label> <input type="text" name="txtBuscarCuenta"  pattern="\d+" style="height: 30px" required title="Solo puede ingresar números">
		    <input class="btnFiltrar" type="submit" name="btnBuscarCuenta" value="Filtrar" style="width: 120px; height: 50px; margin-left: 30px"></div>
		</div> 
			<div class="columnas">
		    <input class="btnMostrar" type="submit" name="btnMostrarTodo" value="Mostrar Todo" style="width: 180px; height: 50px" formnovalidate>
		    </div> 
		</div>  
		</form><br>
        
        <table id="prestamos_id" class="display">
            <thead>
                <tr>
                    <th>ID Préstamo</th>
                    <th>DNI Cliente</th>
                    <th>Nro de Cuenta</th>
                    <th>Fecha del Préstamo</th>
                    <th>Importe con intereses</th>
                    <th>Importe pedido por el cliente</th>
                    <th>Plazo de pago en meses</th>
                    <th>Monto a pagar por mes</th>
                    <th>Cant. Cuotas</th>
                    <th>Estado</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Prestamos> listaPrestamos = (ArrayList<Prestamos>) request.getAttribute("listaU");
                    if (listaPrestamos != null) {
                        for (Prestamos prestamo : listaPrestamos) {
                %>
                    
                        <tr>
                            <td><%= prestamo.getIdPrestamo() %></td>
                            <td><%= prestamo.getDni() %></td>
                            <td><%= prestamo.getNroCuenta() %></td>
                            
                            <td><%= prestamo.getFechaPrestamo() %></td>
                            <td><%= prestamo.getImporteConIntereses() %></td>
                            <td><%= prestamo.getImportePedidoPorCliente() %></div></td>
                            
                            <td><%= prestamo.getPlazoPago() %></td>
                            <td><%= prestamo.getMontoPorMes() %></td>
                            
                            <td><%= prestamo.getCantidadCuotas() %></td>
                            <form action="servletAutorizarPrestamos" method="post">
	                        	<td>
	                        
		                            <input type="hidden" name="nroCuenta" value="<%= prestamo.getNroCuenta() %>">
		                           	<input type="hidden" name="montoPorMes" value="<%= prestamo.getMontoPorMes() %>">
		                           	<input type="hidden" name="cantidadCuotas" value="<%= prestamo.getCantidadCuotas() %>">
		                           	<input type="hidden" name="importePedidoPorCliente" value="<%= prestamo.getImportePedidoPorCliente() %>">
		                            <input type="hidden" name="idPrestamo" value="<%= prestamo.getIdPrestamo() %>">
		                               
		                           <select name="ddlEstado">
		                                <option value="Pendiente" <%= "Pendiente".equals(prestamo.getEstado()) ? "selected" : "" %>>Pendiente</option>
		                                <option value="Aprobado" <%= "Aprobado".equals(prestamo.getEstado()) ? "selected" : "" %>>Aprobado</option>
		                               <option value="Rechazado" <%= "Rechazado".equals(prestamo.getEstado()) ? "selected" : "" %>>Rechazado</option>
		                            </select>
		                       
	                         	</td>
		                         <td>
		                         		<input class="BtnModificar" type="submit" name="btnModificar" value="Modificar">
		                         </td>
	                        </form>
                        </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </main>
	

    <!-- Footer -->
    <footer>
        <input class="btn-volver" type="button" value="Volver al Menú Principal" onclick="window.location.href='MenuAdministrador.jsp'">
        <p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
    </footer>
</body>
</html>