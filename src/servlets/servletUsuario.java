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
import negocio.UsuarioNeg;
import negocioImp.UsuarioNegImp;


@WebServlet("/servletUsuario")
public class servletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsuarioNeg negUsuario = new UsuarioNegImp();

    public servletUsuario() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("btnIngresar") != null) {
	        String nombreUsuario = request.getParameter("usuario");
	        String contraseña = request.getParameter("contraseña");

	        Usuarios usuario = negUsuario.validarUsuario(nombreUsuario);

	        if (usuario != null && usuario.getContraseña() != null && usuario.getContraseña().equals(contraseña)) {
	        	HttpSession session = request.getSession();
	            session.setAttribute("usuario", usuario);
	            
	            String dniCliente = negUsuario.obtenerDniPorNombreUsuario(nombreUsuario);
	            session.setAttribute("dniCliente", dniCliente);

	            if ("administrador".equalsIgnoreCase(usuario.getTipoUsuario())) {
	                response.sendRedirect("MenuAdministrador.jsp");
	                
	            } else if ("cliente".equalsIgnoreCase(usuario.getTipoUsuario())) {
	                response.sendRedirect("MenuCliente.jsp");
	            }
	        }else {
	        	request.setAttribute("mensaje", "Contraseña incorrecta, ingrese nuevamente.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response); // Redirige a Login.jsp con el mensaje
	        }
		
		doGet(request, response);
		}
	}
}

