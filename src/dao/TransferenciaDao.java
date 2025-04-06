package dao;

import entidades.Clientes;
import entidades.Cuentas;
import entidades.Transferencias;

public interface TransferenciaDao {
	
	public boolean TransMovimiento(Transferencias Trans);
	public Cuentas BuscarCuentaPorCBU(String CBU);
	public Clientes BuscarClientePorCBU(String CBU);
	
}
