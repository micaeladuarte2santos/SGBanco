package entidades;

public class Localidades {
	String IdLocalidad;   
	String nombreLocalidad;
	public String getNombreLocalidad() {
		return nombreLocalidad;
	}
	public String getIdLocalidad() {
		return IdLocalidad;
	}
	public void setIdLocalidad(String idLocalidad) {
		IdLocalidad = idLocalidad;
	}
	@Override
	public String toString() {
		return "Localidades [IdLocalidad=" + IdLocalidad + ", nombreLocalidad=" + nombreLocalidad + "]";
	}
	public void setNombreLocalidad(String nombreLocalidad) {
		this.nombreLocalidad = nombreLocalidad;
	} 
}
