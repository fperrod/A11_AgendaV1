package agenda.tests;

import agenda.modelo.Contacto;
import agenda.persistencia.ContactoDao;
import agenda.persistencia.ContactoDaoJDBCMala;
import agenda.persistencia.ContactoDaoMemArray;

public class TestContactoDaoJDBC {
	public static void main(String[] args) {
		
		ContactoDao dao = new ContactoDaoJDBCMala();

		System.out.println(dao.buscar(1));
		
		Contacto nuevo = new Contacto("paco,"
				+ "perez,"
				+ "pp,"
				+ "calle,"
				+ "gig,"
				+ "15,"
				+ "1,"
				+ "D,"
				+ "28041,"
				+ "Mad,"
				+ "Mad)");
		System.out.println(nuevo.getIdContacto()); // 0
		
		dao.insertar(nuevo);
//		
		System.out.println(nuevo.getIdContacto()); // 1
//
//		
//		Contacto buscado = dao.buscar(1);
//		System.out.println(buscado);
	}
}
