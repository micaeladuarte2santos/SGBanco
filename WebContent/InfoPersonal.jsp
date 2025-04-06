<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="entidades.Usuarios"%>
<%@ page import="entidades.Clientes"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
    <jsp:include page="Css/Info.css"></jsp:include>
</style>
    <script>
        // Función para alternar la visibilidad de la información
        function toggleInfo() {
            var infoDiv = document.getElementById('infoPersonal');
            var button = document.getElementById('toggleButton');
            
            // Alternar la visibilidad de la información
            if (infoDiv.style.display === 'none') {
                infoDiv.style.display = 'block';
                button.value = 'Ocultar'; // Cambiar texto a "Ocultar"
                button.classList.remove('mostrar'); // Eliminar clase mostrar
                button.classList.add('ocultar'); // Añadir clase ocultar
            } else {
                infoDiv.style.display = 'none';
                button.value = 'Mostrar'; // Cambiar texto a "Mostrar"
                button.classList.remove('ocultar'); // Eliminar clase ocultar
                button.classList.add('mostrar'); // Añadir clase mostrar
            }
        }
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
    <address class="parteIzq"><br><br><br>
      <div class="btn-container"><input class="btn" type="button" value="Transferir" onclick="window.location.href='servletTransferir?Param=1'"><br><br></div>
      <div class="btn-container"><input class="btn" type="button" value="PagoPrestamos" onclick="window.location.href='servletPagoPrestamos?Param=1'"><br><br></div>
	  <div class="btn-container"><input class="btn" type="button" value="PedirPrestamos" onclick="window.location.href='servletPedirPrestamos?Param=1'"><br><br></div>
	  <div class="btn-container"><input class="btn" type="button" value="InfoPersonal" onclick="window.location.href='servletInfoPersonal?Param=1'"><br><br></div>
    </address>
    
    <div class="parteDer"> 
        <h2>Información Personal</h2>    
        <br>
		<div id="infoPersonal" style="display: none;">
	        <div class="info-columns">
		        <div class="columna">
		            <div class="info-item"><p><strong>DNI:</strong></p>  <label>${cliente.dni}</label></div>
		            <div class="info-item"><p><strong>CUIL:</strong></p> <label>${cliente.cuil}</label></div>
		            <div class="info-item"><p><strong>Cant.Cuentas:</strong></p> <label>${cantidadCuentas}</label></div>
		            <br>
		            <div class="info-item"><p><strong>Nombre:</strong></p> <label>${cliente.nombre}</label></div>
		            <div class="info-item"><p><strong>Apellido:</strong></p> <label>${cliente.apellido}</label></div>
		            <div class="info-item"><p><strong>Género:</strong></p> <label>${cliente.sexo}</label></div>
		        </div>
		        <div class="columna">
		            <div class="info-item"><p><strong>Nacionalidad:</strong></p> <label>${cliente.nacionalidad}</label></div>
		            <div class="info-item"><p><strong>Nacimiento:</strong></p> <label>${cliente.fechaNacimiento}</label></div>
		            <div class="info-item"><p><strong>Localidad:</strong></p> <label>${cliente.localidad}</label></div>
		            <br>
		            <div class="info-item"><p><strong>Correo:</strong></p> <label>${cliente.correo}</label></div>
		            <div class="info-item"><p><strong>Teléfono:</strong></p> <label>${cliente.telefono}</label></div>
		           <div class="info-item"><p><strong>Dirección:</strong></p> <label>${cliente.direccion}</label></div>
		        </div>
	    	</div>
	    	<div class="info-columns">
		        <div class="columna">
		        	<br>
		            <div class="info-item"><p><strong>Usuario:</strong></p> <label>${usuario.getNombreUsuario()}<label></div>
		        </div>
		        <div class="columna">
		        	<br>
		            <div class="info-item"><p><strong>Contraseña:</strong></p><label>${cliente.getUsuario().getContraseña()}</label></div>
		        </div>
	    	</div>
	    	<br>
	    </div><br>
	    <br><input class="btn mostrar" type="button" id="toggleButton" value="Mostrar" onclick="toggleInfo()"><br><br>
	  </div>
</div>

<footer>
	<input class="btn-volver" type="button" value="Volver al Menú de Clientes" onclick="window.location.href='MenuCliente.jsp'">
	<p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
</footer>
</body>
</html>