package daoImp;
import dao.ClienteDao;
import entidades.Clientes;
import entidades.Usuarios;
import excepciones.CorreoDuplicado;
import excepciones.DniInvalido;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;


public class ClienteDaoImp implements ClienteDao{
	private Conexion cn;
	
	public boolean borrar(String dni) {
		boolean estado=true;
		cn = new Conexion();
		cn.Open();
		String query = "UPDATE Clientes SET estado=0 WHERE dni='" + dni + "'";
		System.out.println(query);
		try{
			estado=cn.execute(query);
		 }
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			cn.close();
		}
		return estado;
	}
	
	public List<Clientes> obtenerTodos() {
		cn = new Conexion();
		cn.Open();
		 List<Clientes> list = new ArrayList<Clientes>();
		 try
		 {
			 ResultSet rs= cn.query("SELECT dni,cuil,nombre,apellido,sexo,nacionalidad,fechaNacimiento,direccion,nombreLocalidad AS localidad,nombreProvincia AS provincia,correo,telefono,idUsuario_C,contraseña, idLocalidad_L FROM  (Clientes INNER JOIN Localidades  ON idLocalidad_C = idLocalidad_L) INNER JOIN Provincias ON idProvincia_L = idProvincia_P WHERE estado=1");
			 while(rs.next())
			 {
				 Clientes cliente = new Clientes();
				 cliente.setDni(rs.getString("dni"));
				 cliente.setCuil(rs.getString("cuil"));
				 cliente.setNombre(rs.getString("nombre"));
				 cliente.setApellido(rs.getString("apellido"));
				 cliente.setSexo(rs.getString("sexo"));
				 cliente.setNacionalidad(rs.getString("nacionalidad"));
				 Date fecha = rs.getDate("fechaNacimiento");
				 cliente.setFechaNacimiento(fecha.toLocalDate());
				 cliente.setDireccion(rs.getString("direccion"));
				 cliente.setLocalidad(rs.getString("localidad"));
				 cliente.setProvincia(rs.getString("provincia"));
				 cliente.setCorreo(rs.getString("correo"));
				 cliente.setTelefono(rs.getString("telefono"));
				 Usuarios usu = new Usuarios();
				 usu.setIdUsuario(rs.getInt("idUsuario_C"));
				 usu.setContraseña(rs.getString("contraseña"));
				 cliente.setUsuario(usu);
				 cliente.setIDlocalidad(rs.getString("idLocalidad_L"));
				 list.add(cliente);
			 }
			 
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 finally{
			 cn.close();
		 }
		 return list;
	}
	

	@Override
	public boolean existeCliente(String cli) {
		cn = new Conexion();
		cn.Open();
		String consulta = "SELECT * FROM Clientes WHERE dni = '" + cli + "'";
		
	    try {
	          return cn.existeClient(consulta);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return true;
	}

	@Override
	public boolean agregarCliente(Clientes cliente) {
		  cn = new Conexion();
		    cn.Open();
		    boolean agregar = false;

		    // Consulta SQL para insertar los datos del cliente
		    String consultaInsert = "INSERT INTO Clientes(dni, cuil, nombre, apellido, sexo, nacionalidad, fechaNacimiento, direccion, "
		            + "idLocalidad_C, correo, telefono, idUsuario_C, contraseña) "
		            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		    try {
		        // Verificar si el DNI es válido
		        if (!Clientes.verificarDniInvalido(cliente.getDni())) {
		            throw new DniInvalido();
		        }

		        // Usar PreparedStatement para proteger contra inyecciones SQL
		        PreparedStatement stmt = cn.connection.prepareStatement(consultaInsert);
		        stmt.setString(1, cliente.getDni());
		        stmt.setString(2, cliente.getCuil());
		        stmt.setString(3, cliente.getNombre());
		        stmt.setString(4, cliente.getApellido());
		        stmt.setString(5, cliente.getSexo());
		        stmt.setString(6, cliente.getNacionalidad());
		        stmt.setDate(7, Date.valueOf(cliente.getFechaNacimiento()));
		        stmt.setString(8, cliente.getDireccion());
		        stmt.setString(9, cliente.getLocalidad());
		        stmt.setString(10, cliente.getCorreo());
		        stmt.setString(11, cliente.getTelefono());
		        stmt.setInt(12, cliente.getUsuario().getIdUsuario());
		        stmt.setString(13, cliente.getUsuario().getContraseña());
		        // Ejecutar la inserción
		        int filasInsertadas = stmt.executeUpdate();
		        agregar = filasInsertadas > 0;

		    } catch (SQLException e) {
		        System.out.println("Error de SQL: " + e.getMessage());
		        e.printStackTrace();
		    } catch (DniInvalido e) {
		        System.out.println("DNI Inválido: " + cliente.getDni());
		        e.printStackTrace();
		    } finally {
		        cn.close();
		    }
		    return agregar;
	}

	@Override
	public boolean modificarCliente(Clientes cli)  {
		cn = new Conexion();
		cn.Open();
		boolean estado = false;
		String fechaNacimientoStr = cli.getFechaNacimiento().toString(); 
		try {
			if(verificarCorreo(cli.getCorreo())== true) {
				throw new CorreoDuplicado();
			}
			String consulta = "UPDATE Clientes SET cuil = '" + cli.getCuil() +
					"', nombre = '" + cli.getNombre() +
					"', apellido = '" + cli.getApellido() +
					"', sexo = '" + cli.getSexo() +
					"', nacionalidad = '" + cli.getNacionalidad() +
					"', fechaNacimiento = '" + fechaNacimientoStr +
					"', direccion = '" + cli.getDireccion() +
					"', idLocalidad_C = '" + cli.getLocalidad() + 
					"', correo = '" + cli.getCorreo() +
					"', telefono = '" + cli.getTelefono() +
					"', contraseña = '" + cli.getUsuario().getContraseña() +
					"' WHERE dni = '" + cli.getDni() + "'";
			cn.execute(consulta);
			
		}catch (CorreoDuplicado e1) {
	        System.out.println(e1.getMessage());
			String consulta = "UPDATE Clientes SET cuil = '" + cli.getCuil() +
					"', nombre = '" + cli.getNombre() +
					"', apellido = '" + cli.getApellido() +
					"', sexo = '" + cli.getSexo() +
					"', nacionalidad = '" + cli.getNacionalidad() +
					"', fechaNacimiento = '" + fechaNacimientoStr +
					"', direccion = '" + cli.getDireccion() +
					"', idLocalidad_C = '" + cli.getLocalidad() + 
					"', telefono = '" + cli.getTelefono() +
					"', contraseña = '" + cli.getUsuario().getContraseña() +
					"' WHERE dni = '" + cli.getDni() + "'";
			cn.execute(consulta);
	        e1.printStackTrace();
		}

		finally{
			cn.close();
		}
		return estado;
	}

	@Override
	public List<Clientes> listarClientesPorDni(String dni) {
		cn = new Conexion();
		cn.Open();
		 List<Clientes> list = new ArrayList<Clientes>();
		 String consulta = "SELECT dni,cuil,nombre,apellido,sexo,nacionalidad,fechaNacimiento,direccion,nombreLocalidad AS localidad,nombreProvincia AS provincia,correo,telefono,idUsuario_C,contraseña, idLocalidad_L FROM  (Clientes INNER JOIN Localidades  ON idLocalidad_C = idLocalidad_L) INNER JOIN Provincias ON idProvincia_L = idProvincia_P WHERE estado=1 and dni like '"+dni+"%'";
		 try {
			 ResultSet rs= cn.query(consulta);
			 while(rs.next())
			 {
				 Clientes cliente = new Clientes();
				 cliente.setDni(rs.getString("dni"));
				 cliente.setCuil(rs.getString("cuil"));
				 cliente.setNombre(rs.getString("nombre"));
				 cliente.setApellido(rs.getString("apellido"));
				 cliente.setSexo(rs.getString("sexo"));
				 cliente.setNacionalidad(rs.getString("nacionalidad"));
				 Date fecha = rs.getDate("fechaNacimiento");
				 cliente.setFechaNacimiento(fecha.toLocalDate());
				 cliente.setDireccion(rs.getString("direccion"));
				 cliente.setLocalidad(rs.getString("localidad"));
				 cliente.setProvincia(rs.getString("provincia"));
				 cliente.setCorreo(rs.getString("correo"));
				 cliente.setTelefono(rs.getString("telefono"));
				 Usuarios usu = new Usuarios();
				 usu.setIdUsuario(rs.getInt("idUsuario_C"));
				 usu.setContraseña(rs.getString("contraseña"));
				 cliente.setUsuario(usu);
				 cliente.setIDlocalidad(rs.getString("idLocalidad_L"));
				 list.add(cliente);
			 } 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 finally {
			 cn.close();
		 }
		 return list;
	}

	@Override
	public List<Clientes> listarClientesPorProvincia(String Provincia) {
		cn = new Conexion();
		cn.Open();
		 List<Clientes> list = new ArrayList<Clientes>();
		 String consulta = "SELECT dni,cuil,nombre,apellido,sexo,nacionalidad,fechaNacimiento,direccion,nombreLocalidad AS localidad,nombreProvincia AS provincia,correo,telefono,idUsuario_C,contraseña, idLocalidad_L FROM  (Clientes INNER JOIN Localidades  ON idLocalidad_C = idLocalidad_L) INNER JOIN Provincias ON idProvincia_L = idProvincia_P WHERE estado=1 and idProvincia_P = '"+Provincia+"'";
		 try {
			 ResultSet rs= cn.query(consulta);
			 while(rs.next())
			 {
				 Clientes cliente = new Clientes();
				 cliente.setDni(rs.getString("dni"));
				 cliente.setCuil(rs.getString("cuil"));
				 cliente.setNombre(rs.getString("nombre"));
				 cliente.setApellido(rs.getString("apellido"));
				 cliente.setSexo(rs.getString("sexo"));
				 cliente.setNacionalidad(rs.getString("nacionalidad"));
				 Date fecha = rs.getDate("fechaNacimiento");
				 cliente.setFechaNacimiento(fecha.toLocalDate());
				 cliente.setDireccion(rs.getString("direccion"));
				 cliente.setLocalidad(rs.getString("localidad"));
				 cliente.setProvincia(rs.getString("provincia"));
				 cliente.setCorreo(rs.getString("correo"));
				 cliente.setTelefono(rs.getString("telefono"));
				 Usuarios usu = new Usuarios();
				 usu.setIdUsuario(rs.getInt("idUsuario_C"));
				 usu.setContraseña(rs.getString("contraseña"));
				 cliente.setUsuario(usu);
				 cliente.setIDlocalidad(rs.getString("idLocalidad_L"));
				 list.add(cliente);	 
			 }		 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 finally{
			 cn.close();
		 }
		 return list;
	}
	
	public boolean verificarCorreo(String correo) {
	    cn = new Conexion();
	    cn.Open();
	    boolean existe = false;

	    try {
	        String query = "SELECT * FROM banco.clientes WHERE correo = '"+correo+"'";
	        
	        if (cn.query(query).next()) {  // Si hay un resultado
	            existe = true;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al verificar correo: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return existe;
	}

	public boolean verificarCuit(String cuit) {
	    cn = new Conexion();
	    cn.Open();
	    boolean existe = false;

	    try {
	        String query = "SELECT * FROM banco.clientes WHERE cuil = '"+cuit+"'";
	        
	        if (cn.query(query).next()) {  // Si hay un resultado
	            existe = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return existe;
	}
	@Override
    public Clientes obtenerClienteConCuentasPorIdUsuario(String nombreUsu) {
        Clientes cliente = null;
        cn = new Conexion();
        cn.Open();
        String sql = "SELECT dni, cuil, nombre, apellido, sexo, nacionalidad, "
                   + "fechaNacimiento, direccion, l.nombreLocalidad AS localidad, correo, "
                   + "telefono, idUsuario_C, u.nombreUsuario AS nombreUsuario, c.contraseña AS contraseña, "
                   + "(SELECT COUNT(*) FROM Cuentas WHERE dni_Cu = dni AND estado = 1) AS cantidadCuentas "
                   + "FROM Clientes c "
                   + "INNER JOIN Localidades l ON idLocalidad_C = idLocalidad_L "
                   + "INNER JOIN Usuarios u ON idUsuario_C = idUsuario "
                   + "WHERE c.estado = 1 AND nombreUsuario like '"+nombreUsu+"%'";
	    try{ 
			 ResultSet rs= cn.query(sql);
			 while(rs.next()){ 
				 cliente = new Clientes();
				 cliente.setDni(rs.getString("dni"));
				 cliente.setCuil(rs.getString("cuil"));
				 cliente.setNombre(rs.getString("nombre"));
				 cliente.setApellido(rs.getString("apellido"));
				 cliente.setSexo(rs.getString("sexo"));
				 cliente.setNacionalidad(rs.getString("nacionalidad"));
				 Date fecha = rs.getDate("fechaNacimiento");
				 cliente.setFechaNacimiento(fecha.toLocalDate());
				 cliente.setDireccion(rs.getString("direccion"));
				 cliente.setLocalidad(rs.getString("localidad"));
				 cliente.setCorreo(rs.getString("correo"));
				 cliente.setTelefono(rs.getString("telefono"));
				 Usuarios usu = new Usuarios();
				 usu.setIdUsuario(rs.getInt("idUsuario_C"));
				 usu.setContraseña(rs.getString("contraseña"));
				 cliente.setUsuario(usu);
				 cliente.setIDlocalidad(rs.getString("idLocalidad_L"));
			 }
 
		 }
		 catch(Exception e) { 
			 e.printStackTrace();
		 }finally{ 
			 cn.close();
		}
		 return cliente; 
	}

	@Override
	public int contarCuentasPorNombreU(String nombreUsu) {
	    cn = new Conexion();
	    cn.Open();
	    int cantidadCuentas = 0;
	    String consulta = "SELECT COUNT(*) AS cantidad FROM Cuentas cu " +
	                      "INNER JOIN Clientes c ON c.dni = cu.dni_Cu " +
	                      "INNER JOIN Usuarios u ON c.idUsuario_C = u.idUsuario " +
	                      "WHERE cu.estado = 1 AND u.nombreUsuario LIKE ?";
	    try {
	        PreparedStatement stmt = cn.connection.prepareStatement(consulta);
	        stmt.setString(1, nombreUsu + "%"); // Utiliza el parámetro con el LIKE
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            cantidadCuentas = rs.getInt("cantidad");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al contar cuentas: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }
	    return cantidadCuentas;
	}
}
