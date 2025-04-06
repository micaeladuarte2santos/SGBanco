package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import entidades.Clientes;
import entidades.Localidades;
import entidades.Provincias;
import entidades.Usuarios;
import negocioImp.ClienteNegImp;
import negocioImp.LocalidadNegImp;
import negocioImp.ProvinciaNegImp;
import negocioImp.UsuarioNegImp;


@WebServlet("/servletAgregarClientes")
public class servletAgregarClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteNegImp negClientes = new ClienteNegImp();
	ProvinciaNegImp negProvincias = new ProvinciaNegImp();
	LocalidadNegImp negLocalidades = new LocalidadNegImp();
	UsuarioNegImp negUsuarios = new UsuarioNegImp();

    public servletAgregarClientes() {
        super();

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    
    	if (request.getParameter("Param") != null) {
    	    ArrayList<Provincias> ddlProvincia = negProvincias.cargarDDlProvincia();
    	    request.setAttribute("cargaDDLProvincias", ddlProvincia);
    	    RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
    	    dispatcher.forward(request, response);
    	    return;
    	}
        String idProvincia = request.getParameter("idProvincia");

        if (idProvincia != null && !idProvincia.isEmpty()) {
            ArrayList<Localidades> ddlLocalidad = negLocalidades.cargarDDLLocalidadesPorProvincia(idProvincia);
            response.setContentType("text/html;charset=ISO-8859-1");

            PrintWriter out = response.getWriter();
            out.println("<option value=''>Seleccione una localidad</option>");
            for (Localidades localidad : ddlLocalidad) {
                out.println("<option value='" + localidad.getIdLocalidad() + "'>" + localidad.getNombreLocalidad() + "</option>");
            }
            out.close();
        } 


        if(request.getParameter("btnAgregar") != null) {
            String dni = request.getParameter("txtDNI");
            
            boolean existeCliente = negClientes.validarCliente(dni);
            
            String nombreUsuario = request.getParameter("txtUsuario");
            
	        Usuarios usuario = negUsuarios.validarUsuario(nombreUsuario);
	        boolean agregado = false;
	        boolean existeUsuario = true;
	        
	        String cuil = request.getParameter("txtCuil");
	        String nombre = request.getParameter("txtNombre");
	        String apellido = request.getParameter("txtApellido");
	        String sexo = request.getParameter("ddlSexo");
	        String nacionalidad = request.getParameter("txtNacionalidad");
	        String fechaNacimiento = request.getParameter("FechaNacimiento");
	        String direccion = request.getParameter("txtDireccion");
	        String localidad = request.getParameter("ddlLocalidad");
	        String correo = request.getParameter("txtCorreo");
	        String telefono = request.getParameter("txtTelefono");
	        String contraseña = request.getParameter("txtContrasenia");
	        String contraseña2 = request.getParameter("txtConfirmarContrasenia");
	        
	        if (dni == null || dni.isEmpty() ||
	                cuil == null || cuil.isEmpty() ||
	                nombre == null || nombre.isEmpty() ||
	                apellido == null || apellido.isEmpty() ||
	                sexo == null || sexo.isEmpty() ||
	                nacionalidad == null || nacionalidad.isEmpty() ||
	                fechaNacimiento == null || fechaNacimiento.isEmpty() ||
	                direccion == null || direccion.isEmpty() ||
	                localidad == null || localidad.isEmpty() ||
	                correo == null || correo.isEmpty() ||
	                telefono == null || telefono.isEmpty() ||
	                nombreUsuario == null || nombreUsuario.isEmpty() ||
	                contraseña == null || contraseña.isEmpty() ||
	                contraseña2 == null || contraseña2.isEmpty()) {
	                
	                // Establecer mensaje de error
	                request.setAttribute("mensaje", "Favor, de completar todos los campos.");
	                
	                // Recargar la página de AgregarCliente
	                ArrayList<Provincias> ddlProvincia = negProvincias.cargarDDlProvincia();
	                request.setAttribute("cargaDDLProvincias", ddlProvincia);
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
	                dispatcher.forward(request, response);
	                return; // Detener la ejecución posterior
	            }
	        
    		if (negClientes.verificarCorreo(correo)) {
	               
                request.setAttribute("mensaje", "El correo "+ correo + " esta en uso");
         
                ArrayList<Provincias> ddlProvincia = negProvincias.cargarDDlProvincia();
                request.setAttribute("cargaDDLProvincias", ddlProvincia);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
                dispatcher.forward(request, response);
                return; 
            }
    		if (negClientes.verificarCuit(cuil)) {
	               
                request.setAttribute("mensaje", "El cuil "+ cuil + " esta en uso");
         
                ArrayList<Provincias> ddlProvincia = negProvincias.cargarDDlProvincia();
                request.setAttribute("cargaDDLProvincias", ddlProvincia);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
                dispatcher.forward(request, response);
                return; 
            }
  
	        if (existeCliente) {
	            request.setAttribute("mensaje", "El cliente con DNI " + dni + " ya existe.");
	        } else if (usuario != null) {
	            existeUsuario = false;
	            if(contraseña != null && contraseña.equals(contraseña2)) {
	            	int idUsuario = CargarUsuario(nombreUsuario, contraseña);
		            agregado = CargarCliente(dni, cuil, nombre, apellido, sexo, nacionalidad, fechaNacimiento, direccion, localidad, correo, telefono, contraseña, idUsuario);
		            
		            if (agregado) {
		                request.setAttribute("mensaje", "Cliente agregado con éxito.");
		            } else {
		                request.setAttribute("mensaje", "Error al agregar cliente.");
		            }
	            }
	            else {
	            	request.setAttribute("mensaje", "Las contaseñas no coinciden, verifiquelas de nuevo.");
	            }
	        } else {
	            request.setAttribute("mensaje", "El nombre de usuario ya está en uso.");
	        }
	        
	        ArrayList<Provincias> ddlProvincia = negProvincias.cargarDDlProvincia();
    	    request.setAttribute("cargaDDLProvincias", ddlProvincia);
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
            dispatcher.forward(request, response);
          
            response.getWriter().append("Served at: ").append(request.getContextPath());
        }
    }



    public boolean CargarCliente(String dni,String cuil,String nombre,String apellido,String sexo,String nacionalidad,String fechaNacimiento,String direccion,String localidad,String correo,String telefono,String contraseña, int idUsuario) {
    	Clientes Clien = new Clientes();
    	Clien.setDni(dni);
    	Clien.setCuil(cuil);
    	Clien.setNombre(nombre);
    	Clien.setApellido(apellido);
    	Clien.setSexo(sexo);
    	Clien.setNacionalidad(nacionalidad);
    	LocalDate aux = LocalDate.parse(fechaNacimiento);
    	Clien.setFechaNacimiento(aux);
    	Clien.setDireccion(direccion);
    	Clien.setLocalidad(localidad);
    	Clien.setCorreo(correo);
    	Clien.setTelefono(telefono);
    	Usuarios usu = new Usuarios();
    	
    	usu.setContraseña(contraseña);
    	usu.setIdUsuario(idUsuario);

    	Clien.setUsuario(usu);
    	return negClientes.agregarCliente(Clien);
    }
    
    public int CargarUsuario(String nombreUsuario,String contraseña) {
    	Usuarios Usua = new Usuarios();
    	Usua.setNombreUsuario(nombreUsuario);
    	Usua.setContraseña(contraseña);
    	return negUsuarios.agregarUsuario(Usua);
    }
    
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("btnAgregarCliente") != null) {
			 ArrayList<Provincias> ddlProvincia = new  ArrayList<>();
			 ddlProvincia = negProvincias.cargarDDlProvincia();
			 Usuarios nombre = new Usuarios(); 
			 
			 //request.setAttribute("Usuario",ddlProvincia);
			 request.setAttribute("cargaDDLProvincias",ddlProvincia);	 

			 RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
			 dispatcher.forward(request, response);
			 return;
		}
		if(request.getParameter("btnListarCliente") != null) {
			 ArrayList<Provincias> ddlProvincia = new  ArrayList<>();
			 ddlProvincia = negProvincias.cargarDDlProvincia();
			 ArrayList<Clientes> lista= negClientes.listarClientes();
			 request.setAttribute("listaU", lista);
			 request.setAttribute("cargaDDLProvincias",ddlProvincia);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCliente.jsp");
			 dispatcher.forward(request, response);
			 return;
		}

		doGet(request, response);
	}
	
	/*                   
	 *     <%
        boolean existeCliente = true;
        if(request.getAttribute("existeCliente") != null){
            existeCliente = (boolean)request.getAttribute("existeCliente");    
        }
        
        boolean existeUsuario = true;
        if(request.getAttribute("existeUsuario") != null){
            existeUsuario = (boolean)request.getAttribute("existeUsuario");    
        }
        
        boolean agregado = false;
        if(request.getAttribute("agregar") != null){
            agregado = (boolean)request.getAttribute("agregar");    
        }
    %>
	 *  
	 *  <%
                        if (existeUsuario) {
                            out.println("<p>El usuario existe, puedes agregar el cliente.</p>");
                        } else {
                            out.println("<p>El usuario no existe. Debes crear un usuario primero.</p>");
                        }
                        
                        if (agregado) {
                            out.println("<p>Cliente agregado exitosamente.</p>");
                        } else if (!existeCliente) {
                            out.println("<p>El cliente ya existe en el sistema.</p>");
                        }
                    %>*/

}