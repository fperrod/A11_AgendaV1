package agenda.modelo;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class Contacto implements Comparable<Contacto>, Serializable {

	private int idContacto;
	private String nombre;
	private String apellidos;
	private String apodo;
	private Domicilio dom; //tiene que ser serializable tambien para que se pueda alamcenar
	private Set<String> telefonos;
	private Set<String> correos;
	
	public Contacto() {
		telefonos = new LinkedHashSet<>();
		correos = new LinkedHashSet<>();
		dom = new Domicilio();
	}

	public Contacto(String nombre) {
		this();
		this.nombre = nombre;
	}

	public Contacto(String nombre, String apellidos, String apodo) {
		this(nombre);
		this.apellidos = apellidos;
		this.apodo = apodo;
	}

	public int getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(int idContacto) {
		this.idContacto = idContacto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public Domicilio getDom() {
		return dom;
	}

	public void setDom(Domicilio dom) {
		this.dom = dom;
	}

	public Set<String> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(Set<String> telefonos) {
		this.telefonos = telefonos;
	}
	
	public void addTelefono(String telefono) {
		telefonos.add(telefono);
	}

	public Set<String> getCorreos() {
		return correos;
	}

	public void setCorreos(Set<String> correos) {
		this.correos = correos;
	}

	public void addCorreo(String correo) {
		correos.add(correo);
	}

	@Override
	public int hashCode() {
		return idContacto;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacto other = (Contacto) obj;
		return idContacto == other.idContacto;
	}

	@Override
	public String toString() {
		return "Contacto [" + idContacto + ", " + nombre + ", " + apellidos + ", " + apodo + "]";
	}

	@Override
	public int compareTo(Contacto o) {
		if (this.equals(o)) {
			return 0;
		}
		Collator col = Collator.getInstance(new Locale("es"));
		return col.compare(this.nombre + this.idContacto, o.nombre + o.idContacto);
		
	}
	
	// Opcion 3 de Comparator
	public static Comparator<Contacto> getIdComparator() {
		return new Comparator<Contacto>() {
			
			@Override
			public int compare(Contacto c1, Contacto c2) {
				return c1.getIdContacto() - c2.getIdContacto();
			}
			
		};
	}
}
