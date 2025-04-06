package negocioImp;

import java.util.ArrayList;


import daoImp.LocalidadesDaoImp;
import entidades.Localidades;
import negocio.LocalidadNeg;

public class LocalidadNegImp implements LocalidadNeg {
	LocalidadesDaoImp lDao = new LocalidadesDaoImp();

		@Override
		public ArrayList<Localidades> cargarDDLLocalidades() {
			ArrayList<Localidades> ddlLocalidades = lDao.Obtenerlocalidades();
			return ddlLocalidades;
		}

		@Override
		public ArrayList<Localidades> cargarDDLLocalidadesPorProvincia(String Provincia) {
			ArrayList<Localidades> ddlLocalidades = lDao.ObtenerlocalidadesPorProvincia(Provincia);
			return ddlLocalidades;
		}
		

}
