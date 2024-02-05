package agenda.inicio;

import agenda.vista.MenuPrincipal;
import agenda.vista.swing.VInicial;

public class Main {
	public static void main(String[] args) {
		String tipoVista = "grafico";
		if (args.length == 1)
			tipoVista = args[0];
		
		switch (tipoVista) {
		case "consola":
			new MenuPrincipal().menu();
			break;
		case "grafico":
			new VInicial();
			break;
		default:
			new VInicial();
			break;
		}
	}
}
