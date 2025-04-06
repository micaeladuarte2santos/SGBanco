package dao;

import java.util.List;
import entidades.Clientes;

public interface ClienteDao {
	public boolean existeCliente(String cli);
	public boolean borrar(String dni);
	public boolean agregarCliente(Clientes cli);
	public List<Clientes> obtenerTodos();
	public List<Clientes> listarClientesPorDni(String dni);
	public List<Clientes> listarClientesPorProvincia(String Provincia);
	public boolean modificarCliente(Clientes cli);
	public Clientes obtenerClienteConCuentasPorIdUsuario(String nombreUsu);
	public int contarCuentasPorNombreU(String nombreUsu);
	public boolean verificarCorreo(String correo); 
	public boolean verificarCuit(String cuit); 
}
