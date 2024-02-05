package agenda.persistencia;

import java.sql.Connection;
import java.util.Set;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import agenda.config.Config;
import agenda.modelo.Contacto;

public class ContactoDaoJDBCMala implements ContactoDao {
	
	private DataSource ds; // a un atributo de clase una referencia
	
	public ContactoDaoJDBCMala() {
		ds = Config.getDataSource();
	}
	/*implementar la intrerfaz
	 * añadir librerias JDBC
	 * 
	 */

	@Override
	public void insertar(Contacto c) {
	 try (Connection connect = ds.getConnection()){
		 String sql = "INSERT INTO VALUES"
		 		    + "(null,'" 
		 		    + c.getNombre() + "','"
		 		    + c.getApellidos() + "','"
		 		    + c.getApodo() + "','"
		 		    + c.getDom().getTipoVia() + "','"
		 		    + c.getDom().getVia() + "','"
		 		    + c.getDom().getNumero() + ","
		 		    + c.getDom().getPiso() + ","
		 		    + c.getDom().getPuerta() + "','"
		 		    + c.getDom().getCodigoPostal() + "','"
		 		    + c.getDom().getCodigoPostal() + "','"
		 		    + c.getDom().getCiudad() + "','"
		 		    + c.getDom().getProvincia() + "VALUES";
		
	} catch (Exception e) {
		// TODO: handle exception
	}
		
	}

	@Override
	public void actualizar(Contacto c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean eliminar(int idContacto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(Contacto c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Contacto buscar(int idContacto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contacto> buscar(String cadena) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contacto> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
