package negocioImp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.MovimientosDao;
import daoImp.MovimientosDaoImp;
import entidades.Movimientos;
import negocio.MovimientosNeg;

public class MovimientosNegImp implements MovimientosNeg{
	private MovimientosDao mDao = new MovimientosDaoImp();
	
	public ArrayList<Movimientos> listarMovimientos(String dni){
		return (ArrayList<Movimientos>) mDao.obtenerTodos(dni);
	}

	@Override
	public Map<String, Integer> porcentajeTiposMovimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
		return mDao.porcentajeTiposMovimientosPorFecha(fechaInicio, fechaFin);
	}
	
	public boolean agregarMovimiento(Movimientos movimiento) {
		return mDao.agregarMovimiento(movimiento);
	}
	
	public List<Movimientos> obtenerMovimientosPorCuenta(int nroCuenta){
		return mDao.obtenerMovimientosPorCuenta(nroCuenta);
	}
}
