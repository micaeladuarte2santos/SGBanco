package daoImp;

import entidades.Movimientos;
import entidades.TipoMovimiento;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.MovimientosDao;

public class MovimientosDaoImp implements MovimientosDao{
	private Conexion cn;
	
	public List<Movimientos> obtenerTodos(String dni) {
		cn = new Conexion();
		cn.Open();
		 List<Movimientos> list = new ArrayList<Movimientos>();
		 try
		 {
			 String consulta="SELECT m.idMovimiento, m.nroCuenta_M, m.fecha, m.detalle, m.importe, tm.nombre AS tipoMovimiento FROM ((Movimientos m INNER JOIN Cuentas c ON m.nroCuenta_M = c.nroCuenta) INNER JOIN Clientes cl ON c.dni_Cu = cl.dni) INNER JOIN tiposDeMovimientos tm ON m.idTipoMovimiento_M = tm.idTipoMovimiento WHERE  cl.dni = ?";
			 PreparedStatement stmt = cn.connection.prepareStatement(consulta);
		     stmt.setString(1, dni);
		     ResultSet rs = stmt.executeQuery();
			 while(rs.next())
			 {
				 Movimientos mov = new Movimientos();
				 mov.setIdMovimiento(rs.getInt("idMovimiento"));
				 mov.setNroCuenta_M(rs.getInt("nroCuenta_M"));
				 Date fecha = rs.getDate("fecha");
				 mov.setFecha(fecha.toLocalDate());
				 mov.setDetalle(rs.getString("detalle"));
				 mov.setImporte(rs.getDouble("importe"));
				 
				 TipoMovimiento tmov = new TipoMovimiento();
				 tmov.setNombre(rs.getString("tipoMovimiento"));
		         
				 mov.setTipoDeMovimiento(tmov);
				 
				 list.add(mov);
			 }
			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 cn.close();
		 }
		 return list;
	}

	@Override
	public Map<String, Integer> porcentajeTiposMovimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
		 cn = new Conexion();
		    cn.Open();
		    
		    Map<String, Integer> porcentajeTipoDeMovimientos = new HashMap<>();

		    String fechaInicioStr = fechaInicio.toString();
		    String fechaFinStr = fechaFin.toString();

		    String query = 
		        "SELECT " +
		        "  CASE " +
		        "    WHEN idTipoMovimiento_M = 5 THEN 'Pago de Servicio' " +
		        "    WHEN idTipoMovimiento_M = 4 THEN 'Transferencia Salida' " +
		        "    WHEN idTipoMovimiento_M = 3 THEN 'Transferencia Ingresada' " +
		        "    WHEN idTipoMovimiento_M = 2 THEN 'Retiro' " +
		        "    WHEN idTipoMovimiento_M = 1 THEN 'Deposito' " +
		        "  END AS Rango, " +
		        "  (COUNT(*) * 100.0 / " +
		        "   (SELECT COUNT(*) FROM movimientos WHERE fecha BETWEEN '" + fechaInicioStr + "' AND '" + fechaFinStr + "')) AS Porcentaje " +
		        "FROM movimientos " +
		        "WHERE fecha BETWEEN '" + fechaInicioStr + "' AND '" + fechaFinStr + "' " +
		        "GROUP BY Rango";

		    System.out.println("Query: " + query);
		    
		    try {
		        PreparedStatement ps = cn.connection.prepareStatement(query);
		        ResultSet rs = ps.executeQuery();
		        
		        while (rs.next()) {
		            porcentajeTipoDeMovimientos.put(rs.getString("Rango"), rs.getInt("Porcentaje"));
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        cn.close(); 
		    }
		    
		    System.out.println("Datos cargados en el Map: " + porcentajeTipoDeMovimientos);
		    return porcentajeTipoDeMovimientos;
	
	}
	
	
	public boolean agregarMovimiento(Movimientos movimiento) {
	    cn = new Conexion(); 
	    cn.Open();
	    boolean agregar = false;

	    String consultaInsert = "INSERT INTO movimientos (nroCuenta_M, fecha, detalle, importe, idTipoMovimiento_M) "
	            + "VALUES (?, ?, ?, ?, ?)";

	    try {
	        PreparedStatement stmt = cn.connection.prepareStatement(consultaInsert);
	        stmt.setInt(1, movimiento.getNroCuenta_M()); 
	        stmt.setDate(2, Date.valueOf(movimiento.getFecha())); 
	        stmt.setString(3, movimiento.getDetalle()); 
	        stmt.setDouble(4, movimiento.getImporte()); 
	        stmt.setInt(5, movimiento.getTipoDeMovimiento().getIdTipoMovimiento()); 

	        int filasInsertadas = stmt.executeUpdate();
	        agregar = filasInsertadas > 0; 

	    } catch (Exception e) {
	        System.out.println( e.getMessage());
	        e.printStackTrace();
	    } finally {
	        cn.close(); 
	    }

	    return agregar; 
	}

	public List<Movimientos> obtenerMovimientosPorCuenta(int nroCuenta) {
	    cn = new Conexion();
	    cn.Open();
	    List<Movimientos> list = new ArrayList<>();

	    try {
	        String consulta = "SELECT m.idMovimiento, m.nroCuenta_M, m.fecha, m.detalle, m.importe, tm.nombre AS tipoMovimiento FROM Movimientos m INNER JOIN tiposDeMovimientos tm ON m.idTipoMovimiento_M = tm.idTipoMovimiento WHERE m.nroCuenta_M = ?";
	        PreparedStatement stmt = cn.connection.prepareStatement(consulta);
	        stmt.setInt(1, nroCuenta);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Movimientos mov = new Movimientos();
	            mov.setIdMovimiento(rs.getInt("idMovimiento"));
	            mov.setNroCuenta_M(rs.getInt("nroCuenta_M"));
	            Date fecha = rs.getDate("fecha");
	            mov.setFecha(fecha.toLocalDate());
	            mov.setDetalle(rs.getString("detalle"));
	            mov.setImporte(rs.getDouble("importe"));

	            TipoMovimiento tmov = new TipoMovimiento();
	            tmov.setNombre(rs.getString("tipoMovimiento"));

	            mov.setTipoDeMovimiento(tmov);

	            list.add(mov);
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return list;
	}

	
}
