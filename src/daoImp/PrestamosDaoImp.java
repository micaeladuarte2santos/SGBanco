package daoImp;
import dao.PrestamosDao;
import entidades.Prestamos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;


public class PrestamosDaoImp implements PrestamosDao{
	private Conexion cn;
	
	public List<Prestamos> obtenerTodos() {
		cn = new Conexion();
		cn.Open();
		 List<Prestamos> list = new ArrayList<Prestamos>();
		 try
		 {
			 ResultSet rs= cn.query("SELECT idPrestamo,dni_Pr,nroCuenta_Pr,fechaPrestamo,importeConIntereses,importePedidoCliente,plazoPago,cantidadCuotas,montoPorMes,estado FROM Prestamos WHERE estado='pendiente'");
			 while(rs.next())
			 {
				 Prestamos p = new Prestamos();
				 p.setIdPrestamo(rs.getInt("idPrestamo"));
				 p.setDni(rs.getString("dni_Pr"));
				 p.setNroCuenta(rs.getInt("nroCuenta_Pr"));
				 Date fecha = rs.getDate("fechaPrestamo");
				 p.setFechaPrestamo(fecha.toLocalDate());
				 p.setImporteConIntereses(rs.getDouble("importeConIntereses"));
				 p.setImportePedidoPorCliente(rs.getDouble("importePedidoCliente"));
				 p.setPlazoPago(rs.getInt("plazoPago"));
				 p.setCantidadCuotas(rs.getInt("cantidadCuotas"));
				 p.setMontoPorMes(rs.getDouble("montoPorMes"));
				 p.setEstado(rs.getString("estado"));
				 
				 list.add(p);
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
	
	public boolean actualizarEstado(Prestamos prestamo) {
	    String sql = "UPDATE Prestamos SET estado = ? WHERE idPrestamo = ?";
	    cn = new Conexion(); // Instanciar la conexión
	    cn.Open(); // Abrir la conexión manualmente

	    boolean actualizado = false;

	    try {
	        PreparedStatement ps = (PreparedStatement) cn.connection.prepareStatement(sql);
	        ps.setString(1, prestamo.getEstado());
	        ps.setInt(2, prestamo.getIdPrestamo());

	        actualizado = ps.executeUpdate() > 0; // Verificar si se actualizó alguna fila
	    } catch (SQLException e) {
	        e.printStackTrace(); // Manejo de errores
	    } finally {
	        cn.close(); // Cerrar la conexión manualmente
	    }

	    return actualizado;
	}
	
	public Map<String, Integer> contarPrestamosPorMontosYFechas(LocalDate fechaInicio, LocalDate fechaFin){
		cn = new Conexion();
		cn.Open();
		
		Map<String, Integer> prestamosPorMontos = new HashMap<>();

		String fechaInicioStr = fechaInicio.toString();  //lo paso a formato 'YYYY-MM-DD' como en sql
		String fechaFinStr = fechaFin.toString();  

		
        String query = "SELECT CASE " +
                "WHEN importePedidoCliente BETWEEN 0 AND 10000 THEN '0 - 10K' " +
                "WHEN importePedidoCliente BETWEEN 10001 AND 50000 THEN '10K - 50K' " +
                "WHEN importePedidoCliente > 50000 THEN 'Más de 50K' " +
                "END AS rango, COUNT(*) AS cantidad " +
                "FROM prestamos WHERE fechaPrestamo BETWEEN '" + fechaInicioStr +"' AND '" + fechaFinStr +"'" +
                "GROUP BY rango";
        
        System.out.println("Query: " + query);
        try {
        	PreparedStatement ps = cn.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prestamosPorMontos.put(rs.getString("rango"), rs.getInt("cantidad"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Datos cargados en el Map: " + prestamosPorMontos);
        return prestamosPorMontos;
    }

	public ArrayList<Prestamos> listarPrestamosPorCuenta(int numCuenta) {
		cn = new Conexion();
		cn.Open();
		 ArrayList<Prestamos> list = new ArrayList<Prestamos>();
		 try
		 {
			 ResultSet rs= cn.query("SELECT idPrestamo,dni_Pr,nroCuenta_Pr,fechaPrestamo,importeConIntereses,importePedidoCliente,plazoPago,cantidadCuotas,montoPorMes,estado FROM Prestamos WHERE estado='pendiente' and nroCuenta_Pr like'"+ numCuenta +"%'");
			 while(rs.next())
			 {
				 Prestamos p = new Prestamos();
				 p.setIdPrestamo(rs.getInt("idPrestamo"));
				 p.setDni(rs.getString("dni_Pr"));
				 p.setNroCuenta(rs.getInt("nroCuenta_Pr"));
				 Date fecha = rs.getDate("fechaPrestamo");
				 p.setFechaPrestamo(fecha.toLocalDate());
				 p.setImporteConIntereses(rs.getDouble("importeConIntereses"));
				 p.setImportePedidoPorCliente(rs.getDouble("importePedidoCliente"));
				 p.setPlazoPago(rs.getInt("plazoPago"));
				 p.setCantidadCuotas(rs.getInt("cantidadCuotas"));
				 p.setMontoPorMes(rs.getDouble("montoPorMes"));
				 p.setEstado(rs.getString("estado"));
				 
				 list.add(p);
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

	public boolean agregarPrestamo(Prestamos prestamo) {
		cn = new Conexion();
	    cn.Open();
	    boolean agregar = false;
	    
	    String consultaInsert = "INSERT INTO prestamos(dni_Pr, nroCuenta_Pr, fechaPrestamo, importeConIntereses, importePedidoCliente, plazoPago, cantidadCuotas, montoPorMes, estado) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {

	        PreparedStatement stmt = cn.connection.prepareStatement(consultaInsert);
	        stmt.setString(1, prestamo.getDni());
	        stmt.setInt(2, prestamo.getNroCuenta());
	        stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
	        stmt.setDouble(4, prestamo.getImporteConIntereses());
	        stmt.setDouble(5, prestamo.getImportePedidoPorCliente());
	        stmt.setInt(6, prestamo.getPlazoPago());
	        stmt.setInt(7, prestamo.getCantidadCuotas());
	        stmt.setDouble(8, prestamo.getMontoPorMes());
	        stmt.setString(9, prestamo.getEstado());

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
}
