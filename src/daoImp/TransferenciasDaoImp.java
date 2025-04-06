package daoImp;

import java.sql.ResultSet;

import dao.TransferenciaDao;
import entidades.Clientes;
import entidades.Cuentas;
import entidades.Transferencias;

public class TransferenciasDaoImp implements TransferenciaDao {
		
	private Conexion cn;
	@Override
	public boolean TransMovimiento(Transferencias trans) {
		cn = new Conexion();
		cn.Open();
		boolean estado=false;
		try {
		String consulta= "INSERT INTO banco.tranferencias (nroCuentaOrigen, CbuDestino, monto, fecha) VALUES "
				+ "("+ trans.getNroCuentaOrigen()+", '"+trans.getCbuDestino()+"', "+trans.getMonto()+", '"+trans.getFecha().toString()+"')";
		estado =cn.execute(consulta);
		UpdateCuentas(trans);
			
		}catch(Exception e){
			System.out.println("Error de SQL: " + e.getMessage());
		}finally {
			cn.close();
		}
		
		
		return estado;
	}
	
	private void UpdateCuentas(Transferencias Movimiento) {
		cn = new Conexion();
		cn.Open();
		try {
		String consulta="UPDATE Cuentas SET saldo = saldo - " + Movimiento.getMonto() + 
                " WHERE nroCuenta = " + Movimiento.getNroCuentaOrigen();
		String consulta2="UPDATE Cuentas SET saldo = saldo + " + Movimiento.getMonto() + 
                " WHERE cbu = '" + Movimiento.getCbuDestino() + "'";
		
		cn.execute(consulta);
		cn.execute(consulta2);	
		}catch(Exception e){
			System.out.println("Error de SQL: " + e.getMessage());
		}finally {
			cn.close();
		}
		
		
	}

	@Override
	public Clientes BuscarClientePorCBU(String CBU) {
		cn = new Conexion();
		cn.Open();
		String consulta="Select nombre, apellido, cbu from clientes inner join cuentas on clientes.dni = cuentas.dni_Cu\r\n" + 
				"where  cuentas.estado = 1 and cbu = '" + CBU +"'";
		Clientes cbuCliente = new Clientes();
		try {
			ResultSet rs= cn.query(consulta);
			while(rs.next())
			 {
				cbuCliente.setNombre(rs.getString("nombre"));
				cbuCliente.setApellido(rs.getString("apellido"));
			 }
			
		}catch(Exception e){
			e.getMessage();
		}finally {
			cn.close();
		}
		
		return cbuCliente;
	}

	@Override
	public Cuentas BuscarCuentaPorCBU(String CBU) {
		cn = new Conexion();
		cn.Open();
		String consulta="Select * from clientes inner join cuentas on clientes.dni = cuentas.dni_Cu\r\n" + 
				"where  cuentas.estado = 1 and cbu = '" + CBU +"'";
		Cuentas cbuCuenta = new Cuentas();
		try {
			ResultSet rs= cn.query(consulta);
			while(rs.next())
			 {
				cbuCuenta.setNroCuenta(rs.getInt("nroCuenta"));
			 }
			
		}catch(Exception e){
			e.getMessage();
		}finally {
			cn.close();
		}
		
		return cbuCuenta;
		
	}

	
	
}
