package repository;

import javax.swing.JOptionPane;

public interface Validaciones {

	static String validarIngresoString(String mensaje) {
		String dato;
		do {
			dato = JOptionPane.showInputDialog(mensaje);
			if (dato == null) {
				return null;
			}
			dato = dato.trim();
		} while (dato.isEmpty());
		return dato;
	}
}
