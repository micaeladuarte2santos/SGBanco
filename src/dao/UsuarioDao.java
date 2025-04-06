package dao;
import entidades.Usuarios;

public interface UsuarioDao {
	
	public Usuarios existeUsuario(Usuarios usuario);
	public int agregarUsuario(Usuarios usuario);
	public boolean modificarUsuario(Usuarios usuario);
	public String obtenerDniPorNombreUsuario(String nombreUsuario);
}
