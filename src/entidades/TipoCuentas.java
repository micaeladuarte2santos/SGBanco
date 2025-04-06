package entidades;

public class TipoCuentas {
	int idTipoCuenta;
	String nombreCuenta;
	
	public TipoCuentas() {
		
	}
	
	public int getIdTipoCuenta() {
		return idTipoCuenta;
	}
	public void setIdTipoCuenta(int idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}
	
	public String getNombreCuenta() {
		return nombreCuenta;
	}
	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}
	
	@Override
	public String toString() {
		return "TipoCuentas [idTipoCuenta=" + idTipoCuenta + ", nombreCuenta=" + nombreCuenta + "]";
	}
}
