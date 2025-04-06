package entidades;

import java.time.LocalDate;

public class Cuentas {

	private int nroCuenta;
	private String dni;
	private LocalDate fechaCreacion;
	private TipoCuentas tipoDeCuenta;
	private String cbu;
	private float saldo;
	private boolean estado;
	
	public Cuentas() {
	
	}
	
	
	
	public TipoCuentas getTipoDeCuenta() {
		return tipoDeCuenta;
	}


	public void setTipoDeCuenta(TipoCuentas tipoDeCuenta) {
		this.tipoDeCuenta = tipoDeCuenta;
	}


	public int getNroCuenta() {
		return nroCuenta;
	}
	
	public void setNroCuenta(int nroCuenta) {
		this.nroCuenta = nroCuenta;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setLocalDate(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
	public String getCbu() {
		return cbu;
	}
	
	public void setCbu(String cbu) {
		this.cbu = cbu;
	}
	
	public float getSaldo() {
		return saldo;
	}
	
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	
	public boolean getEstado() {
		return estado;
	}
	
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}