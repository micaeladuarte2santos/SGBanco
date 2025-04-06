package daoImp;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

import entidades.Cuotas;
import dao.CuotasDao;

public class CuotasDaoImp implements CuotasDao{
	private Conexion cn;
	
	public boolean agregarCuota(Cuotas c) {
		cn = new Conexion();
	    cn.Open();
	    boolean agregar = false;

	    String consultaInsert = "INSERT INTO Cuotas(idPrestamo,numeroCuota,fechaVencimiento,monto,estado) VALUES (?, ?, ?, ?, ?)";

	    try {
	        
	        PreparedStatement stmt = cn.connection.prepareStatement(consultaInsert);
	        stmt.setInt(1, c.getIdPrestamo());
	        stmt.setInt(2, c.getNumeroCuota());
	        stmt.setDate(3, Date.valueOf(c.getFechaVencimientoCuota()));
	        stmt.setDouble(4, c.getMonto());
	        stmt.setString(5, c.getEstado());

	        // Ejecutar la inserción
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

	public List<Cuotas> listarCuotasPorDni(String dni) {
		cn = new Conexion();
		cn.Open();
		List<Cuotas> list = new ArrayList<Cuotas>();
		String consulta = "select cuotas.idCuota as idCuota, cuotas.idPrestamo as idPrestamo, cuotas.numeroCuota as numeroCuota, cuotas.fechaVencimiento as fechaVencimiento, cuotas.monto as monto from cuotas inner join prestamos on cuotas.idPrestamo = prestamos.idPrestamo where prestamos.dni_Pr = '"+ dni +"' and prestamos.estado = 'aprobado' and cuotas.estado = 'pendiente'";
		try {
			ResultSet rs = cn.query(consulta);
			while(rs.next()) {
				Cuotas cuotas = new Cuotas();
				cuotas.setIdCuota(rs.getInt("idCuota"));
				cuotas.setIdPrestamo(rs.getInt("idPrestamo"));
				cuotas.setNumeroCuota(rs.getInt("numeroCuota"));
				Date fecha = rs.getDate("fechaVencimiento");
				cuotas.setFechaVencimientoCuota(fecha.toLocalDate());
				cuotas.setMonto(rs.getDouble("monto"));
				list.add(cuotas);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		return list;
	}

	public boolean pagarCuota(int idCuota) {
		boolean estado=true;
		cn= new Conexion();
		cn.Open();
		String query = "UPDATE Cuotas SET estado='cobrado' WHERE idCuota='"+ idCuota +"'";
		System.out.println(query);
		try {
			estado=cn.execute(query);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		return estado;
	}

	public boolean pagarCuota(int idCuota, LocalDate fechaPago) {
		boolean estado=true;
		cn= new Conexion();
		cn.Open();
		String query = "UPDATE Cuotas SET estado='cobrado', fechaPago= '"+ fechaPago + "' WHERE idCuota='"+ idCuota +"'";
		System.out.println(query);
		try {
			estado=cn.execute(query);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		return estado;
	}

	public List<Cuotas> listarCuotasPorPrestamo(int idPrestamo, String dni) {
		cn = new Conexion();
		cn.Open();
		List<Cuotas> list = new ArrayList<Cuotas>();
		String consulta = "select cuotas.idCuota as idCuota, cuotas.idPrestamo as idPrestamo, cuotas.numeroCuota as numeroCuota, cuotas.fechaVencimiento as fechaVencimiento, cuotas.monto as monto from cuotas inner join prestamos on cuotas.idPrestamo = prestamos.idPrestamo where prestamos.idPrestamo = '"+ idPrestamo +"' and prestamos.estado = 'aprobado' and cuotas.estado = 'pendiente' and prestamos.dni_Pr = '"+ dni +"'";
		try {
			ResultSet rs = cn.query(consulta);
			while(rs.next()) {
				Cuotas cuotas = new Cuotas();
				cuotas.setIdCuota(rs.getInt("idCuota"));
				cuotas.setIdPrestamo(rs.getInt("idPrestamo"));
				cuotas.setNumeroCuota(rs.getInt("numeroCuota"));
				Date fecha = rs.getDate("fechaVencimiento");
				cuotas.setFechaVencimientoCuota(fecha.toLocalDate());
				cuotas.setMonto(rs.getDouble("monto"));
				list.add(cuotas);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		return list;
	}
}
