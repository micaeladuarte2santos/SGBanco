package daoImp;
import dao.TipoDeMovimientosDao;
import entidades.TipoMovimiento;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

public class TipoDeMovimientosDaoImo implements TipoDeMovimientosDao{
	private Conexion cn;

	public int obtenerIdPorDescripcion(String nombre) {
		int id=-1;
	    cn = new Conexion();
		cn.Open();
		
		String consulta="SELECT idTipoMovimiento FROM tiposDeMovimientos WHERE nombre = ?" ;
	    try
	    {
	        PreparedStatement ps = cn.connection.prepareStatement(consulta);
	        ps.setString(1, nombre);
	        ResultSet rs = ps.executeQuery();
	           if (rs.next()) {
	               id = rs.getInt("idTipoMovimiento");
	           }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return id;
	}

}
