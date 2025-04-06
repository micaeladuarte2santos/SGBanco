package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Usuarios;
import entidades.Cuotas;
import negocio.CuotasNeg;
import negocioImp.CoutasNegImp;
import entidades.Cuentas;
import negocio.CuentasNeg;
import negocioImp.CuentasNegImp;

/**
 * Servlet implementation class servletPagoPrestamos
 */
@WebServlet("/servletPagoPrestamos")
public class servletPagoPrestamos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String dniCliente;
	CuotasNeg cNeg = new CoutasNegImp();
	CuentasNeg cuNeg= new CuentasNegImp();   

	
    public servletPagoPrestamos() {
        super();

    }

    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        
        dniCliente = (String) session.getAttribute("dniCliente");
        
        if (request.getParameter("Param") != null) {
        	//codigo
        	
        	List<Integer> ddlCuentas = cuNeg.obtenerNumerosDeCuentaPorDniNeg(dniCliente);
        	request.setAttribute("cargaDDL", ddlCuentas);
        	
        	ArrayList<Cuotas> lista = cNeg.listarCuotasPorDni(dniCliente);
        	request.setAttribute("listaU", lista);
        	
			RequestDispatcher rd = request.getRequestDispatcher("PagarCuotaPrestamo.jsp");   
	        rd.forward(request, response);
        }
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		if(request.getParameter("btnMostrarTodo") != null) {
			
			List<Integer> ddlCuentas = cuNeg.obtenerNumerosDeCuentaPorDniNeg(dniCliente);
        	request.setAttribute("cargaDDL", ddlCuentas);
        	
			ArrayList<Cuotas> lista = cNeg.listarCuotasPorDni(dniCliente);
            request.setAttribute("listaU", lista);
            
			RequestDispatcher rd = request.getRequestDispatcher("/PagarCuotaPrestamo.jsp");   
			
	        rd.forward(request, response);
			 return;
    	}
        
    	if(request.getParameter("btnBuscarPrestamo") != null) {
			 String idprestamo = request.getParameter("txtBuscarPrestamo");
			 if(idprestamo != null) {
				 int idPrestamo = Integer.parseInt(idprestamo);
				 
				 List<Integer> ddlCuentas = cuNeg.obtenerNumerosDeCuentaPorDniNeg(dniCliente);
		         request.setAttribute("cargaDDL", ddlCuentas);
		         
				 ArrayList<Cuotas> lista= cNeg.listarCuotasPorPrestamo(idPrestamo,dniCliente);
				 request.setAttribute("listaU", lista);
				 
				 RequestDispatcher dispatcher = request.getRequestDispatcher("/PagarCuotaPrestamo.jsp");
				 dispatcher.forward(request, response);
			 }else {
				 request.setAttribute("mensaje", "Debe ingresar un id de prestamo para filtrar");
				 List<Integer> ddlCuentas = cuNeg.obtenerNumerosDeCuentaPorDniNeg(dniCliente);
		         request.setAttribute("cargaDDL", ddlCuentas);
		        	
				 ArrayList<Cuotas> lista = cNeg.listarCuotasPorDni(dniCliente);
		         request.setAttribute("listaU", lista);
		         
		         RequestDispatcher rd = request.getRequestDispatcher("/PagarCuotaPrestamo.jsp");   
				 rd.forward(request, response);
				 
			 }
			 return;
    	}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("btnPagar")!=null) {
			String idprestamo = request.getParameter("idPrestamo");
			int idPrestamo = Integer.parseInt(idprestamo);
			String numcuota = request.getParameter("numCuota");
			int numCuota = Integer.parseInt(numcuota);
			String idcuota = request.getParameter("idCuota");
			int idCuota = Integer.parseInt(idcuota);
			String idcuenta = request.getParameter("numeroCuenta");
			int idCuenta= Integer.parseInt(idcuenta);
			String monto = request.getParameter("monto");
			float Monto= Float.parseFloat(monto);
			LocalDate fechaPago = LocalDate.now();
			
			Cuentas cuenta = cuNeg.obtenerCuentaPorNumero(idCuenta);
			if(cuenta.getSaldo()>=Monto) {
				boolean pagado= cNeg.pagarCuota(idCuota, fechaPago);
				
				if(pagado) {
					boolean actualizado= cuNeg.restarSaldo(idCuenta, Monto);
					if(actualizado) {
						request.setAttribute("mensaje", "Se ha pagado correctamente la cuota "+ numCuota + " del prestamo "+ idPrestamo + ".");
					}else {
						request.setAttribute("mensaje", "Se ha pagado correctamente la cuota "+ numCuota + " del prestamo "+ idPrestamo +" pero no se ha podido actualizar el saldo de la cuenta "+ idCuenta + ".");
					}
				}else {
					request.setAttribute("mensaje", "No se pudo pagar la cuota "+ numCuota + " del prestamo "+ idPrestamo + ".");
				}
				
				List<Integer> ddlCuentas = cuNeg.obtenerNumerosDeCuentaPorDniNeg(dniCliente);
		         request.setAttribute("cargaDDL", ddlCuentas);
				
				ArrayList<Cuotas> lista= cNeg.listarCuotasPorDni(dniCliente);
				request.setAttribute("listaU", lista);
			}else {
				request.setAttribute("mensaje", "No se pudo pagar la cuota debido a que no hay suficiente saldo en la cuenta "+ idCuenta +".");
				
				List<Integer> ddlCuentas = cuNeg.obtenerNumerosDeCuentaPorDniNeg(dniCliente);
		         request.setAttribute("cargaDDL", ddlCuentas);
				
				ArrayList<Cuotas> lista= cNeg.listarCuotasPorDni(dniCliente);
				request.setAttribute("listaU", lista);
			}
			
			RequestDispatcher rd= request.getRequestDispatcher("PagarCuotaPrestamo.jsp");
			rd.forward(request, response);
		}
		doGet(request, response);
	}

}
