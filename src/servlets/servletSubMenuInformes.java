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


@WebServlet("/servletSubMenuInformes")
public class servletSubMenuInformes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public servletSubMenuInformes() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        if(request.getParameter("Param") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/SubMenuInformes.jsp");
		    dispatcher.forward(request, response);
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
