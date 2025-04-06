package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import entidades.Movimientos;
import entidades.TipoMovimiento;
import entidades.Transferencias;
import entidades.Usuarios;
import negocioImp.CuentasNegImp;
import negocioImp.MovimientosNegImp;
import negocioImp.TransferenciasNegImp;


@WebServlet("/servletTransferir")
public class servletTransferir extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	CuentasNegImp negCuentas = new CuentasNegImp();
	TransferenciasNegImp negTransferencias = new TransferenciasNegImp();
	MovimientosNegImp negMovimientos = new MovimientosNegImp();
	

    public servletTransferir() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        
        if (request.getParameter("Param") != null) {
        	ArrayList<Cuentas> listaCuentas = (ArrayList<Cuentas>)negCuentas.listarCuentaPorUsuario(usuario);
        	request.setAttribute("listaU", listaCuentas);
			RequestDispatcher rd = request.getRequestDispatcher("Transferir.jsp");   
	        rd.forward(request, response);
        }
        if(request.getParameter("BtnTransferir")!= null) {
        	
        	Clientes existe=negTransferencias.BuscarClientePorCBU(request.getParameter("txtCBU"));
        	if(existe.getApellido() != null ) {
        	Transferencias trans = new Transferencias();
        	trans.setCbuDestino(request.getParameter("txtCBU"));
        	int IDcuenta = Integer.parseInt(request.getParameter("DDLcuenta"));
        	trans.setNroCuentaOrigen(IDcuenta);
        	double monto= Double.parseDouble(request.getParameter("txtCantidadDinero"));
        	trans.setMonto(monto);
        	LocalDate fecha = LocalDate.now();
        	trans.setFecha(fecha);
        	negTransferencias.Transferenciacuenta(trans);
        	cargarMovimientos(IDcuenta,2,monto,fecha,"Retiro de efectivo");
        	Cuentas aux = negTransferencias.BuscarCuentaPorCBU(request.getParameter("txtCBU"));
        	cargarMovimientos(aux.getNroCuenta(),1,monto,fecha,"Deposito de dinero");
        	request.setAttribute("mensaje", "Transferencia enviada a "+ existe.getNombre()+" "+existe.getApellido()+" "+aux.getNroCuenta());
        	}else {
        	request.setAttribute("mensaje", "No existe el CBU ingresado.");
        	}
        	
        	ArrayList<Cuentas> listaCuentas = (ArrayList<Cuentas>)negCuentas.listarCuentaPorUsuario(usuario);
        	request.setAttribute("listaU", listaCuentas);
        	RequestDispatcher rd = request.getRequestDispatcher("Transferir.jsp");   
        	rd.forward(request, response);
        }
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	private void cargarMovimientos(int IDcuenta,int tipoMovimiento,double monto,LocalDate fecha, String detalle) {
		Movimientos movimiento = new Movimientos();
		TipoMovimiento tipo = new TipoMovimiento();
		tipo.setIdTipoMovimiento(tipoMovimiento);
		movimiento.setDetalle(detalle);
		movimiento.setFecha(fecha);
		movimiento.setImporte(monto);
		movimiento.setNroCuenta_M(IDcuenta);
		movimiento.setTipoDeMovimiento(tipo);
		negMovimientos.agregarMovimiento(movimiento);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doGet(request, response);
	}

}
