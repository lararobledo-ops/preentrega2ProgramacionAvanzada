package GUI;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import BLL.Usuario;
import BLL.Editor;
import repository.Hashing;

public class Main {
	
	private static ImageIcon cargarIcono(String nombre) {
		URL url = Main.class.getResource(nombre);
		return url == null ? null : new ImageIcon(url);
		
	}

	public static void main(String[] args) {
	

		String[] menu = { "Login", "Registrarse", "Salir" };
		int opcion;
		do {
			opcion = JOptionPane.showOptionDialog(null, "Bienvenido, ingrese con su usuario", "Libreria Yenny",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, cargarIcono("esnupi.jpg"), menu, menu[0]);

			switch (opcion) {
			case 0: {
				Usuario logueado = Usuario.Login();
				if (logueado == null) {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
				} else {
					logueado.Menu();
				}
				break;
			}
			case 1:
				Usuario.registrarse();
				break;
			default:
				break;
			}
		} while (opcion != 2 && opcion != JOptionPane.CLOSED_OPTION);
	}
		
		private static void crearEditorPrueba() {

		    String hash = Hashing.hash("1234");

		    Editor editor = new Editor(
		        0,
		        "Paula",
		        "Robledo",
		        40987263,
		        "paula.editor@gmail.com",
		        hash
		    );

		    Usuario.getController().agregarUsuario(editor);
		}
	

}
