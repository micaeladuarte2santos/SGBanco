package dao;

import entidades.Cuentas;
import entidades.Usuarios;

import java.util.List;

public interface CuentaDao {
	public boolean borrar(int nroCuenta);
	public List<Cuentas> obtenerTodos();
	public boolean agregarCuenta(Cuentas cu);
	public List<Cuentas> listarCuentasPorDni(String dni);
	public List<Cuentas> listarCuentasPorTipoCuenta(int TipoCuenta);
	public boolean modificarCuenta(Cuentas cu);
	public int contarCuentasPorDni(String dni);
	public List<Integer> obtenerNumerosDeCuentaPorDni(String dni);
	public Cuentas obtenerCuentaPorNumero(int nroCuenta);
	public List<Cuentas> listarCuentaPorUsuario(Usuarios Usua);
	public boolean sumarSaldo(int nroCuenta, float monto);
	public Float obtenerSaldo(int nroCuenta);
	public boolean restarSaldo (int nroCuenta, float monto);
}
