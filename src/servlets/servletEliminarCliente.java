package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Clientes;
import entidades.Usuarios;
import negocio.ClienteNeg;
import negocio.CuentasNeg;
import negocioImp.ClienteNegImp;
import negocioImp.CuentasNegImp;


@WebServlet("/servletEliminarCliente")
public class servletEliminarCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteNeg cNeg = new ClienteNegImp();
	CuentasNeg ctNeg= new CuentasNegImp();
       

    public servletEliminarCliente() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        
        String dniBuscar = request.getParameter("txtBuscarDni");
        if (request.getParameter("btnBuscarDni") != null && dniBuscar != null && !dniBuscar.isEmpty()) {
            // Obtener lista filtrada
            ArrayList<Clientes> listaFiltrada = cNeg.listarClientesPorDni(dniBuscar);

            // Pasar lista filtrada al JSP
            request.setAttribute("listaU", listaFiltrada);
            RequestDispatcher dispatcher = request.getRequestDispatcher("EliminarClientes.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Si el botón de "Mostrar Todo" fue presionado
        if (request.getParameter("btnMostrarTodo") != null) {

        	ArrayList<Clientes> lista= cNeg.listarClientes();
			request.setAttribute("listaU", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/EliminarClientes.jsp");   
			
	        rd.forward(request, response);
        }
        
        if (request.getParameter("Param") != null) {
			ArrayList<Clientes> lista= cNeg.listarClientes();
			request.setAttribute("listaU", lista);
			RequestDispatcher rd = request.getRequestDispatcher("EliminarClientes.jsp");   
			
	        rd.forward(request, response);
        }
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dni = request.getParameter("dni").toString() ;
		List<Integer> listaCuentas = ctNeg.obtenerNumerosDeCuentaPorDniNeg(dni);
		
		if(request.getParameter("btnEliminar") != null)
		{
			boolean eliminado=cNeg.borrarCliente(dni);
			
			if (eliminado) {
		        request.setAttribute("mensaje", "El cliente con DNI " + dni + " fue eliminado correctamente.");
		    }else {
		    	request.setAttribute("mensaje", "No se pudo eliminar el cliente con DNI" + dni + ".");
		    }
			
			//borro las cuentas asosicadas a ese cliente
			for (int num : listaCuentas) {
			    ctNeg.borrarCuenta(num);
			}
			
            ArrayList<Clientes> lista= cNeg.listarClientes();
			request.setAttribute("listaU", lista);
			
			
			RequestDispatcher rd = request.getRequestDispatcher("EliminarClientes.jsp");   
	        rd.forward(request, response);
			
		}
		doGet(request, response);
	}

}
