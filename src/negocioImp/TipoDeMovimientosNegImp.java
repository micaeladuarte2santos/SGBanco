package negocioImp;
import entidades.TipoMovimiento;
import dao.TipoDeMovimientosDao;
import daoImp.TipoDeMovimientosDaoImo;
import negocio.TipoDeMovimientoNeg;

public class TipoDeMovimientosNegImp implements TipoDeMovimientoNeg{
	private TipoDeMovimientosDao tDao = new TipoDeMovimientosDaoImo();
	
	public int obtenerIdPorDescripcion(String nombre) {
		return tDao.obtenerIdPorDescripcion(nombre);
	}
	
}
