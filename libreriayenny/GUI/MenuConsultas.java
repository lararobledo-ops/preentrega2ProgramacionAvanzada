package GUI;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import BLL.Libro;
import BLL.Usuario;
import DLL.ControllerConsultas;
import DLL.ControllerLibro;
import repository.Validaciones;

public class MenuConsultas {

	private static void mostrar(LinkedList<String> filas, String vacio) {
		if (filas.isEmpty()) {
			JOptionPane.showMessageDialog(null, vacio);
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (String f : filas) {
			sb.append(f).append("\n");
		}
		JOptionPane.showMessageDialog(null, sb.toString());
	}

	public static void main(String[] args) {

		Usuario logueado = Usuario.Login();
		if (logueado == null) {
			return;
		}

		ControllerConsultas consultas = new ControllerConsultas();
		ControllerLibro libros = new ControllerLibro();

		String[] opciones = {
				"Libros con su editor",
				"Libros con editor y escritor",
				"Buscar por titulo",
				"Buscar en las tablas",
				"Crear libro",
				"Eliminar libro",
				"Leer libro",
				"Actualizar",
				"Salir" };

		int opcion;
		do {
			opcion = JOptionPane.showOptionDialog(null, "Consultas - " + logueado.getNombre(),
					"Consultas", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones,
					opciones[0]);

			switch (opcion) {
			case 0:
				mostrar(consultas.librosConEditor(), "No hay libros cargados.");
				break;
			case 1:
				mostrar(libros.mostrarLibrosConUsuarios(), "No hay libros con escritor asignado.");
				break;
			case 2: {
				String texto = Validaciones.validarIngresoString("Titulo (o parte) a buscar");
				if (texto == null) {
					break;
				}
				LinkedList<Libro> encontrados = libros.buscarPorTitulo(texto);
				LinkedList<String> filas = new LinkedList<>();
				for (Libro l : encontrados) {
					filas.add(l + " | " + l.getGenero() + " | $" + l.getPrecio() + " | stock: " + l.getStock());
				}
				mostrar(filas, "No se encontraron libros con ese titulo.");
				break;
			}
			case 3: {
				String texto = Validaciones.validarIngresoString("Buscar por titulo, genero, editor o escritor");
				if (texto == null) {
					break;
				}
				mostrar(consultas.buscar(texto), "Sin resultados.");
				break;
			}
			case 4:
				
			
			case 5:
				break;
			case 6:
				String texto = LibroRepository.listarLibros("Listar libros");
				break;
			case 7:
				break;
			case 8:
				break;
				
			default:
				break;
			}
		} while (opcion != 4 && opcion != JOptionPane.CLOSED_OPTION);
	}
}