package entidades;
import java.time.LocalDate;

import excepciones.CorreoDuplicado;
import excepciones.DniInvalido;

public class Clientes {
	//atributos
	private String dni;
	private String cuil;
	private String nombre;
	private String apellido;
	private String sexo;
	private String nacionalidad;
	private LocalDate fechaNacimiento;
	public String getIDlocalidad() {
		return IDlocalidad;
	}


	public void setIDlocalidad(String iDlocalidad) {
		IDlocalidad = iDlocalidad;
	}

	private String direccion;
	private String localidad;
	private String IDlocalidad;
	private String provincia;
	private String correo;
	private String telefono;
	private Usuarios usuario;
	private boolean estado;
	
	//constructores
	public Clientes() {
		
	}

	
	//sets y gets

	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public static boolean verificarDniInvalido(String dni) throws DniInvalido{
		boolean existeLetra=false;
		for(int i=0;i<dni.length();i++) {
			if(!Character.isDigit(dni.charAt(i))) {
				existeLetra=true;
				throw new DniInvalido();
			}
		}
		if(existeLetra==true) {
			return false;
		}
		return true;
	}


	public Usuarios getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	
}
