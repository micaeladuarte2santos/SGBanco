package dao;
import entidades.TipoCuentas;
import java.util.ArrayList;

public interface TipoCuentaDao {
	public ArrayList<TipoCuentas> obtenerTipoCuentas(); 
	public TipoCuentas obtenerTipoCuentaPorId(int idTipoCuenta);
}