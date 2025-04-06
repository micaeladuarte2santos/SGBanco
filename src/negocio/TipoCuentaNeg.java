package negocio;

import java.util.ArrayList;
import entidades.TipoCuentas;

public interface TipoCuentaNeg {
	public ArrayList<TipoCuentas> cargarDDlTipoCuenta();
	public TipoCuentas obtenerTipoCuentaPorId(int idTipoCuenta);
}
