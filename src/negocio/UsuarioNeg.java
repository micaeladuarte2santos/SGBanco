package negocio;
import entidades.Usuarios;

public interface UsuarioNeg {
	public Usuarios validarUsuario(String nombreUsuario);
	public int agregarUsuario(Usuarios usuario);
	public boolean modificarUsuario(Usuarios usuario);
	public String obtenerDniPorNombreUsuario(String nombreUsuario);
}
