package entidades;

import java.time.LocalDate;


public class Transferencias {
	private int tranferncia;
	private int nroCuentaOrigen;
	private double monto;
	private String CbuDestino;
	private LocalDate fecha;
	
	
	public int getTranferncia() {
		return tranferncia;
	}
	public void setTranferncia(int tranferncia) {
		this.tranferncia = tranferncia;
	}
	public int getNroCuentaOrigen() {
		return nroCuentaOrigen;
	}
	public void setNroCuentaOrigen(int nroCuentaOrigen) {
		this.nroCuentaOrigen = nroCuentaOrigen;
	}

	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}


	public String getCbuDestino() {
		return CbuDestino;
	}
	public void setCbuDestino(String cbuDestino) {
		CbuDestino = cbuDestino;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
}
