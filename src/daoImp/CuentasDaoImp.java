package daoImp;

import dao.CuentaDao;
import entidades.Cuentas;
import entidades.Clientes;
import entidades.TipoCuentas;
import entidades.Usuarios;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CuentasDaoImp implements CuentaDao {
	private Conexion cn;
	
	public boolean borrar(int nroCuenta) {
		boolean estado=true;
		cn= new Conexion();
		cn.Open();
		String query = "UPDATE Cuentas SET estado=0 WHERE nroCuenta=" + nroCuenta;
		System.out.println(query);
		try {
			estado=cn.execute(query);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		return estado;
	}
	
	public List<Cuentas> obtenerTodos(){
		cn= new Conexion();
		cn.Open();
		List<Cuentas> list = new ArrayList<Cuentas>();
		try
		{
			ResultSet rs= cn.query("SELECT c.nroCuenta , c.dni_Cu, c.fechaCreacion, tc.nombreCuenta, c.cbu, c.saldo,tc.idTipoCuenta FROM Cuentas c INNER JOIN tiposdecuentas tc ON tc.idTipoCuenta = c.idTipoCuenta_Cu WHERE c.estado = 1");
			while(rs.next()) {
				Cuentas cuenta = new Cuentas();
				cuenta.setNroCuenta(rs.getInt("nroCuenta"));
				cuenta.setDni(rs.getString("dni_Cu"));
				Date fecha = rs.getDate("fechaCreacion");
				cuenta.setLocalDate(fecha.toLocalDate());
				
				TipoCuentas tc = new TipoCuentas();
				tc.setNombreCuenta(rs.getString("nombreCuenta"));
				tc.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
				cuenta.setTipoDeCuenta(tc);
				cuenta.setCbu(rs.getString("cbu"));
				cuenta.setSaldo(rs.getFloat("saldo"));
				
				list.add(cuenta);
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
	
	public boolean agregarCuenta(Cuentas cuenta) {
	    cn = new Conexion();
	    cn.Open();
	    boolean agregar = false;
	    
	    String consultaInsert = "INSERT INTO cuentas(nroCuenta, dni_Cu, fechaCreacion, idTipoCuenta_Cu, cbu, saldo, estado) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try {

	        PreparedStatement stmt = cn.connection.prepareStatement(consultaInsert);
	        stmt.setInt(1, cuenta.getNroCuenta());
	        stmt.setString(2, cuenta.getDni());
	        stmt.setDate(3, Date.valueOf(cuenta.getFechaCreacion()));
	        
	        stmt.setInt(4, cuenta.getTipoDeCuenta().getIdTipoCuenta());
	        
	        stmt.setString(5, cuenta.getCbu());
	        stmt.setFloat(6, cuenta.getSaldo());
	        stmt.setBoolean(7,cuenta.getEstado());

	        int filasInsertadas = stmt.executeUpdate();
	        agregar = filasInsertadas > 0;

	    } catch (SQLException e) {
	        System.out.println("Error de SQL: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return agregar;
	}
	
	public boolean modificarCuenta(Cuentas cu) {
		cn = new Conexion();
		cn.Open();
		boolean estado = false;
		String fechaCreacionStr = cu.getFechaCreacion().toString(); 
		String consulta = "UPDATE Cuentas SET dni_Cu = '" + cu.getDni() +
		             "', fechaCreacion  = '" + fechaCreacionStr +
		             "', idTipoCuenta_Cu = '" + cu.getTipoDeCuenta().getIdTipoCuenta() +
		             "', cbu  = '" + cu.getCbu()+
		             "', saldo  = '" + cu.getSaldo() +
		             "' WHERE nroCuenta = '" + cu.getNroCuenta() + "'";
		try{
			estado=cn.execute(consulta);
		 }
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			cn.close();
		}
		return estado;
	}

	public List<Cuentas> listarCuentasPorDni(String dni) {
		cn = new Conexion();
		cn.Open();
		List<Cuentas> list = new ArrayList<Cuentas>();
		String consulta = "SELECT nroCuenta,dni_Cu,fechaCreacion,tiposdecuentas.nombreCuenta AS tipoCuenta,idTipoCuenta,cbu,saldo FROM Cuentas INNER JOIN tiposDeCuentas ON Cuentas.idTipoCuenta_Cu = tiposdecuentas.idTipoCuenta WHERE estado=1 and dni_Cu like '"+dni+"%'";
		 
		try{
			ResultSet rs= cn.query(consulta);
			while(rs.next())
			{
				Cuentas cuenta = new Cuentas();
				cuenta.setNroCuenta(rs.getInt("nroCuenta"));
				cuenta.setDni(rs.getString("dni_Cu"));
				TipoCuentas tipoCuenta = new TipoCuentas();
	            tipoCuenta.setNombreCuenta(rs.getString("tipoCuenta"));
	            tipoCuenta.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
	            cuenta.setTipoDeCuenta(tipoCuenta);
				Date fecha = rs.getDate("fechaCreacion");
				cuenta.setLocalDate(fecha.toLocalDate());
				cuenta.setCbu(rs.getString("cbu"));
				cuenta.setSaldo(rs.getFloat("saldo"));
				list.add(cuenta);
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


	public List<Cuentas> listarCuentasPorTipoCuenta(int TipoCuenta){
		cn = new Conexion();
		cn.Open();
		 List<Cuentas> list = new ArrayList<Cuentas>();
		 String consulta = "SELECT nroCuenta,dni_Cu,fechaCreacion,tiposdecuentas.nombreCuenta AS tipoCuenta,idTipoCuenta,cbu,saldo FROM Cuentas INNER JOIN tiposDeCuentas ON Cuentas.idTipoCuenta_Cu = tiposdecuentas.idTipoCuenta WHERE estado=1 and Cuentas.idTipoCuenta_Cu = '"+TipoCuenta+"'";
		 try
		 {
			 ResultSet rs= cn.query(consulta);
			 while(rs.next())
			 {
				 Cuentas cuenta = new Cuentas();
				 cuenta.setNroCuenta(rs.getInt("nroCuenta"));
				 cuenta.setDni(rs.getString("dni_Cu"));
				Date fecha = rs.getDate("fechaCreacion");
				cuenta.setLocalDate(fecha.toLocalDate());
				 
				 TipoCuentas tipoCuenta = new TipoCuentas();
		          tipoCuenta.setNombreCuenta(rs.getString("tipoCuenta"));
		          tipoCuenta.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
		         cuenta.setTipoDeCuenta(tipoCuenta);
				 
				 cuenta.setCbu(rs.getString("cbu"));
				 cuenta.setSaldo(rs.getFloat("saldo"));
				 list.add(cuenta);
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
	
	public int contarCuentasPorDni(String dni) {
	    cn = new Conexion();
	    cn.Open();
	    int cantidadCuentas = 0;
	    String consulta = "SELECT COUNT(*) AS cantidad FROM Cuentas WHERE estado = 1 AND dni_Cu = ?";
	    
	    try {
	        PreparedStatement stmt = cn.connection.prepareStatement(consulta);
	        stmt.setString(1, dni);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            cantidadCuentas = rs.getInt("cantidad");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error de SQL: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }
	    
	    return cantidadCuentas;
	}

	
	public List<Integer> obtenerNumerosDeCuentaPorDni(String dni) {
	    List<Integer> numerosDeCuenta = new ArrayList<>();
	    cn = new Conexion();
	    cn.Open();
	    
	    try {
	        String consulta = "SELECT c.nroCuenta FROM Cuentas c INNER JOIN Clientes cl ON c.dni_Cu = cl.dni WHERE cl.dni = ?";
	        PreparedStatement stmt = cn.connection.prepareStatement(consulta);
	        stmt.setString(1, dni);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            numerosDeCuenta.add(rs.getInt("nroCuenta"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }
	    
	    return numerosDeCuenta;
	}
	
	public Cuentas obtenerCuentaPorNumero(int nroCuenta) {
	    cn = new Conexion();
	    cn.Open();
	    Cuentas cuenta = null;
	    String consulta = "SELECT c.nroCuenta, c.dni_Cu, c.fechaCreacion, tc.nombreCuenta, c.cbu, c.saldo, c.estado, tc.idTipoCuenta FROM Cuentas c INNER JOIN tiposdecuentas tc ON c.idTipoCuenta_Cu = tc.idTipoCuenta WHERE c.nroCuenta = ?";
	    try {
	        PreparedStatement stmt = cn.connection.prepareStatement(consulta);
	        stmt.setInt(1, nroCuenta);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            cuenta = new Cuentas();
	            cuenta.setNroCuenta(rs.getInt("nroCuenta"));
	            cuenta.setDni(rs.getString("dni_Cu"));
	            cuenta.setLocalDate(rs.getDate("fechaCreacion").toLocalDate());

	            TipoCuentas tipoCuenta = new TipoCuentas();
	            tipoCuenta.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
	            tipoCuenta.setNombreCuenta(rs.getString("nombreCuenta"));
	            cuenta.setTipoDeCuenta(tipoCuenta);

	            cuenta.setCbu(rs.getString("cbu"));
	            cuenta.setSaldo(rs.getFloat("saldo"));
	            cuenta.setEstado(rs.getBoolean("estado"));
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }
	    return cuenta;
	}
	
	public boolean sumarSaldo(int nroCuenta, float monto) {
	    cn = new Conexion();
	    cn.Open();
	    boolean actualizado = false;
	    String consulta = "UPDATE Cuentas SET saldo = saldo + ? WHERE nroCuenta = ?";

	    try {
	        PreparedStatement stmt = cn.connection.prepareStatement(consulta);
	        stmt.setFloat(1, monto); 
	        stmt.setInt(2, nroCuenta); 

	        int filasAfectadas = stmt.executeUpdate();
	        actualizado = filasAfectadas > 0; 
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar el saldo: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return actualizado;
	}

	@Override
	public List<Cuentas> listarCuentaPorUsuario(Usuarios Usua) {
		List<Cuentas> ListaPorUsuario= new ArrayList<>();
		cn = new  Conexion();
		cn.Open();
		
				
		String consulta = "select nroCuenta, dni_Cu,fechaCreacion,nombreCuenta, cbu,saldo from cuentas inner join clientes on clientes.dni = cuentas.dni_Cu inner join usuarios on clientes.idUsuario_C = usuarios.idUsuario inner join tiposdecuentas on tiposdecuentas.idTipoCuenta = cuentas.idTipoCuenta_Cu \r\n" + 
				"where cuentas.estado=1 and nombreUsuario ='"+ Usua.getNombreUsuario() +"'";
		try {
			
			ResultSet rt = cn.query(consulta);
			while(rt.next()) {
				Cuentas cuen = new Cuentas();
				TipoCuentas Tipo = new TipoCuentas();
				Tipo.setNombreCuenta(rt.getString("nombreCuenta"));
				cuen.setCbu(rt.getString("cbu"));
				cuen.setDni(rt.getString("dni_Cu"));
				cuen.setNroCuenta(rt.getInt("nroCuenta"));
				cuen.setTipoDeCuenta(Tipo);
				cuen.setSaldo(rt.getFloat("saldo"));
				ListaPorUsuario.add(cuen);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			cn.close();
		}
				
		
		return ListaPorUsuario;
	}
	
	public Float obtenerSaldo(int nroCuenta) {
		cn = new  Conexion();
		cn.Open();
	    Float saldo = null;
	    String consulta = "SELECT saldo FROM Cuentas WHERE nroCuenta = ?";

	    try {
	    	
	        PreparedStatement stmt = cn.connection.prepareStatement(consulta);
	        stmt.setInt(1, nroCuenta);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            saldo = rs.getFloat("saldo");
	        } 
	        
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return saldo;
	}

	
	public boolean restarSaldo(int nroCuenta, float monto) {
		cn = new Conexion();
	    cn.Open();
	    boolean actualizado = false;
	    String consulta = "UPDATE Cuentas SET saldo = saldo - ? WHERE nroCuenta = ?";

	    try {
	        PreparedStatement stmt = cn.connection.prepareStatement(consulta);
	        stmt.setFloat(1, monto); 
	        stmt.setInt(2, nroCuenta); 

	        int filasAfectadas = stmt.executeUpdate();
	        actualizado = filasAfectadas > 0; 
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar el saldo: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return actualizado;
	}

}
