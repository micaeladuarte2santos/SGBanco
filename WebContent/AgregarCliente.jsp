<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidades.Provincias"%>
<%@ page import="entidades.Localidades"%>
<%@ page import="entidades.Usuarios"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agregar Cliente</title>
<link rel="stylesheet" type="text/css" href="Css/Global.css">
<link rel="stylesheet" type="text/css" href="Css/Agregar_Pedir.css">
<link rel="stylesheet" type="text/css" href="Css/SubMenu.css">
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
    
    	<script>
        function cargarLocalidades() {
            const provinciaId = document.getElementById("ddlProvincia").value;
            const xhr = new XMLHttpRequest();
            xhr.open("GET", "servletAgregarClientes?idProvincia=" + provinciaId, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    document.getElementById("ddlLocalidad").innerHTML = xhr.responseText;
                }
            };
            xhr.send();
        }
    </script>
   
	

    <div class="contenido">
        <address class="parteIzq"><br><br><br><br><br><br><br><br><br>
	        <input class="btn" name="btnAgregarCliente" type="button" value="Agregar Cliente" onclick="window.location.href='servletAgregarClientes?Param=1'"><br><br><br>
	        <input class="btn" name ="btnListarCliente" type="button" value="Listar Cliente" onclick="window.location.href='ServletListarCliente?Param=1'"><br><br><br>
	        <input class="btn" name ="btnListarCliente" type="button" value="Eliminar Cliente" onclick="window.location.href='servletEliminarCliente?Param=1'"><br><br><br>
    	</address>

        <div class="parteDer"> 
            <h2>Agregar Cliente</h2>
            
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
            <form action="servletAgregarClientes" method="get">		    
                <div class="form-group">
                    <label class="label-agregar">DNI:</label>
                    <input type="text" name ="txtDNI">
                </div>
                <div class="form-group">
                    <label class="label-agregar">CUIL:</label>
                    <input type="text" name ="txtCuil">
                </div> <br>
                <div class="form-group">
                    <label class="label-agregar">Nombre:</label>
                    <input type="text" name ="txtNombre">
                </div>
                <div class="form-group">
                    <label class="label-agregar">Apellido:</label>
                    <input type="text" name ="txtApellido">
                </div>
                <div class="form-group">
                    <label class="label-agregar">Género:</label>
                    <select name ="ddlSexo">
                        <option>Seleccione un Género</option>
                        <option>Hombre</option>
                        <option>Mujer</option>
                        <option>Otro</option>
                    </select>
                </div>
                <div class="form-group">
                    <label class="label-prestamo">Fecha nacimiento:</label>
                    <input type="date" id="fecha" name="FechaNacimiento">
                </div> 
                <div class="form-group">
                    <label class="label-agregar">Nacionalidad:</label>
                    <input type="text" name ="txtNacionalidad">
                </div><br>   
                <div class="form-group">
                    <label class="label-agregar">Correo:</label>
                    <input type="text" name ="txtCorreo">
                </div>
                <div class="form-group">
                    <label class="label-agregar">Teléfono:</label>
                    <input type="text" name="txtTelefono">
                </div>
                <div class="form-group">
                    <label class="label-agregar">Dirección:</label>
                    <input type="text" name ="txtDireccion">
                </div>
                <div class="form-group">
                    <label class="label-agregar">Provincia:</label>
                    <select name ="ddlProvincia" id="ddlProvincia" onchange="cargarLocalidades()">
                        <option>Seleccione una provincia</option>
                        <%
                            ArrayList<Provincias> ddlProvincias = (ArrayList<Provincias>) request.getAttribute("cargaDDLProvincias");
                            if(ddlProvincias != null){
                                for(Provincias Prov : ddlProvincias){
                        %>
                                    <option value="<%= Prov.getIdProvincia() %>"><%= Prov.getNombreProvincia() %></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label class="label-agregar">Localidad:</label>
                    <select name ="ddlLocalidad" id="ddlLocalidad">
                        <option>Seleccione una localidad</option>
                    </select>
                </div><br>
                <div class="form-group">
                    <label class="label-agregar">Usuario:</label>
                    <input type="text" name ="txtUsuario">
                </div>
                <div class="form-group">
                    <label class="label-agregar">Contraseña:</label>
                    <input type="password" name ="txtContrasenia">
                </div>
                <div class="form-group">
                    <label class="label-agregar">Confirmar Contraseña:</label>
                    <input type="password" name ="txtConfirmarContrasenia">
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
