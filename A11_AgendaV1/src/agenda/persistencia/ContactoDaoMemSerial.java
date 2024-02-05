package agenda.persistencia;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;
import javax.management.loading.PrivateClassLoader;

import agenda.modelo.Contacto;

public class ContactoDaoMemSerial implements ContactoDao {

	private Map<Integer, Contacto> almacen; //como estamos todo esto en memoria, tenemos que hacer que sea serailizable
	private Integer proximoId;
	private final String F_MAP = "mapa.dat";
	private final String F_IDX = "indice.dat";
	
	
	public ContactoDaoMemSerial()  {
		
		try (FileInputStream fisMap = new FileInputStream(F_MAP);
			 FileInputStream fisIdx = new FileInputStream(F_IDX)) { //ABRIR ARCHIVO BINARIO
			   
			 
			ObjectInputStream oisMap = new ObjectInputStream(fisMap); //LEER ARCHIVO BINARIO
			almacen = (Map<Integer, Contacto>) oisMap.readObject(); //ESTO DEVUELVE EL OBJETO ALMACEN, Y SE CASTEA A MAP
			
			ObjectInputStream oisIdx = new ObjectInputStream(fisIdx); 
			proximoId = (Integer)oisIdx.readObject();
			
		} catch (FileNotFoundException e) {
			//si no Existe el fichero creamos un nuevo almacen
			almacen = new HashMap<>();
			proximoId = 1;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private void grabar() {
		try (FileOutputStream fosMap = new FileOutputStream(F_MAP);
			 FileOutputStream fosIdx = new FileOutputStream(F_IDX)) {
			ObjectOutputStream oos = new ObjectOutputStream(fosMap);
			oos.writeObject(almacen);
			oos = new ObjectOutputStream(fosIdx);
			oos.writeObject(proximoId);
					
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//Generar el id
	//Añadir un elemento en el map
	@Override
	public void insertar(Contacto c) {
		c.setIdContacto(proximoId++);
		almacen.put(c.getIdContacto(), c);
		grabar();
	}

	@Override
	public void actualizar(Contacto c) {
		almacen.replace(c.getIdContacto(), c);
		grabar();
	}

	// retorna false si el contacto no existe
	@Override
	public boolean eliminar(int idContacto) {
		Contacto eliminado = almacen.remove(idContacto);
		grabar();
		return eliminado != null;
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
