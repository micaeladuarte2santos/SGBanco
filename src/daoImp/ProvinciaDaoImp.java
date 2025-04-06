package daoImp;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.ProvinciaDao;
import entidades.Provincias;

public class ProvinciaDaoImp implements ProvinciaDao {
	
	Conexion cn;

	@Override
	public ArrayList<Provincias> obtenerProvincias() {

		    ArrayList<Provincias> provincias = new ArrayList<>();
		    Conexion cn= new Conexion();
		    try {
		        cn.Open();
		        ResultSet rs = cn.query("SELECT * FROM provincias");

		        while (rs.next()) {
		            Provincias provincia = new Provincias();
		            provincia.setIdProvincia(rs.getString("idProvincia_P"));
		            provincia.setNombreProvincia(rs.getString("nombreProvincia")); 
		            provincias.add(provincia);
		        }

		        rs.close(); 

		    } catch (Exception e) {
		        System.err.println("Error al cargar provincias: " + e.getMessage());
		    } finally {
		        cn.close(); 
		    }

		    return provincias;
		

	} 
	




}
