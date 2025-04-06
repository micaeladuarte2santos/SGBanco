package entidades;
import java.time.LocalDate;


public class Prestamos {
	//atributos
	private int idPrestamo;
	private String dni_Pr;
	private int nroCuenta_Pr;
	private LocalDate fechaPrestamo;
	private double importeConIntereses;
	private double importePedidoPorCliente;
	private int plazoPago;
	private int cantidadCuotas;
	private double montoPorMes;
	private String estado;
	
	//constructores
	public Prestamos() {
		
	}
	
	//sets y gest
	public int getIdPrestamo() {
		return idPrestamo;
	}
	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}
	public String getDni() {
		return dni_Pr;
	}
	public void setDni(String dni_Pr) {
		this.dni_Pr = dni_Pr;
	}
	public int getNroCuenta() {
		return nroCuenta_Pr;
	}
	public void setNroCuenta(int nroCuenta_Pr) {
		this.nroCuenta_Pr = nroCuenta_Pr;
	}
	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public double getImporteConIntereses() {
		return importeConIntereses;
	}
	public void setImporteConIntereses(double importeConIntereses) {
		this.importeConIntereses = importeConIntereses;
	}
	public double getImportePedidoPorCliente() {
		return importePedidoPorCliente;
	}
	public void setImportePedidoPorCliente(double importePedidoPorCliente) {
		this.importePedidoPorCliente = importePedidoPorCliente;
	}
	public int getPlazoPago() {
		return plazoPago;
	}
	public void setPlazoPago(int plazoPago) {
		this.plazoPago = plazoPago;
	}
	public int getCantidadCuotas() {
		return cantidadCuotas;
	}
	public void setCantidadCuotas(int cantidadCuotas) {
		this.cantidadCuotas = cantidadCuotas;
	}
	public double getMontoPorMes() {
		return montoPorMes;
	}
	public void setMontoPorMes(double montoPorMes) {
		this.montoPorMes = montoPorMes;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
}
