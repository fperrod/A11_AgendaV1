package agenda.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class Config {
	
	private static DataSource ds; //un sigleton
	private static Properties prop; //vamos a entregar otro objeto en modo Sinleton
	
	
	private Config () {};
	
	public static DataSource getDataSource() { //este es un metodo factoria
		
		if ( ds == null) {
			BasicDataSource bds = new BasicDataSource();
			bds.setDriverClassName(getProperties().getProperty("bbdd.driver"));
			bds.setUrl(getProperties().getProperty("bbdd.url"));
			bds.setUsername(getProperties().getProperty("bbdd.user"));
			bds.setPassword(getProperties().getProperty("bbdd.pwd"));
			ds = bds;
		}
		
		return ds;
	}
	
	public static Properties getProperties() {
		if (prop == null) {
			prop = new Properties();
			try (FileReader fr = new FileReader("agenda.properties")) {
				prop.load(fr);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Problema con el fichero config");//lanzo una uncauhgt para controlar 
			}
		}
		return prop;
	}
}
