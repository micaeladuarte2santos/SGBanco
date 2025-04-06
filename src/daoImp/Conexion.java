package daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


import entidades.Usuarios;

public class Conexion {
	private String host = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String pass = "root";
	private String dbName = "banco??profileSQL=true&useSSL=false";

	protected Connection connection;
	
	public Connection Open()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(host+dbName, user, pass);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return this.connection;
	}
	
	public ResultSet query(String query)
	{
		Statement st;
		ResultSet rs=null;
		try
		{
			st= connection.createStatement();
			rs= st.executeQuery(query);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean execute(String query)
	{
		Statement st;
		boolean save = true;
		try {
			st = connection.createStatement();
		    st.executeUpdate(query);
		}
		catch(SQLException e)
		{
			save = false;
			e.printStackTrace();
		}
		return save;
	}
	
	public boolean close()
	{
		boolean ok=true;
		try {
			connection.close();
		}
		catch(Exception e)
		{
			ok= false;
			e.printStackTrace();
		}
		return ok;
	}
	
	public Usuarios existe(String consulta) {
        Usuarios usuario = null;
        Open(); 
        try (PreparedStatement stmt = connection.prepareStatement(consulta);
             ResultSet rs = stmt.executeQuery()) {//ejecuto la consulta
            if (rs.next()) {//verificar si hay un registro en el ResultSet,si devuelve true significa que existe un usuario con el nombre ingresado.
                usuario = new Usuarios();
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTipoUsuario(rs.getString("tipoUsuario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(); 
        }
        return usuario;
    }
	
	public boolean existeClient( String Consulta ) {
		Open();
		try(PreparedStatement stmt = connection.prepareStatement(Consulta)) {
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		}catch(SQLException e){
			
		}finally {
		 close(); 
		}
	return true;
	}
}
