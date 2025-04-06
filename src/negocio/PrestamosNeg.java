package negocio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import entidades.Prestamos;

public interface PrestamosNeg {
	public ArrayList<Prestamos> listarPrestamos();
	public boolean actualizarEstadoNeg(Prestamos prestamo);
	public boolean agregarPrestamo(Prestamos prestamo);
	public Map<String, Integer> contarPrestamosPorMontosYFechas(LocalDate fechaInicio, LocalDate fechaFin);
	public ArrayList<Prestamos> listarPrestamosPorCuenta(int numCuenta);
}
