package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Clientes;
import entidades.Cuentas;
import entidades.Usuarios;
import negocio.CuentasNeg;
import negocioImp.CuentasNegImp;
import negocioImp.UsuarioNegImp;


@WebServlet("/servletEliminarCuenta")
public class servletEliminarCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CuentasNeg cNeg = new CuentasNegImp();
	UsuarioNegImp negUsuarios = new UsuarioNegImp();
       
    public servletEliminarCuenta() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        
        String dniBuscar = request.getParameter("txtBuscarDni");
        if (request.getParameter("btnBuscarDni") != null && dniBuscar != null && !dniBuscar.isEmpty()) {
            // Obtener lista filtrada
            ArrayList<Cuentas> listaFiltrada = cNeg.listarCuentasPorDni(dniBuscar);

            // Pasar lista filtrada al JSP
            request.setAttribute("listaU", listaFiltrada);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/EliminarCuenta.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Si el botón de "Mostrar Todo" fue presionado
        if (request.getParameter("btnMostrarTodo") != null) {

        	ArrayList<Cuentas> lista= cNeg.listarCuentas();
			request.setAttribute("listaU", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/EliminarCuenta.jsp");   
			
	        rd.forward(request, response);
        }
        
        if (request.getParameter("Param") != null) {
			ArrayList<Cuentas> lista = cNeg.listarCuentas();
			request.setAttribute("listaU", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/EliminarCuenta.jsp");
			
		     rd.forward(request, response);
        }
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("btnEliminar") != null)
		{
			int nroCuenta = Integer.parseInt(request.getParameter("nroCuenta"));
			
			//elimina la cuenta solo si el saldo es =0
			if(cNeg.obtenerSaldo(nroCuenta)==0) {
				
				boolean eliminado = cNeg.borrarCuenta(nroCuenta);
				
				if(eliminado) {
					request.setAttribute("mensaje", "La cuenta con Número de cuenta " + nroCuenta + " fue eliminada correctamente");
				}else {
					request.setAttribute("mensaje", "No se pudo eliminar la cuenta con número de cuenta " + nroCuenta);
				}
			}else {
				request.setAttribute("mensaje", "No se pudo eliminar la cuenta con número " + nroCuenta +" ya que posee saldo positivo");
			}
			
			
			
			ArrayList<Cuentas> lista = cNeg.listarCuentas();
			request.setAttribute("listaU", lista);
			
			RequestDispatcher rd = request.getRequestDispatcher("/EliminarCuenta.jsp");
			rd.forward(request, response);
			
		}
		doGet(request, response);
	}

}
