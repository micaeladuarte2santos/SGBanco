package servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Usuarios;
import entidades.Prestamos;
import negocioImp.ClienteNegImp;
import negocioImp.PrestamosNegImp;

/**
 * Servlet implementation class servletPedirPrestamos
 */
@WebServlet("/servletPedirPrestamos")
public class servletPedirPrestamos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteNegImp negClientes = new ClienteNegImp();
	PrestamosNegImp negPrestamos = new PrestamosNegImp();

    public servletPedirPrestamos() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        
        if (request.getParameter("Param") != null) {
        	if(request.getParameter("btnPedirPrestamo") != null) {
                String dni = request.getParameter("txtDni");
                String nroCuenta = request.getParameter("txtNroCuenta");
                String fecha = request.getParameter("Fecha");
                String importeIntereses = request.getParameter("txtImporteIntereses");
                String importePedido = request.getParameter("txtImportePedido");
                String plazo = request.getParameter("txtPlazo");
                String monto = request.getParameter("txtMonto");
    	        
    	        boolean agregado = false;
				boolean existeCliente = negClientes.validarCliente(dni);
				
				if(existeCliente == false) {
					 request.setAttribute("mensaje", "Cliente inexistente.");
				}
				agregado = CargarPrestamo(dni, nroCuenta, fecha, importeIntereses, importePedido, plazo, monto);
	        	 if(agregado) {
	        		 request.setAttribute("mensaje", "Prestamo agregado con exito");
	        	 }else {
	        		 request.setAttribute("mensaje", "Error al agendar prestamo");
	        	 }
        	
        	}
        }
			RequestDispatcher rd = request.getRequestDispatcher("PedirPrestamo.jsp");   
	        rd.forward(request, response);
	        
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	public boolean CargarPrestamo(String dni, String nroCuenta, String fecha, String importeIntereses, String importePedido, String plazo, String monto) {
        Prestamos prestamo = new Prestamos();
        
        prestamo.setIdPrestamo(1);
        
        prestamo.setDni(dni);
        
        int auxNroCuenta = Integer.parseInt(nroCuenta);
        prestamo.setNroCuenta(auxNroCuenta);
        
        LocalDate auxFechaPrestamo = LocalDate.parse(fecha);
        prestamo.setFechaPrestamo(auxFechaPrestamo);
        
        float auxImporteIntereses = Float.parseFloat(importeIntereses);
        prestamo.setImporteConIntereses(auxImporteIntereses);
        
        float auxImportePedido = Float.parseFloat(importePedido);
        prestamo.setImportePedidoPorCliente(auxImportePedido);
        
        int auxPlazo = Integer.parseInt(plazo);
        prestamo.setPlazoPago(auxPlazo);
        
        prestamo.setCantidadCuotas(1);
        
        float auxMonto = Float.parseFloat(monto);
        prestamo.setMontoPorMes(auxMonto);
        
        String estado = "pendiente";
        prestamo.setEstado(estado);
       
        return negPrestamos.agregarPrestamo(prestamo);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
