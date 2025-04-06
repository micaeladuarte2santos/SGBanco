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

import entidades.Cuentas;
import entidades.TipoCuentas;
import entidades.Usuarios;
import negocioImp.CuentasNegImp;
import negocioImp.TipoCuentaNegImp;
import negocioImp.UsuarioNegImp;

@WebServlet("/ServletListarCuenta")
public class ServletListarCuenta extends HttpServlet {
    private static final long serialVersionUID = 1L;
    CuentasNegImp negCuentas= new CuentasNegImp();
	UsuarioNegImp negUsuarios = new UsuarioNegImp();
	TipoCuentaNegImp negTipoCuentas = new TipoCuentaNegImp();

    public ServletListarCuenta() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("btnModificar") != null) {
        	Cuentas cuenta = new Cuentas();
           
            cuenta.setNroCuenta(Integer.parseInt(request.getParameter("txtNroCuenta")));
            cuenta.setDni(request.getParameter("txtDniCliente"));
            
            String fechaCreacion = request.getParameter("txtFecha");
            cuenta.setLocalDate(LocalDate.parse(fechaCreacion));
            
            int tipoCuentaId = Integer.parseInt(request.getParameter("ddlTipoCuenta"));
            TipoCuentas tipoCuenta = new TipoCuentas();
            tipoCuenta.setIdTipoCuenta(tipoCuentaId);
            cuenta.setTipoDeCuenta(tipoCuenta);
            
            cuenta.setCbu(request.getParameter("txtCBU"));
            cuenta.setSaldo(Float.parseFloat(request.getParameter("txtSaldo")));
            
            negCuentas.modificar(cuenta);
    		ArrayList<TipoCuentas> ddlTipoCuentas = negTipoCuentas.cargarDDlTipoCuenta();
    		ArrayList<Cuentas> listaCuentas = negCuentas.listarCuentas();
    		
            request.setAttribute("listaU", listaCuentas);
            request.setAttribute("cargarDDlTipoCuenta", ddlTipoCuentas);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); 
		Usuarios usuario = (Usuarios) session.getAttribute("usuario");
    	if (request.getParameter("Param") != null) {
    		
    		ArrayList<TipoCuentas> ddlTipoCuentas = negTipoCuentas.cargarDDlTipoCuenta();
    		ArrayList<Cuentas> listaCuentas = negCuentas.listarCuentas();
    		
            request.setAttribute("listaU", listaCuentas);
            request.setAttribute("cargarDDlTipoCuenta", ddlTipoCuentas);
            
			RequestDispatcher rd = request.getRequestDispatcher("/ListarCuentas.jsp");   
			
	        rd.forward(request, response);
        }
    	
    	if(request.getParameter("btnBuscarPorDni") != null) {
    		 ArrayList<TipoCuentas> ddlTipoCuenta = new  ArrayList<>();
    		 ddlTipoCuenta = negTipoCuentas.cargarDDlTipoCuenta();
			 String dni = request.getParameter("txtBuscarPorDniCliente");
			 ArrayList<Cuentas> lista= negCuentas.listarCuentasPorDni(dni);
			 request.setAttribute("listaU", lista);
			 request.setAttribute("cargarDDlTipoCuenta",ddlTipoCuenta);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
			 dispatcher.forward(request, response);
			 return;
    	}
    	if(request.getParameter("btnFlitrarTipoCuenta") != null) {
			if(!request.getParameter("ddltipoCuenta").equals("--Seleccione Tipo de Cuenta--")) {
    		ArrayList<TipoCuentas> ddlTipoCuenta = new  ArrayList<>();
			 ddlTipoCuenta = negTipoCuentas.cargarDDlTipoCuenta();
			 int idTipo =  Integer.parseInt(request.getParameter("ddltipoCuenta"));
			 ArrayList<Cuentas> lista= negCuentas.listarCuentasPorTipo(idTipo);
			 request.setAttribute("listaU", lista);
			 request.setAttribute("cargarDDlTipoCuenta",ddlTipoCuenta);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
			 dispatcher.forward(request, response);
			}else {
	    		ArrayList<TipoCuentas> ddlTipoCuentas = negTipoCuentas.cargarDDlTipoCuenta();
	    		ArrayList<Cuentas> lista = negCuentas.listarCuentas();
	    		
	            request.setAttribute("listaU", lista);
	            request.setAttribute("cargarDDlTipoCuenta", ddlTipoCuentas);
	            
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
				dispatcher.forward(request, response);
			}
    	}
    	if(request.getParameter("btnMostrarTodo")!=null) {
    		ArrayList<TipoCuentas> ddlTipoCuentas = negTipoCuentas.cargarDDlTipoCuenta();
    		ArrayList<Cuentas> lista = negCuentas.listarCuentas();
    		
            request.setAttribute("listaU", lista);
            request.setAttribute("cargarDDlTipoCuenta", ddlTipoCuentas);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
			dispatcher.forward(request, response);
    	}
    }
    
}
