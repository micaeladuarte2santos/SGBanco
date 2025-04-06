package negocioImp;

import entidades.Cuotas;
import negocio.CuotasNeg;

import java.time.LocalDate;
import java.util.ArrayList;

import dao.CuotasDao;
import daoImp.CuotasDaoImp;

public class CoutasNegImp implements CuotasNeg{

	private CuotasDao cdao = new CuotasDaoImp(); 
	
	public boolean agregarCuota(Cuotas c) {
		return cdao.agregarCuota(c);
	}

	public ArrayList<Cuotas> listarCuotasPorDni(String dni) {
		return (ArrayList<Cuotas>) cdao.listarCuotasPorDni(dni);
	}
	
	public boolean pagarCuota(int idCuota, LocalDate fechaPago) {
		return cdao.pagarCuota(idCuota, fechaPago);
	}

	public ArrayList<Cuotas> listarCuotasPorPrestamo(int idPrestamo, String dni) {
		return (ArrayList<Cuotas>) cdao.listarCuotasPorPrestamo(idPrestamo,dni);
	}
}
