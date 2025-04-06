<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidades.TipoCuentas" %>
<%@ page import= "entidades.Usuarios" %>
<%@ page import="entidades.Cuentas" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="Css/Global.css">
<link rel="stylesheet" type="text/css" href="Css/Agregar_Pedir.css">
<link rel="stylesheet" type="text/css" href="Css/SubMenu.css">


<script>
    function actualizarMaximo() {
        // Obtener el select y el input
        const selectCuenta = document.getElementById("selectCuenta");
        const inputCantidad = document.getElementById("inputCantidad");

        // Obtener el saldo de la cuenta seleccionada
        const saldoSeleccionado = selectCuenta.options[selectCuenta.selectedIndex].getAttribute("data-saldo");

        // Actualizar el valor máximo del input
        inputCantidad.max = saldoSeleccionado;
		inputCantidad.min = 1;
        // Opcional: Resetear el valor si supera el nuevo máximo
        if (inputCantidad.value > saldoSeleccionado) {
            inputCantidad.value = saldoSeleccionado;
        }
    }

    // Inicializar el máximo cuando se cargue la página
    document.addEventListener("DOMContentLoaded", actualizarMaximo);
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
	<div class="encabezado"></div>

	<div class="contenido">
	    <address class="parteIzq">
	    	<br><br><br>
	    	<input class="btn" type="button" value="Transferir" onclick="window.location.href='servletTransferir?Param=1'"><br><br>
		    <input class="btn" type="button" value="PagoPrestamos" onclick="window.location.href='servletPagoPrestamos?Param=1'"><br><br>
		    <input class="btn" type="button" value="PedirPrestamos" onclick="window.location.href='servletPedirPrestamos?Param=1'"><br><br>
		    <input class="btn" type="button" value="InfoPersonal" onclick="window.location.href='servletInfoPersonal?Param=1'"><br><br>
	    </address>
    
	    <div class="parteDer"> 
	        <h2>Transferir</h2>
	        <br>
	        <form action="servletTransferir" method="get">		
	        	<div class="form-group">
		            <label class="label-agregar">Cuenta:</label>
		            <select name="DDLcuenta" id="selectCuenta" onchange="actualizarMaximo()">
		            <%
				    ArrayList<Cuentas> listaCuentas = null;
				    if (request.getAttribute("listaU") != null) {
				    	listaCuentas = (ArrayList<Cuentas>)request.getAttribute("listaU");
				    	for(Cuentas cuent : listaCuentas){%>
				    <option value="<%= cuent.getNroCuenta() %>" data-saldo="<%= cuent.getSaldo() %>">
                    <%= cuent.getNroCuenta() %> - <%= cuent.getTipoDeCuenta().getNombreCuenta() %> - $<%= cuent.getSaldo() %> </option>
				    	<%}
				    	}%>
		            
		            </select>
	        	</div> 
	        	<div class="form-group">
		        	<label class="label-agregar">CBU:</label>
		        	<input type="text" name="txtCBU" required>
		        </div>   
		        <div class="form-group">
		        	<label class="label-agregar">Cantidad de Dinero</label>
		        	 <input type="number" name="txtCantidadDinero" id="inputCantidad" min="0" step="1" required>
		            
		        </div>   <br>
		        <div class="button-container">
	        		<button class="BtnAceptar" name="BtnTransferir"value="btnTransferir" type="submit">Transferir</button>
    			</div><br>
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
	    </div>
	</div>

	<footer>
		<input class="btn-volver" type="button" value="Volver al Menú de Clientes" onclick="window.location.href='MenuCliente.jsp'">
	    <p><a href="Login.jsp" class="cerrar-sesion">Cerrar sesión</a></p>
	</footer>
</body>
</html>