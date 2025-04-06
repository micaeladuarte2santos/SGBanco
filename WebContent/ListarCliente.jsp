<%@ page import="java.util.ArrayList" %>
<%@ page import="entidades.Provincias" %>
<%@ page import="entidades.Clientes" %>
<%@ page import= "entidades.Usuarios" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Clientes</title>

<style type="text/css">
    <jsp:include page="Css/ListarCliente.css"></jsp:include>
</style>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script>
        function cargarLocalidades(filaId) {
            const provinciaId = document.getElementById("ddlProvincia_" + filaId).value;
            const xhr = new XMLHttpRequest();
            xhr.open("GET", "ServletListarCliente?idProvincia=" + provinciaId, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    document.getElementById("ddlLocalidad_" + filaId).innerHTML = xhr.responseText;
                }
            };
            xhr.send();
        }
        
        function confirmarModificacion(event, filaId) {
   
    try {
        const confirmar = confirm("¿Estás seguro de que deseas modificar este cliente?");
        if (!confirmar) {
            event.preventDefault(); 
            window.location.reload();
        }
    } catch (error) {
        console.error("Ocurrió un error al intentar modificar el cliente:", error);
        alert("Hubo un problema al intentar modificar el cliente. Por favor, inténtalo de nuevo.");
        event.preventDefault(); 
    }
}
        
        
</script>
         <script type="text/javascript">
    	$(document).ready(function() {
    		$('#clientes_id').DataTable({
    			"searching": false, // Disables the search box
                "lengthMenu": [ [5, 10, 25, 50], [5, 10, 25, 50] ] // Custom entries per page
            });
    	});
	</script>
        
</head>
<body>
<% 
    ArrayList<Clientes> listaClientes = null;
    if (request.getAttribute("listaU") != null) {
        listaClientes = (ArrayList<Clientes>) request.getAttribute("listaU");
    }
%>

<div class="encabezado"><%
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
    <address class="parteIzq"><br><br><br><br><br><br><br>
        <input class="btnA" name="btnAgregarCliente" type="button" value="Agregar Cliente" onclick="window.location.href='servletAgregarClientes?Param=1'"><br><br><br>
        <input class="btnA" name ="btnListarCliente" type="button" value="Listar Cliente" onclick="window.location.href='ServletListarCliente?Param=1'"><br><br><br>
        <input class="btnA" name ="btnListarCliente" type="button" value="Eliminar Cliente" onclick="window.location.href='servletEliminarCliente?Param=1'"><br><br><br>
    </address>

    <div class="parteDer">
        <h2>Listar Clientes</h2>
        <form action="ServletListarCliente" method="get">
        <br><br>
        <div class="info-columns">
	        <div class="columna">
		            <label class="label-agregar">Filtrar por provincia:</label>
		            <select name="ddlProvincia" style="width: 248px;" required>
		                <option value="">--Seleccione una provincia--</option>
		                <% ArrayList<Provincias> cargarDDLProvincias = (ArrayList<Provincias>) request.getAttribute("cargaDDLProvincias");
		                    if(cargarDDLProvincias != null){
		                        for(Provincias Prov : cargarDDLProvincias){ %>
		                            <option value="<%= Prov.getIdProvincia() %>"><%= Prov.getNombreProvincia() %></option>
		                <% } } %>
		            </select>    <input class="btnFiltrarI" type="submit" name="btnBuscarProvincia" value="Filtrar">
		    </div>
            <div class="columna">
                    <label class="label-agregar">Buscar por DNI:</label>
                    <input type="text" name="txtBuscarDni" style="height: 35px; border: 1px solid #ccc;border-radius: 5px;">  
                    <input class="btnFiltrarI" type="submit" name="btnBuscarDni" value="Buscar" >
            </div>
            <div class="columna">
	             <input class="btnMostrar" type="submit" name="btnMostrarTodo" value="Mostrar Todo" style="width: 180px; height: 60px; margin-left: 70px;">
      		   </div> 
         </div>
        </form><br>
        
        <table id="clientes_id" class="blueTable">
            <thead style="height: 60px; color: white;">
                <tr>
                    <th>DNI</th>
                    <th>CUIL</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Sexo</th>
                    <th>Nacionalidad</th>
                    <th>Fecha Nacimiento</th>
                    <th>Dirección</th>
                    <th>Provincia</th>
                    <th>Localidad</th>
                    <th>Correo</th>
                    <th>Telefono</th>
                    <th>Usuario</th>
                    <th>Contraseña</th>
                    <th></th>
                </tr>
            </thead>
            <tbody style="background-color: white;">
                <% if (listaClientes != null) {
                    int filaId = 0;
                    for (Clientes cliente : listaClientes) { %>
                        <tr>
       			<form action="ServletListarCliente?idFila=<%= filaId %>" method="post">
                            <td data-order="<%= cliente.getDni() %>"><input type="text" name="txtDNI" style="width: 90px;" value="<%= cliente.getDni() %>" readonly></td>
                           <td data-order="<%=cliente.getCuil() %>"><input type="text" name="txtCuil" style="width: 90px;" value="<%= cliente.getCuil() %>"readonly></td>
                            <td data-order="<%= cliente.getNombre()%>"><input type="text" name="txtNombre" value="<%= cliente.getNombre() %>"required></td>
                            <td data-order="<%= cliente.getApellido()%>" ><input type="text" name="txtApellido" value="<%= cliente.getApellido() %>"required></td>
                            <td data-order="<%= cliente.getSexo()  %>"> 
                                <select name="ddlSexo" style="width: 90px;">
                                    <option selected><%= cliente.getSexo() %></option>
                                    <option>Hombre</option>
                                    <option>Mujer</option>
                                    <option>Otro</option>
                                </select>
                            </td>
                            <td data-order="<%= cliente.getNacionalidad() %>"><input type="text" name="txtNacionalidad" value="<%= cliente.getNacionalidad() %>" required></td>
                            <td data-order="<%= cliente.getFechaNacimiento() %>"><input type="date" name="FechaNacimiento" value="<%= cliente.getFechaNacimiento() %>"required></td>
                            <td data-order="<%= cliente.getDireccion() %>"><input type="text" name="txtDireccion" style="width: 140px;" value="<%= cliente.getDireccion() %>"required></td>
                            <td>
                                <select style="width: 115px;" name="ddlProvincia" id="ddlProvincia_<%= filaId %>" onchange="cargarLocalidades(<%= filaId %>)">
                                   <option disabled selected><%= cliente.getProvincia() %></option>
                                    <%
                                        ArrayList<Provincias> ddlProvincias = (ArrayList<Provincias>) request.getAttribute("cargaDDLProvincias");
                                        if (ddlProvincias != null) {
                                            for (Provincias Prov : ddlProvincias) {
                                    %>
                                                <option value="<%= Prov.getIdProvincia() %>"><%= Prov.getNombreProvincia() %></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td data-order="<%=  cliente.getLocalidad()  %>">
                                <select style="width: 130px;" name="ddlLocalidad" id="ddlLocalidad_<%= filaId %>">
                                    <option selected value="<%= cliente.getIDlocalidad()%>"><%= cliente.getLocalidad() %></option>
                                </select>
                            </td>
                            <td data-order="<%= cliente.getCorreo()%>"><input type="text" name="txtCorreo" style="width: 150px;" value="<%= cliente.getCorreo() %>"required></td>
                            <td data-order="<%= cliente.getTelefono() %>"><input type="text" name="txtTelefono" value="<%= cliente.getTelefono() %>"required></td>
                            <td><input type="text" name="txtUsuario" value="<%= cliente.getUsuario().getIdUsuario() %>"readonly></td>
                            <td><input style="width: 80px;" type="password" name="txtContrasenia" value="<%= cliente.getUsuario().getContraseña()%>"required></td>
                            <td><input class="BtnModificar" type="submit" name="btnModificar" value="Modificar"  onclick="confirmarModificacion(event, <%= filaId %>)"></td>
        				</form>
                        </tr>
                    <% filaId++; } } %>
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