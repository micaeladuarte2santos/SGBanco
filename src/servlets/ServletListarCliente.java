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
import javax.servlet.http.HttpSession;

import entidades.Clientes;
import entidades.Usuarios;
import entidades.Localidades;
import entidades.Provincias;
import negocioImp.ClienteNegImp;
import negocioImp.LocalidadNegImp;
import negocioImp.ProvinciaNegImp;
import negocioImp.UsuarioNegImp;

@WebServlet("/ServletListarCliente")
public class ServletListarCliente extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ClienteNegImp negClientes = new ClienteNegImp();
	UsuarioNegImp negUsuarios = new UsuarioNegImp();
    ProvinciaNegImp negProvincias = new ProvinciaNegImp();
    LocalidadNegImp negLocalidades = new LocalidadNegImp();

    public ServletListarCliente() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("btnModificar") != null) {
            Clientes cliente = new Clientes();
            Usuarios usuario = new Usuarios();
           
                cliente.setDni(request.getParameter("txtDNI"));
                cliente.setCuil(request.getParameter("txtCuil"));
                cliente.setNombre(request.getParameter("txtNombre"));
                cliente.setApellido(request.getParameter("txtApellido"));
                cliente.setSexo(request.getParameter("ddlSexo"));
                cliente.setNacionalidad(request.getParameter("txtNacionalidad"));

                
                String fechaNacimiento = request.getParameter("FechaNacimiento");
                cliente.setFechaNacimiento(LocalDate.parse(fechaNacimiento));

                cliente.setDireccion(request.getParameter("txtDireccion"));
                cliente.setLocalidad(request.getParameter("ddlLocalidad"));
                cliente.setCorreo(request.getParameter("txtCorreo"));
                cliente.setTelefono(request.getParameter("txtTelefono"));
                
                String id= request.getParameter("txtUsuario");
                int idUsuario = Integer.parseInt(id);
                usuario.setIdUsuario(idUsuario);
                usuario.setContraseña(request.getParameter("txtContrasenia"));
                cliente.setUsuario(usuario);
                negUsuarios.modificarUsuario(usuario);
                negClientes.modificar(cliente);
   
                ArrayList<Provincias> ddlProvincia = negProvincias.cargarDDlProvincia();
                ArrayList<Clientes> listaClientes = negClientes.listarClientes();

                request.setAttribute("listaU", listaClientes);
                request.setAttribute("cargaDDLProvincias", ddlProvincia);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCliente.jsp");
                dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false); 
		Usuarios usuario = (Usuarios) session.getAttribute("usuario");
    	if (request.getParameter("Param") != null) {
    		
    		ArrayList<Provincias> ddlProvincia = negProvincias.cargarDDlProvincia();
    		ArrayList<Clientes> listaClientes = negClientes.listarClientes();
    		
            request.setAttribute("listaU", listaClientes);
            request.setAttribute("cargaDDLProvincias", ddlProvincia);
            
			RequestDispatcher rd = request.getRequestDispatcher("/ListarCliente.jsp");   
			
	        rd.forward(request, response);
        }
    	
        String idProvincia = request.getParameter("idProvincia");

        if (idProvincia != null && !idProvincia.isEmpty()) {
            ArrayList<Localidades> ddlLocalidad = negLocalidades.cargarDDLLocalidadesPorProvincia(idProvincia);
            response.setContentType("text/html;charset=ISO-8859-1");

            PrintWriter out = response.getWriter();
           
            for (Localidades localidad : ddlLocalidad) {
                out.println("<option value='" + localidad.getIdLocalidad() + "'>" + localidad.getNombreLocalidad() + "</option>");
            }
            out.close();
        } 
        response.getWriter().append("Served at: ").append(request.getContextPath());
        
    	if(request.getParameter("btnMostrarTodo") != null) {
    		ArrayList<Provincias> ddlProvincia = negProvincias.cargarDDlProvincia();
    		ArrayList<Clientes> listaClientes = negClientes.listarClientes();
    		
            request.setAttribute("listaU", listaClientes);
            request.setAttribute("cargaDDLProvincias", ddlProvincia);
            
			RequestDispatcher rd = request.getRequestDispatcher("/ListarCliente.jsp");   
			
	        rd.forward(request, response);
			 return;
    	}
        
    	if(request.getParameter("btnBuscarDni") != null) {
			 ArrayList<Provincias> ddlProvincia = new  ArrayList<>();
			 ddlProvincia = negProvincias.cargarDDlProvincia();
			 String dni = request.getParameter("txtBuscarDni");
			 ArrayList<Clientes> lista= negClientes.listarClientesPorDni(dni);
			 request.setAttribute("listaU", lista);
			 request.setAttribute("cargaDDLProvincias",ddlProvincia);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCliente.jsp");
			 dispatcher.forward(request, response);
			 return;
    	}
    	if(request.getParameter("btnBuscarProvincia") != null) {
			 ArrayList<Provincias> ddlProvincia = new  ArrayList<>();
			 ddlProvincia = negProvincias.cargarDDlProvincia();
			 ArrayList<Clientes> lista= negClientes.listarClientesPorProv(request.getParameter("ddlProvincia"));
			 request.setAttribute("listaU", lista);
			 request.setAttribute("cargaDDLProvincias",ddlProvincia);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCliente.jsp");
			 dispatcher.forward(request, response);
			 return;
    	}
    }
}
