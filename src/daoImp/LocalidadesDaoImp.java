package daoImp;

import java.sql.ResultSet;

import java.util.ArrayList;

import dao.LocalidadDao;
import entidades.Localidades;


public class LocalidadesDaoImp implements LocalidadDao{


	@Override
	public ArrayList<Localidades> ObtenerlocalidadesPorProvincia(String idProvincia) {
	    ArrayList<Localidades> localidades = new ArrayList<>();
	    Conexion cn = new Conexion();
	    
	    try {
	        cn.Open();
	        String query = "SELECT nombreLocalidad, idLocalidad_L FROM Provincias p JOIN Localidades l ON p.idProvincia_P = l.idProvincia_L WHERE p.idProvincia_P = '" + idProvincia + "' " + 
	                "ORDER BY l.nombreLocalidad";

	        ResultSet rs = cn.query(query);

	        while (rs.next()) {
	            Localidades localidad = new Localidades();
	            localidad.setNombreLocalidad(rs.getString("nombreLocalidad"));
	            localidad.setIdLocalidad(rs.getString("idLocalidad_L"));
	            localidades.add(localidad);
	        }

	        rs.close(); 

	    } catch (Exception e) {
	        System.err.println("Error al cargar: " + e.getMessage());
	    } finally {
	        cn.close(); 
	    }

	    return localidades;
	}

	@Override
	public ArrayList<Localidades> Obtenerlocalidades() {
	    ArrayList<Localidades> localidades = new ArrayList<>();
	    Conexion cn= new Conexion();
	    try {
	        cn.Open();
	        ResultSet rs = cn.query("SELECT * FROM localidades");

	        while (rs.next()) {
	            Localidades localidad = new Localidades();
	            localidad.setNombreLocalidad(rs.getString("nombreLocalidad"));
	            localidad.setIdLocalidad(rs.getString("idLocalidad_L"));
	            localidades.add(localidad);
	        }

	        rs.close(); 

	    } catch (Exception e) {
	        System.err.println("Error al cargar provincias: " + e.getMessage());
	    } finally {
	        cn.close(); 
	    }
	
	    
	    return localidades;
	}






}
