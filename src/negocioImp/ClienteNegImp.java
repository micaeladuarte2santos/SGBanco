package negocioImp;

import java.util.ArrayList;

import dao.ClienteDao;
import daoImp.ClienteDaoImp;
import entidades.Clientes;
import negocio.ClienteNeg;

public class ClienteNegImp implements ClienteNeg{
	
	private ClienteDao cDao =(ClienteDao) new ClienteDaoImp();
	
	public ClienteNegImp() {}
	
	public boolean borrarCliente(String dni) {
		return cDao.borrar(dni);
	}
	
	@Override
	public boolean validarCliente(String cli) {
		return cDao.existeCliente(cli);
	}

	@Override
	public boolean agregarCliente(Clientes cli) {
		return cDao.agregarCliente(cli);
	}

	@Override
	public ArrayList<Clientes> listarClientes() {
		return (ArrayList<Clientes>) cDao.obtenerTodos();
	}

	@Override
	public boolean modificar(Clientes cli) {
		return cDao.modificarCliente(cli);
	}

	@Override
	public ArrayList<Clientes> listarClientesPorDni(String dni) {
		return (ArrayList<Clientes>) cDao.listarClientesPorDni(dni);
	}

	@Override
	public ArrayList<Clientes> listarClientesPorProv(String Prov) {
		return (ArrayList<Clientes>) cDao.listarClientesPorProvincia(Prov);
	}

	@Override
	public Clientes obtenerClienteConCuentasPorIdUsuario(String nombreUsu){
        return cDao.obtenerClienteConCuentasPorIdUsuario(nombreUsu);
    }

	public int contarCuentasPorNombreU(String nombreUsu){
		return cDao.contarCuentasPorNombreU(nombreUsu);
	}

	@Override
	public boolean verificarCorreo(String correo) {
		return cDao.verificarCorreo(correo);
	}

	@Override
	public boolean verificarCuit(String cuit) {
		return cDao.verificarCuit(cuit);
	}
	
}
