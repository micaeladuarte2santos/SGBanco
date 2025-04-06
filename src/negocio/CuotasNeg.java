package negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import entidades.Cuotas;

public interface CuotasNeg {
	public boolean agregarCuota(Cuotas c);
	public ArrayList<Cuotas> listarCuotasPorDni(String dni);
	public boolean pagarCuota(int idCuota, LocalDate fechaPago);
	public ArrayList<Cuotas> listarCuotasPorPrestamo(int idPrestamo, String dni);
}
