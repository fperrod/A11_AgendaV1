package agenda.persistencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import agenda.modelo.Contacto;

class TestDaoMem {
	
	private static Contacto[] contactos;
	private ContactoDao dao = getInstance();
	
	private ContactoDao getInstance() {
		return new ContactoDaoMemDinamica();
	}

	@BeforeEach
	void carga() {
		contactos = new Contacto[10];
		for (int i = 0; i < contactos.length; i++) {
			Contacto c = new Contacto();
			c.setNombre("Con" + i);
			c.setApellidos("Con" + i + " Ape");
			c.setApodo("Con" + i + " Apodo");
			contactos[i] = c;
		}
		dao = getInstance();
		for (Contacto contacto : contactos) {
			dao.insertar(contacto);
		}
	}

	@Test
	void testContactoDaoMem() {
		dao = getInstance();
		assertNotNull(dao.buscarTodos());
		assertEquals(0, dao.buscarTodos().size());
	}
	
	@Test
	void testInsertar() {
		dao = getInstance();
		for (Contacto contacto : contactos) {
			dao.insertar(contacto);
		}
		assertEquals(contactos.length, dao.buscarTodos().size());
//		muestraContactos();
	}

	void muestraContactos() {
		for (Contacto contacto : dao.buscarTodos()) {
			System.out.println(contacto.getIdContacto() + " " +
					contacto.getNombre());
		}
	}


	@Test
	void testEliminar() {
		assertTrue(dao.eliminar(2));
		assertFalse(dao.eliminar(15));
		assertEquals(contactos.length - 1, dao.buscarTodos().size());
		assertTrue(dao.eliminar(1));
		for (int i = 3; i <= 9; i++) {
			assertTrue(dao.eliminar(i));
		}
		assertEquals(1, dao.buscarTodos().size());
		assertNotNull(dao.buscar(10));
		assertTrue(dao.eliminar(10));
		assertEquals(0, dao.buscarTodos().size());
	}

	@Test
	void testActualizar() {
		assertEquals(0, dao.buscar("Cabe").size());
		assertEquals(10, dao.buscar("Con").size());
		Contacto b = new Contacto();
		b.setIdContacto(5);
		b.setApodo("Cabezon");
		b.setNombre("nuevo nombre");
		b.setApellidos("nuevo apellidos");
		dao.actualizar(b);
		assertEquals(1, dao.buscar("Cabe").size());
		assertEquals(9, dao.buscar("Con").size());
	}

	@Test
	void testBuscarInt() {
		assertEquals("Con2", dao.buscar(3).getNombre());
		assertNull(dao.buscar(15));
	}

	@Test
	void testBuscarString() {
		Contacto b = contactos[5];
		b.setApodo("Cabezon");
		b.setNombre("nuevo nombre");
		b.setApellidos("new apellidos");
		dao.actualizar(b);
		assertNotNull(dao.buscar("cabe"));
		assertNotNull(dao.buscar("NUEV"));
		assertNotNull(dao.buscar("New"));
		assertEquals(1, dao.buscar("cabe").size());
		assertEquals(1, dao.buscar("NUEV").size());
		assertEquals(1, dao.buscar("New").size());
		assertEquals(9, dao.buscar("CON").size());
	}

	@Test
	void testBuscarTodos() {
		assertEquals(10, dao.buscarTodos().size());
	}

}
