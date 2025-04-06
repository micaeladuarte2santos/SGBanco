package negocioImp;

import java.util.ArrayList;

import daoImp.TipoCuentaDaoImp;
import entidades.TipoCuentas;
import negocio.TipoCuentaNeg;

public class TipoCuentaNegImp implements TipoCuentaNeg{
	
	TipoCuentaDaoImp tDao =  new TipoCuentaDaoImp();

	@Override
	public ArrayList<TipoCuentas> cargarDDlTipoCuenta() {
		ArrayList<TipoCuentas> ddlTipoCuentas = tDao.obtenerTipoCuentas();
		return ddlTipoCuentas;
	}
	
	public TipoCuentas obtenerTipoCuentaPorId(int idTipoCuenta) {
        return tDao.obtenerTipoCuentaPorId(idTipoCuenta);
    }
}
