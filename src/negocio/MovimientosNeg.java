package negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entidades.Movimientos;

public interface MovimientosNeg {
	public ArrayList<Movimientos> listarMovimientos(String dni);
	public Map<String, Integer> porcentajeTiposMovimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
	public boolean agregarMovimiento(Movimientos movimiento);
	public List<Movimientos> obtenerMovimientosPorCuenta(int nroCuenta);
}
