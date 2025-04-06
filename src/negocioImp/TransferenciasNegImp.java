package negocioImp;

import daoImp.TransferenciasDaoImp;
import entidades.Clientes;
import entidades.Cuentas;
import entidades.Transferencias;
import negocio.TransferenciaNeg;

public class TransferenciasNegImp implements TransferenciaNeg {

	private TransferenciasDaoImp tDao = new TransferenciasDaoImp();




	@Override
	public boolean Transferenciacuenta(Transferencias Trans) {
		// TODO Auto-generated method stub
		return tDao.TransMovimiento(Trans);
	}


	@Override
	public Cuentas BuscarCuentaPorCBU(String CBU) {
	return tDao.BuscarCuentaPorCBU(CBU);
	}




	@Override
	public Clientes BuscarClientePorCBU(String CBU) {
		return tDao.BuscarClientePorCBU(CBU);
	}


}
