package negocioImp;

import dao.CuentaDao;
import daoImp.CuentasDaoImp;
import entidades.Cuentas;
import entidades.Usuarios;
import negocio.CuentasNeg;
import java.util.ArrayList;
import java.util.List;

public class CuentasNegImp implements CuentasNeg{
	
	private CuentaDao cDao = (CuentaDao) new CuentasDaoImp();
	
	public CuentasNegImp() {
		
	}
	
	public boolean borrarCuenta(int nroCuenta) {
		return cDao.borrar(nroCuenta);
	}
	
	public ArrayList<Cuentas> listarCuentas(){
		return (ArrayList<Cuentas>) cDao.obtenerTodos();
	}
	
	public boolean agregarCuentas(Cuentas cuenta) {
		return cDao.agregarCuenta(cuenta);
	}
	
	@Override
	public boolean modificar(Cuentas cu) {
		return cDao.modificarCuenta(cu);
	}
	
	@Override
	public ArrayList<Cuentas> listarCuentasPorDni(String dni){
		return (ArrayList<Cuentas>) cDao.listarCuentasPorDni(dni);
	}

	@Override
	public ArrayList<Cuentas> listarCuentasPorTipo(int TipoCuenta){
		return (ArrayList<Cuentas>) cDao.listarCuentasPorTipoCuenta(TipoCuenta);
	}
	
	public int cantidadCuentas(String dni) {
		return cDao.contarCuentasPorDni(dni);
	}
	
	public List<Integer> obtenerNumerosDeCuentaPorDniNeg(String dni){
		return (List<Integer>) cDao.obtenerNumerosDeCuentaPorDni(dni);
	}
	
	public Cuentas obtenerCuentaPorNumero(int nroCuenta) {
		return cDao.obtenerCuentaPorNumero(nroCuenta);
	}
	
	public boolean sumarSaldo(int nroCuenta, float monto) {
		return cDao.sumarSaldo(nroCuenta, monto);
	}

	@Override
	public List<Cuentas> listarCuentaPorUsuario(Usuarios Usua) {
		 return cDao.listarCuentaPorUsuario(Usua);
	}
	
	public Float obtenerSaldo(int nroCuenta) {
		return cDao.obtenerSaldo(nroCuenta);
	}

	public boolean restarSaldo(int nroCuenta, float monto) {
		return cDao.restarSaldo(nroCuenta, monto);
	}
}

