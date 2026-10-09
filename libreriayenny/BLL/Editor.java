package BLL;

import java.util.List;

import javax.swing.JOptionPane;

import DLL.ControllerLibro;

public class Editor extends Usuario {

	private static ControllerLibro libroController = new ControllerLibro();

	public Editor(int id, String nombre, String apellido, int dni, String mail, String contrasenia) {
	    super(id, nombre, apellido, dni, mail, contrasenia);
	}
	@Override
	public String toString() {
		return "Editor [" + super.toString() + "]";
	}

	@Override
	public void Menu() {
		String[] opciones = { "Ver escritores", "Ver libros", "Salir" };
		int opcion;
		do {
			opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Panel editor",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
			switch (opcion) {
			case 0: {
				List<Usuario> escritores = getController().mostrarEscritores();
				if (escritores.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No hay escritores registrados");
				} else {
					StringBuilder sb = new StringBuilder();
					for (Usuario u : escritores) {
						sb.append(u.getNombre()).append(" (").append(u.getMail()).append(")\n");
					}
					JOptionPane.showMessageDialog(null, sb.toString());
				}
				break;
			}
			case 1: {
				List<Libro> libros = libroController.mostrarLibros();
				if (libros.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No hay libros cargados");
				} else {
					StringBuilder sb = new StringBuilder();
					for (Libro l : libros) {
						sb.append(l).append("\n");
					}
					JOptionPane.showMessageDialog(null, sb.toString());
				}
				break;
			}
			default:
				break;
			}
		} while (opcion != 2 && opcion != JOptionPane.CLOSED_OPTION);
	}
}
