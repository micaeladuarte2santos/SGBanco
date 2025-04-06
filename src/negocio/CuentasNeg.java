package negocio;

import entidades.Cuentas;
import entidades.Usuarios;

import java.util.ArrayList;
import java.util.List;

public interface CuentasNeg {
	public boolean borrarCuenta(int nroCuenta);
	public ArrayList<Cuentas> listarCuentas();
	public boolean agregarCuentas(Cuentas cuenta);
	public ArrayList<Cuentas> listarCuentasPorDni(String dni);
	public ArrayList<Cuentas> listarCuentasPorTipo(int TipoCuenta);
	public boolean modificar(Cuentas cu);
	public int cantidadCuentas(String dni);
	public List<Integer> obtenerNumerosDeCuentaPorDniNeg(String dni);
	public Cuentas obtenerCuentaPorNumero(int nroCuenta);
	public boolean sumarSaldo(int nroCuenta, float monto);
	public List<Cuentas> listarCuentaPorUsuario(Usuarios Usua);
	public Float obtenerSaldo(int nroCuenta);
	public boolean restarSaldo(int nroCuenta, float monto);
}
