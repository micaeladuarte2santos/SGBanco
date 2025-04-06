package negocioImp;
import dao.PrestamosDao;
import daoImp.PrestamosDaoImp;
import negocio.PrestamosNeg;
import entidades.Prestamos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class PrestamosNegImp implements PrestamosNeg{
	
	private PrestamosDao pDao = new PrestamosDaoImp();
	
	public PrestamosNegImp() {
		
	}
	
	public ArrayList<Prestamos> listarPrestamos() {
		return (ArrayList<Prestamos>) pDao.obtenerTodos();
	}
	
	public boolean actualizarEstadoNeg(Prestamos prestamo) {
	    return pDao.actualizarEstado(prestamo);
	}
	
	public Map<String, Integer> contarPrestamosPorMontosYFechas(LocalDate fechaInicio, LocalDate fechaFin){
		return pDao.contarPrestamosPorMontosYFechas(fechaInicio, fechaFin);
	}

	public ArrayList<Prestamos> listarPrestamosPorCuenta(int numCuenta) {
		return pDao.listarPrestamosPorCuenta(numCuenta); 
	}

	public boolean agregarPrestamo(Prestamos prestamo) {
		return pDao.agregarPrestamo(prestamo);
	}
}
