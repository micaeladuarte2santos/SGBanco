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

import entidades.Movimientos;
import entidades.Usuarios;
import negocio.MovimientosNeg;
import negocioImp.MovimientosNegImp;
import entidades.Clientes;
import entidades.Cuentas;
import negocio.CuentasNeg;
import negocioImp.CuentasNegImp;



@WebServlet("/servletHistorialCuentas")
public class servletHistorialCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MovimientosNeg mNeg = new MovimientosNegImp();
	CuentasNeg cNeg= new CuentasNegImp();
	

    public servletHistorialCuentas() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        
        //obtendo el dni del cliente que inicio sesion
        String dniCliente = (String) session.getAttribute("dniCliente");
        
        if(request.getParameter("Param") != null) {
            	
        	ArrayList<Movimientos> lista= mNeg.listarMovimientos(dniCliente);
        	request.setAttribute("listaU", lista); 
        	
        	//cargo el ddl del filtrar
        	List<Integer> ddlCuentas = cNeg.obtenerNumerosDeCuentaPorDniNeg(dniCliente);
        	request.setAttribute("cargaDDL", ddlCuentas);
        	
			RequestDispatcher dispatcher = request.getRequestDispatcher("HistorialCuentas.jsp");
		    dispatcher.forward(request, response);
		}
        
        if (request.getParameter("btnMostrarTodo") != null) {

        	ArrayList<Movimientos> lista= mNeg.listarMovimientos(dniCliente);
        	request.setAttribute("listaU", lista); 
        	
        	//cargo el ddl del filtrar
        	List<Integer> ddlCuentas = cNeg.obtenerNumerosDeCuentaPorDniNeg(dniCliente);
        	request.setAttribute("cargaDDL", ddlCuentas);

			RequestDispatcher dispatcher = request.getRequestDispatcher("HistorialCuentas.jsp");
		    dispatcher.forward(request, response);   
        }
        
        //para filtrar por nro de cuenta
        int nroCuenta = Integer.parseInt(request.getParameter("numeroCuenta"));
        if (request.getParameter("btnFiltrar") != null) {
        	// Obtener lista filtrada
            List<Movimientos> listaFiltrada = mNeg.obtenerMovimientosPorCuenta(nroCuenta);
            
            request.setAttribute("listaU", listaFiltrada);
            RequestDispatcher dispatcher = request.getRequestDispatcher("HistorialCuentas.jsp");
            
            //cargo el ddl del filtrar
        	List<Integer> ddlCuentas = cNeg.obtenerNumerosDeCuentaPorDniNeg(dniCliente);
        	request.setAttribute("cargaDDL", ddlCuentas);
            dispatcher.forward(request, response);
            return;
        }
        
        
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
