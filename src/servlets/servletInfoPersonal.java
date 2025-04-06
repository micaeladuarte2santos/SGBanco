package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Usuarios;
import entidades.Clientes;
import negocio.ClienteNeg;
import negocioImp.ClienteNegImp;


@WebServlet("/servletInfoPersonal")
public class servletInfoPersonal extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteNeg clienteNeg = new ClienteNegImp();

    public servletInfoPersonal() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect("Login.jsp"); // Redirigir al login si no hay sesión activa
            return;
        }
        
        if (request.getParameter("Param") != null) {
        	//codigo
        	String nombreUsu= usuario.getNombreUsuario();
            Clientes cli = clienteNeg.obtenerClienteConCuentasPorIdUsuario(nombreUsu);
            int cantidad = clienteNeg.contarCuentasPorNombreU(nombreUsu);
            request.setAttribute("cliente", cli);  
            request.setAttribute("cantidadCuentas", cantidad);
            
			RequestDispatcher rd = request.getRequestDispatcher("/InfoPersonal.jsp");  		
	        rd.forward(request, response);
        }        
	response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
