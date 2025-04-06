package negocio;

import entidades.Clientes;
import entidades.Cuentas;
import entidades.Transferencias;

public interface TransferenciaNeg {
	public boolean Transferenciacuenta(Transferencias Trans);
	public Cuentas BuscarCuentaPorCBU(String CBU);
	public Clientes BuscarClientePorCBU(String CBU);
}
