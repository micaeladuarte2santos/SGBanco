package entidades;
import java.time.LocalDate;

public class Movimientos {
	
	//atributos
	private int idMovimiento;
	private int nroCuenta_M;
	private LocalDate fecha;
	private String detalle;
	private double importe;
	private TipoMovimiento tipoDeMovimiento;
	
	//constructor
	public Movimientos() {
		
	}
	
	//sets y gets
	
	public int getNroCuenta_M() {
		return nroCuenta_M;
	}
	public int getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(int idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public void setNroCuenta_M(int nroCuenta_M) {
		this.nroCuenta_M = nroCuenta_M;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public TipoMovimiento getTipoDeMovimiento() {
		return tipoDeMovimiento;
	}
	public void setTipoDeMovimiento(TipoMovimiento tipoDeMovimiento) {
		this.tipoDeMovimiento = tipoDeMovimiento;
	}
	
	
}
