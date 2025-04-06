package negocioImp;

import dao.UsuarioDao;
import daoImp.UsuarioDaoImp;
import entidades.Usuarios;
import negocio.UsuarioNeg;

public class UsuarioNegImp implements UsuarioNeg{
	
	private UsuarioDao uDao = new UsuarioDaoImp();

	public Usuarios validarUsuario(String nombreUsuario)
    {
		Usuarios usu = new Usuarios();
        usu.setNombreUsuario(nombreUsuario);

        Usuarios usuarioExistente = uDao.existeUsuario(usu);

        if (usuarioExistente != null) {
            return usuarioExistente;
        } else {
            // Si no existe devuelvo un usuario de tipo 0
            usu.setTipoUsuario("0");
            return usu;
        }
    }

	@Override
	public int agregarUsuario(Usuarios usuario) {
		return uDao.agregarUsuario(usuario);
	}

	@Override
	public boolean modificarUsuario(Usuarios usuario) {
		return uDao.modificarUsuario(usuario);
	}
	
	public String obtenerDniPorNombreUsuario(String nombreUsuario) {
		return uDao.obtenerDniPorNombreUsuario(nombreUsuario);
	}
}
