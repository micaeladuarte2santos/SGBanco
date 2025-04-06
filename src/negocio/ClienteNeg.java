package negocio;

import java.util.ArrayList;
import entidades.Clientes;

public interface ClienteNeg {
	public boolean validarCliente(String cli); 
	public boolean borrarCliente(String dni);
	public boolean agregarCliente(Clientes cli);
	public ArrayList<Clientes> listarClientes();
	public ArrayList<Clientes> listarClientesPorDni(String dni);
	public ArrayList<Clientes> listarClientesPorProv(String Prov);
	public boolean modificar(Clientes cli);
	public Clientes obtenerClienteConCuentasPorIdUsuario(String nombreUsu);
	public int contarCuentasPorNombreU(String nombreUsu);
	public boolean verificarCorreo(String correo);
	public boolean verificarCuit(String cuit); 
}

