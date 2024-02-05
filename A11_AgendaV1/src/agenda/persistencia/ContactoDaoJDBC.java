package agenda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javax.sql.DataSource;

import agenda.config.Config;
import agenda.exceptions.ContactoPersistenceExceptions;
import agenda.modelo.Contacto;

public class ContactoDaoJDBC implements ContactoDao{
	
	private DataSource ds;
	
	public ContactoDaoJDBC () {
		ds = Config.getDataSource();
	}

	@Override
	public void insertar(Contacto c) {
		//ahora trabajamos con statement ,o sentencias, precompildas
				
		
		//arma sentencia
		String insertContacto = "INSERT INTO contactos value(null,?,?,?,?,?,?,?,?,?,?,?)"; 
			
		try (Connection connet = ds.getConnection()) { //OBTENER DATOS 
			connet.setAutoCommit(false); 
			/*no estamos trabajando en modo autocommit. 
			* en este punto si no lanza un commit en la BD los datos no se van a guardar
			* los contacto se tienen que guardar con telefonos.
			*
			*/
			PreparedStatement ps = connet.prepareStatement(insertContacto);
			ps.setString(1, c.getNombre());
			ps.setString(2, c.getApellidos());
			ps.setString(3, c.getApodo());
			ps.setString(4, c.getDom().getTipoVia());
			ps.setString(5, c.getDom().getVia());
			ps.setInt(6, c.getDom().getNumero());
			ps.setInt(7, c.getDom().getPiso());
			ps.setString(8, c.getDom().getPuerta());
			ps.setString(9, c.getDom().getCodigoPostal());
			ps.setString(10, c.getDom().getCiudad());
			ps.setString(11, c.getDom().getProvincia());
			
			/*
			 * ps.executeUpdate(); ejecuta
			 * se obtiene el valor de filas afectadas, en este caso,
			 * se inserta la fila una a una, por lo que tiene que devolver 1
			 * si no devuelve 1, se ejecuta connet.rollback();
			 */ 
			int filas = ps.executeUpdate(); 
			
			if (filas == 1) {
				/*
				 * una vez se ha insertado un registro dentro de este if
				 * ejecutamos la select que me devuelve el ultimo id generado
				 * qu
				 */
				
				PreparedStatement psId = connet.prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet rs = psId.executeQuery();
				rs.next(); //para pasar el puntero al primer registro o al siguiente, si no hay next devuelve false. 
				int idGen = rs.getInt(1);
				
				String insertTelefonos = "INSERT INTO telefonos value(null,?,?)";
				PreparedStatement psTelefonos = connet.prepareStatement(insertTelefonos);
				
				int cantTel = 0;
				for(String telefonos : c.getTelefonos()) {
					psTelefonos.setInt(1, idGen);
					psTelefonos.setString(2, telefonos);
					
					cantTel += psTelefonos.executeUpdate();
				}
				
				String insertCorreos = "INSERT INTO correos value(null,?,?)";
				PreparedStatement psCorreos = connet.prepareStatement(insertCorreos);
				
				int cantCorreos = 0;
				for(String correos : c.getCorreos()) {
					psCorreos.setInt(1, idGen);
					psCorreos.setString(2, correos);
					
					cantCorreos += psCorreos.executeUpdate();
				}
				
				if (cantTel == c.getTelefonos().size() && cantCorreos == c.getCorreos().size()){
					connet.commit();
				} else {
					connet.rollback();
					throw new ContactoPersistenceExceptions(); 
				}
				
			} else {
				connet.rollback();
			}		
			
		} catch (SQLException e) {
			//HACER UN LOG CON FECHA, HORA, USUARIO, TIPO ERROR
			e.printStackTrace();
			//AVISAR A LOS DE ARRIBA QUE NO HE PODIDO REALIZAR MI TRABAJO
			throw new ContactoPersistenceExceptions();
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
