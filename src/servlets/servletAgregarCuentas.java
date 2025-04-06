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
import entidades.TipoCuentas;
import entidades.TipoMovimiento;
import negocio.MovimientosNeg;
import negocio.TipoCuentaNeg;
import negocio.TipoDeMovimientoNeg;
import negocioImp.TipoCuentaNegImp;
import negocioImp.TipoDeMovimientosNegImp;
import entidades.Cuentas;
import entidades.Movimientos;
import negocioImp.ClienteNegImp;
import negocioImp.CuentasNegImp;
import negocioImp.MovimientosNegImp;
import negocioImp.UsuarioNegImp;

/**
 * Servlet implementation class serletAgregarClientes
 */
@WebServlet("/servletAgregarCuentas")
public class servletAgregarCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsuarioNegImp negUsuarios = new UsuarioNegImp();
	ClienteNegImp negClientes = new ClienteNegImp();
	CuentasNegImp negCuentas = new CuentasNegImp();
	TipoCuentaNeg tNeg= new TipoCuentaNegImp();
	MovimientosNeg mNeg = new MovimientosNegImp();
	TipoDeMovimientoNeg tmNeg = new TipoDeMovimientosNegImp();

    public servletAgregarCuentas() {
        super();
       
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Si el parámetro Param es 1, redirigir al login
        if ((request.getParameter("Param")!=null)) {
        	ArrayList<TipoCuentas> tiposCuentas = tNeg.cargarDDlTipoCuenta();
        	request.setAttribute("tiposCuentas", tiposCuentas);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCuenta.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        if(request.getParameter("btnAgregar") != null) {
            String cbu = request.getParameter("txtCbu");
            String dni = request.getParameter("txtDni");
            String tipoCuenta = request.getParameter("ddlTipoCuenta");
            String fechaCreacion = request.getParameter("FechaCreacion");
	        String nroCuenta = request.getParameter("txtNroCuenta");
	        
	        boolean agregado = false;
	        boolean existeCliente = negClientes.validarCliente(dni);
	        
	        if(negCuentas.cantidadCuentas(dni)<3) {
	        	 agregado = CargarCuenta(cbu, dni, tipoCuenta, fechaCreacion, nroCuenta);
	        	 if(agregado) {
	        		 request.setAttribute("mensaje", "Cuenta agregada con exito.");
	        	 }else {
	        		 request.setAttribute("mensaje", "Error al agregar cuenta.");
	        	 }
	        }else {
	        	request.setAttribute("mensaje", "El cliente con DNI " + dni + " ya tiene 3 o mas cuentas.");
	        }
	        
	        
	        ArrayList<TipoCuentas> tiposCuentas = tNeg.cargarDDlTipoCuenta();
        	request.setAttribute("tiposCuentas", tiposCuentas);
        	
        	int auxNroCuenta = Integer.parseInt(nroCuenta);
        	LocalDate fechaActual = LocalDate.now();
        	
        	Movimientos mov = new Movimientos();
	       	mov.setNroCuenta_M(auxNroCuenta);
	       	mov.setFecha(fechaActual);
	       	mov.setDetalle("Alta de una cuenta");
	        mov.setImporte(10000);
	            
	        int idTipoMovimiento = tmNeg.obtenerIdPorDescripcion("Apertura de Cuenta");
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
	    }
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCuenta.jsp");
            dispatcher.forward(request, response);
          
            response.getWriter().append("Served at: ").append(request.getContextPath());
    }
    
    
    public boolean CargarCuenta(String cbu, String dni, String tipoCuenta, String fechaCreacion, String nroCuenta) {
        Cuentas cuenta = new Cuentas();
        cuenta.setCbu(cbu);
        cuenta.setDni(dni);
       // le asigno el tipo de cuenta
        ArrayList<TipoCuentas> listaTipoCuentas = tNeg.cargarDDlTipoCuenta();
        for (TipoCuentas tipo : listaTipoCuentas) {
            if (tipo.getIdTipoCuenta() == Integer.parseInt(tipoCuenta)) {
                cuenta.setTipoDeCuenta(tipo); 
                break;
            }
        }
        
        LocalDate auxFechaCreacion = LocalDate.parse(fechaCreacion);
        cuenta.setLocalDate(auxFechaCreacion);
        
        int auxNroCuenta = Integer.parseInt(nroCuenta);
        cuenta.setNroCuenta(auxNroCuenta);
        
        cuenta.setSaldo(10000); 
        cuenta.setEstado(true); 
       
        return negCuentas.agregarCuentas(cuenta);
    }

    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
