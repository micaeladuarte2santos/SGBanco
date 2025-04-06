package negocioImp;

import java.util.ArrayList;

import daoImp.ProvinciaDaoImp;
import entidades.Provincias;
import negocio.ProvinciaNeg;

public class ProvinciaNegImp implements ProvinciaNeg{
		
	ProvinciaDaoImp pDao =  new ProvinciaDaoImp();

	@Override
	public ArrayList<Provincias> cargarDDlProvincia() {
		ArrayList<Provincias> ddlProvincias = pDao.obtenerProvincias();
		return ddlProvincias;
	}


}
