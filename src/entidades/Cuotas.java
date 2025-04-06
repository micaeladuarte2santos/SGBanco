package entidades;

import java.time.LocalDate;

public class Cuotas {
	//atributos
	private int idCuota;
	private int idPrestamo;
	private int numeroCuota;
	private LocalDate fechaVencimientoCuota;
	private LocalDate fechaPago;
	private double monto;
	private String estado;
	
	//contructores
	public Cuotas() {
		
	}

	//sets y gets
	public int getIdCuota() {
		return idCuota;
	}

	public void setIdCuota(int idCuota) {
		this.idCuota = idCuota;
	}

	public int getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public int getNumeroCuota() {
		return numeroCuota;
	}

	public void setNumeroCuota(int numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public LocalDate getFechaVencimientoCuota() {
		return fechaVencimientoCuota;
	}

	public void setFechaVencimientoCuota(LocalDate fechaVencimientoCuota) {
		this.fechaVencimientoCuota = fechaVencimientoCuota;
	}
	
	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
}
