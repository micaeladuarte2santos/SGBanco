package dao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entidades.Prestamos;

public interface PrestamosDao {
	public List<Prestamos> obtenerTodos();
	public boolean actualizarEstado(Prestamos prestamo);
	public boolean agregarPrestamo(Prestamos prestamo);
	public Map<String, Integer> contarPrestamosPorMontosYFechas(LocalDate fechaInicio, LocalDate fechaFin);
	public ArrayList<Prestamos> listarPrestamosPorCuenta(int numCuenta);
}
