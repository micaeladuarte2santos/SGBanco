package dao;
import entidades.Movimientos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;



public interface MovimientosDao {
	public List<Movimientos> obtenerTodos(String dni);
	public Map<String, Integer> porcentajeTiposMovimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
	public boolean agregarMovimiento(Movimientos movimiento);
	public List<Movimientos> obtenerMovimientosPorCuenta(int nroCuenta);
}
