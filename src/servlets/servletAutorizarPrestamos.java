package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entidades.Usuarios;
import javax.servlet.http.HttpSession;

import entidades.Clientes;
import entidades.Prestamos;
import negocio.PrestamosNeg;
import negocioImp.PrestamosNegImp;
import entidades.Cuotas;
import negocio.CuotasNeg;
import negocioImp.CoutasNegImp;
import entidades.Cuentas;
import negocio.CuentasNeg;
import negocioImp.CuentasNegImp;
import entidades.Movimientos;
import negocio.MovimientosNeg;
import negocioImp.MovimientosNegImp;
import entidades.TipoMovimiento;
import negocio.TipoDeMovimientoNeg;
import negocioImp.TipoDeMovimientosNegImp;


@WebServlet("/servletAutorizarPrestamos")
public class servletAutorizarPrestamos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrestamosNeg pNeg = new PrestamosNegImp();
	CuotasNeg cNeg = new CoutasNegImp();
	CuentasNeg cuentaNeg = new CuentasNegImp();
	MovimientosNeg mNeg = new MovimientosNegImp();
	TipoDeMovimientoNeg tNeg = new TipoDeMovimientosNegImp();

    public servletAutorizarPrestamos() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        if (request.getParameter("btnBuscarCuenta")!= null) {
        	String cuentaBuscar = request.getParameter("txtBuscarCuenta");
            int numCuenta = Integer.parseInt(cuentaBuscar);
        	// Obtener lista filtrada
            ArrayList<Prestamos> listaFiltrada = pNeg.listarPrestamosPorCuenta(numCuenta);

            // Pasar lista filtrada al JSP
            request.setAttribute("listaU", listaFiltrada);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AutorizacionPrestamos.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Si el botón de "Mostrar Todo" fue presionado
        if (request.getParameter("btnMostrarTodo") != null) {

        	ArrayList<Prestamos> lista= pNeg.listarPrestamos();
			request.setAttribute("listaU", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/AutorizacionPrestamos.jsp");   
			
	        rd.forward(request, response);
        }
        
        if (request.getParameter("Param") != null) {
            // Listar los préstamos y enviarlos al JSP
            ArrayList<Prestamos> lista = pNeg.listarPrestamos();
            request.setAttribute("listaU", lista);

            RequestDispatcher rd = request.getRequestDispatcher("AutorizacionPrestamos.jsp");
            rd.forward(request, response);
        }
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("btnModificar") != null) {
			
	        // Obtener los parámetros necesarios
	        int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
	        String nuevoEstado = request.getParameter("ddlEstado");
	        
	        int nroCuenta = Integer.parseInt(request.getParameter("nroCuenta"));
	        float importePedidoPorCliente= Float.parseFloat(request.getParameter("importePedidoPorCliente"));
	        int cantidadCuotas=Integer.parseInt(request.getParameter("cantidadCuotas"));
	        double montoPorMes=Double.parseDouble(request.getParameter("montoPorMes"));

	        // Actualizar el estado del préstamo
	        Prestamos prestamo = new Prestamos();
	        prestamo.setIdPrestamo(idPrestamo);
	        prestamo.setEstado(nuevoEstado);
	        
	        boolean actualizado = pNeg.actualizarEstadoNeg(prestamo);

	        // Verificar el resultado de la operación
	        if (actualizado) {
	            request.setAttribute("mensaje", "Estado actualizado correctamente.");
	        } else {
	            request.setAttribute("mensaje", "Error al actualizar el estado.");
	        }
	        
	        if(nuevoEstado.equals("Aprobado")) {
	        	
	        	//creo las cuotas correspondientes al prestamo
		        LocalDate fechaActual = LocalDate.now();
		        for(int i=1;i<=cantidadCuotas;i++) {
		        	Cuotas c = new Cuotas();
		        	c.setIdPrestamo(idPrestamo);
		        	c.setNumeroCuota(i);
		        	LocalDate fecha = fechaActual.plusMonths(i);
		        	c.setFechaVencimientoCuota(fecha);
		        	c.setMonto(montoPorMes);
		        	c.setEstado("pendiente");
		        	
		        	cNeg.agregarCuota(c);
		        }
		        	
		        //creo el movimiento correspondiente
		       	Movimientos mov = new Movimientos();
		       	mov.setNroCuenta_M(nroCuenta);
		       	mov.setFecha(fechaActual);
		       	mov.setDetalle("Alta de un préstamo");
		        mov.setImporte(importePedidoPorCliente);
		            
		        int idTipoMovimiento = tNeg.obtenerIdPorDescripcion("Alta Préstamo");
		        TipoMovimiento tipoMovimiento = new TipoMovimiento();
		        tipoMovimiento.setIdTipoMovimiento(idTipoMovimiento);
		            
		        mov.setTipoDeMovimiento(tipoMovimiento);
		        mNeg.agregarMovimiento(mov);
		        	
		       	System.out.println("Agregando movimiento con los siguientes datos:");
		       	System.out.println("Número de Cuenta: " + mov.getNroCuenta_M());
		       	System.out.println("Fecha: " + mov.getFecha());
		       	System.out.println("Detalle: " + mov.getDetalle());
		       	System.out.println("Importe: " + mov.getImporte());
		       	System.out.println("ID Tipo Movimiento: " + mov.getTipoDeMovimiento().getIdTipoMovimiento());
		        	
		        //actualizo el saldo de la cuenta que pidio el prestamo
			    cuentaNeg.sumarSaldo(nroCuenta, importePedidoPorCliente);
		        
	        }

	        // Volver a cargar la lista de préstamos para mostrar en el JSP
	        ArrayList<Prestamos> lista = pNeg.listarPrestamos();
	        request.setAttribute("listaU", lista);

	        RequestDispatcher rd = request.getRequestDispatcher("AutorizacionPrestamos.jsp");
	        rd.forward(request, response);
	        return;
	    }
		doGet(request, response);
	}

}
