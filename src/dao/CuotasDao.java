package dao;

import java.time.LocalDate;
import java.util.List;
import entidades.Cuotas;

public interface CuotasDao {
	public boolean agregarCuota(Cuotas c);
	public List<Cuotas> listarCuotasPorDni(String dni);
	public boolean pagarCuota(int idCuota, LocalDate fechaPago);
	public List<Cuotas> listarCuotasPorPrestamo(int idPrestamo, String dni);
}
