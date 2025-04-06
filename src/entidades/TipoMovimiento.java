package entidades;

public class TipoMovimiento {
	//atributos
	private int idTipoMovimiento;
	private String nombre;
	
	//constructor
	public TipoMovimiento() {
		
	}
	
	//sets y gets
	public int getIdTipoMovimiento() {
		return idTipoMovimiento;
	}
	public void setIdTipoMovimiento(int idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
