package entidades;

public class Usuarios {
	//atributos
	private int idUsuario;
	private int idCliente;
	private String nombreUsuario;
	private String contraseña;
	private String tipoUsuario;
	private boolean estado;
	
	//constructores
	public Usuarios(int idUsuario, int idCliente, String nombreUsuario, String contraseña, String tipoUsuario,boolean estado) {
		this.idUsuario = idUsuario;
		this.idCliente = idCliente;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
		this.tipoUsuario = tipoUsuario;
		this.estado = estado;
	}
	
	
	public Usuarios() {
		idUsuario=-1;
	}


	//sets y gets
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
}
