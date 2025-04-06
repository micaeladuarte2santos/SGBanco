package daoImp;

import dao.UsuarioDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import entidades.Usuarios;

public class UsuarioDaoImp implements UsuarioDao{
	
	private Conexion con = new Conexion();
	
	public UsuarioDaoImp() {
		
	}
	
	public Usuarios existeUsuario(Usuarios usu) {
	    Usuarios usuario = null;
	    String consulta = "SELECT * FROM Usuarios WHERE nombreUsuario = '" + usu.getNombreUsuario() + "'";
	    
	    try {
	        usuario = con.existe(consulta);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return usuario;
	}

	@Override
	public int agregarUsuario(Usuarios usuario) {
		con = new Conexion();
	    con.Open();
	    int totalRegistros = 0;
	    String consultaInsert = "INSERT INTO Usuarios(nombreUsuario, contraseña) VALUES('" + usuario.getNombreUsuario() + "', '" 
	    		+ usuario.getContraseña() + "')";

	    try {
	        // Inserta el nuevo usuario
	        con.execute(consultaInsert);

	        // Obtiene el conteo total de registros en la tabla
	        String consultaConteo = "SELECT COUNT(*) AS total FROM usuarios";
	        ResultSet rs = con.query(consultaConteo);
	        if (rs.next()) {
	            totalRegistros = rs.getInt("total");
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    } finally {
	        con.close();
	    }

	    return totalRegistros;
	}

	@Override
	public boolean modificarUsuario(Usuarios usuario) {
		con = new Conexion();
		con.Open();
		boolean estado = false;
		String consulta = "UPDATE Usuarios SET contraseña = '" + usuario.getContraseña()+ "'  where idUsuario = "+usuario.getIdUsuario();

		
		try
		{
			estado=con.execute(consulta);
		 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return estado;
		}
	
	/*public String obtenerDniPorNombreUsuario(String nombreUsuario) {
		String dni = null;
		con = new Conexion();
	    con.Open();
	    try {
	        PreparedStatement stmt = con.connection.prepareStatement("SELECT dni FROM Usuarios WHERE nombreUsuario = ? AND estado = 1");
	        stmt.setString(1, nombreUsuario);

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            dni = rs.getString("dni");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dni;
	}*/
	
	public String obtenerDniPorNombreUsuario(String nombreUsuario) {
	    String dni = null;
	    try (Connection con = new Conexion().Open();
	         PreparedStatement stmt = con.prepareStatement("SELECT c.dni ,c.nombre FROM Usuarios u INNER JOIN Clientes c ON u.idUsuario = c.idUsuario_C WHERE u.nombreUsuario = ? AND u.estado = 1 AND c.estado = 1")) {

	        stmt.setString(1, nombreUsuario); // Asegúrate de que el nombre de usuario esté correctamente configurado
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                dni = rs.getString("dni"); // Verifica que la columna "dni" esté bien en la base de datos
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Log de errores en caso de problemas con la base de datos
	    }
	    return dni;
	}
}


