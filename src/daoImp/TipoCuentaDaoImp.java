package daoImp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.TipoCuentaDao;
import entidades.TipoCuentas;

public class TipoCuentaDaoImp implements TipoCuentaDao {
	
	Conexion cn;

	@Override
	public ArrayList<TipoCuentas> obtenerTipoCuentas() {

		    ArrayList<TipoCuentas> tipoCuenta = new ArrayList<>();
		    Conexion cn= new Conexion();
		    try {
		        cn.Open();
		        ResultSet rs = cn.query("SELECT * FROM tiposDeCuentas");

		        while (rs.next()) {
		        	TipoCuentas tipo = new TipoCuentas();
		            tipo.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
		            tipo.setNombreCuenta(rs.getString("nombreCuenta")); 
		            tipoCuenta.add(tipo);
		        }
		        rs.close(); 

		    } catch (Exception e) {
		        System.err.println("Error al cargar provincias: " + e.getMessage());
		    } finally {
		        cn.close(); 
		    }
		    return tipoCuenta;	
	} 
	
	public TipoCuentas obtenerTipoCuentaPorId(int idTipoCuenta) {
        TipoCuentas tipoCuenta = null;
        String sql = "SELECT * FROM tiposdecuentas WHERE idTipoCuenta = "+idTipoCuenta;
        
        try (PreparedStatement stmt = cn.connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                tipoCuenta = new TipoCuentas();
                tipoCuenta.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
                tipoCuenta.setNombreCuenta(rs.getString("nombreCuenta")); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoCuenta;
    }
	
}
