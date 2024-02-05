package agenda.negocio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.*;

import agenda.modelo.Contacto;
import agenda.persistencia.ContactoDao;
import agenda.persistencia.ContactoDaoJDBC;
import agenda.persistencia.ContactoDaoMemDinamica;
import agenda.persistencia.ContactoDaoMemSerial;

public class AgendaImpl implements Agenda {
	
	private ContactoDao cDao;
	
	public AgendaImpl() { //LO SUYO ES QUE ESTO DESAPAREZACA SIRVE PARA APLICAR INYECCION DE DEPENDENCIAS
		//cDao = new ContactoDaoMemDinamica(); 
//		cDao = new ContactoDaoMemSerial(); 
		cDao = new ContactoDaoJDBC();
	}
	
//	//INYECCION DE DEPENDECIAS EN VEZ DE PONER DEPENDENCIA A LA CLASE LA INYECTA, LA METE EN UN METODO
//	public AgendaImpl(ContactoDao cDao) {
//		cDao = new ContactoDaoMemDinamica();
//	}

	@Override
	public void insertarContacto(Contacto c) {
		cDao.insertar(c);
	}

	@Override
	public Contacto eliminar(int idContacto) {
		Contacto contacEliminado =  cDao.buscar(idContacto);
		cDao.eliminar(idContacto);
		return contacEliminado;	 
	}

	@Override
	public boolean eliminar(Contacto c) {
		return eliminar(c.getIdContacto()) != null;
	}

	@Override
	public void modificar(Contacto c) {
		cDao.actualizar(c);
		
	}

	@Override //se debe retornar, ordenado por apodo
	public Set<Contacto> buscarTodos() {
		Set<Contacto> resp = new TreeSet<>(getComApodo());
		resp.addAll(cDao.buscarTodos());
		return resp;
	}

	@Override
	public Set<Contacto> buscarContactoPorNombre(String nombre) {
		return null;
	}
	
	private Comparator<Contacto> getComApodo(){
		Collator col = Collator.getInstance(new Locale("es"));
		return new Comparator<Contacto>() {

			@Override
			public int compare(Contacto o1, Contacto o2) {
				return col.compare(o1.getApodo() + o1.getIdContacto(), o2.getApodo() + o2.getIdContacto());
			}
		};
	}
	
	private Comparator<Contacto> getComApodoLambda(){
		Collator col = Collator.getInstance(new Locale("es"));
		return (c1, c2) -> col.compare(c1.getApodo()+ c1.getIdContacto() , c2.getApodo()+ c2.getIdContacto());
	}
	

	@Override
	public Contacto buscar(int idContacto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int importarCSV(String fichero) throws IOException {
		List<String[]> datos = importar(fichero);
		int cant = 0;
		for(String[] registroCsv : datos) {
			//priemro crear el objeto contacto, rellenarlo de datos y enviarlo
			Contacto con = new Contacto();
			con.setNombre(registroCsv[0]);
			con.setApellidos(registroCsv[1]);
			con.setApodo(registroCsv[2]);

			con.getDom().setTipoVia(registroCsv[3]);
			con.getDom().setVia(registroCsv[4]);
			
			//controlar la excepcion NumberFormat
			try {
				con.getDom().setNumero(Integer.parseInt(registroCsv[5]));
			} catch (NumberFormatException e) {
				//guardamos igual el contacto con numero en 0
				//algun log
			}
			
			try {
				con.getDom().setPiso(Integer.parseInt(registroCsv[6]));
			} catch (NumberFormatException e) {
				//guardamos igual el contacto con piso en 0
				//algun log
			}
			
			con.getDom().setPuerta(registroCsv[7]);
			con.getDom().setCodigoPostal(registroCsv[8]);
			con.getDom().setCiudad(registroCsv[9]);
			con.getDom().setProvincia(registroCsv[10]);
			//convierte un string con "/" en un array de String. el separador es "/"
			String[] telefonos = registroCsv[11].split(("/")); 
			for (String telefono : telefonos) {
				con.addTelefono(telefono);				
			}
			for (String correo : registroCsv[12].split(("/"))) {
				con.addCorreo(correo);
			}
			Arrays.stream(registroCsv[12].split("/")).forEach(correo -> con.addCorreo(correo));
			cant++;
			cDao.insertar(con);		
			
		}
		return cant;
	}
	
	private List<String[]> importar(String fichero){
		List<String[]> datos = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fichero))){
			String linea;
			while((linea = br.readLine()) != null){
				String[] datosLinea = linea.split(";"); //si hay 4 comas, 5 elementos en el array
				datos.add(datosLinea);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datos;
	}

}
