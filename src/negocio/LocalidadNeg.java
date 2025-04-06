package negocio;

import java.util.ArrayList;

import entidades.Localidades;

public interface LocalidadNeg {
		public ArrayList<Localidades> cargarDDLLocalidades();
		public ArrayList<Localidades> cargarDDLLocalidadesPorProvincia(String Provincia);
}
