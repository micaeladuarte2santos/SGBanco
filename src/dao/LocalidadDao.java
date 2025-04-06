package dao;

import java.util.ArrayList;

import entidades.Localidades;

public interface LocalidadDao {
	public ArrayList<Localidades> Obtenerlocalidades();

	public ArrayList<Localidades> ObtenerlocalidadesPorProvincia(String idProvincia);
}
