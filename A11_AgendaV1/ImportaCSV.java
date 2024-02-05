package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportaCSV {
	
	public static List<String[]> importar(String fichero){
		List<String[]> datos = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichero));
			String linea = br.readLine();
			while(linea != null){
				String[] datosLinea = linea.split(",");
				datos.add(datosLinea);
				linea = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datos;
	}
}
