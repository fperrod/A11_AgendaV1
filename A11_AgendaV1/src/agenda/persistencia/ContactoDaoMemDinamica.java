package agenda.persistencia;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import agenda.modelo.Contacto;

public class ContactoDaoMemDinamica implements ContactoDao {

	private Map<Integer, Contacto> almacen;
	private int proximoId;
	
	public ContactoDaoMemDinamica() {
		almacen = new HashMap<>();
		proximoId = 1;
	}
	
	//Generar el id
	//Añadir un elemento en el map
	@Override
	public void insertar(Contacto c) {
		c.setIdContacto(proximoId++);
		almacen.put(c.getIdContacto(), c);
	}

	@Override
	public void actualizar(Contacto c) {
		almacen.replace(c.getIdContacto(), c);
	}

	// retorna false si el contacto no existe
	@Override
	public boolean eliminar(int idContacto) {
//		Contacto eliminado = almacen.remove(idContacto);
//		return eliminado != null;
		return almacen.remove(idContacto) != null;
	}

	@Override
	public boolean eliminar(Contacto c) {
		return eliminar(c.getIdContacto());
	}

	@Override
	public Contacto buscar(int idContacto) {
		return almacen.get(idContacto);
	}

	@Override
	public Set<Contacto> buscar(String cadena) {
		cadena = cadena.toLowerCase();
		Set<Contacto> resu = new HashSet<>();
		for (Contacto con : almacen.values()) {
			if (con.getApellidos().toLowerCase().contains(cadena) ||
					con.getNombre().toLowerCase().contains(cadena) ||
					con.getApodo().toLowerCase().contains(cadena)) {
				resu.add(con);
			}
		}
		return resu;
	}

	@Override
	public Set<Contacto> buscarTodos() {
		//opcion 1 muy fea
//		Collection<Contacto> valores = almacen.values();
//		Set<Contacto> resu = new HashSet<Contacto>();
//		for (Contacto c : valores) {
//			resu.add(c);
//		}
//		return resu;
		
		//opcion 2 - menos fea
//		Collection<Contacto> valores = almacen.values();
//		Set<Contacto> resu = new HashSet<Contacto>();
//		resu.addAll(valores);
//		return resu;

		//opcion 3 - bastante menos fea
//		Collection<Contacto> valores = almacen.values();
//		Set<Contacto> resu = new HashSet<Contacto>(valores);
//		return resu;
		
		//opcion que mas me gusta
		return new HashSet<Contacto>(almacen.values());
	}

}
