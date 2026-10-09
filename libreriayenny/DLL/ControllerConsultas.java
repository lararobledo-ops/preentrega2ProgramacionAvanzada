package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

/*inner join entre  3 tablas(editor, libro, escritor) y busquedas.*/
public class ControllerConsultas {

	private static Connection con = Conexion.getInstance().getConnection();

	/* inner join de 2 tablas: libro editor. */
	public LinkedList<String> librosConEditor() {

		String sql = "SELECT l.idlibro, l.titulo, l.genero, l.precio, l.stock, "
				+ "e.nombre AS nombre_editor, e.apellido AS apellido_editor "
				+ "FROM libro l "
				+ "INNER JOIN editor e ON l.editor_ideditor = e.ideditor "
				+ "ORDER BY l.titulo";

		LinkedList<String> resultados = new LinkedList<>();

		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				resultados.add("#" + rs.getInt("idlibro")
						+ " " + rs.getString("titulo")
						+ " | " + rs.getString("genero")
						+ " | $" + rs.getDouble("precio")
						+ " | stock: " + rs.getInt("stock")
						+ " | Editor: " + rs.getString("nombre_editor")
						+ " " + rs.getString("apellido_editor"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultados;
	}

	/*inner join de 3 tablas (editor + libro + escritor) con busqueda:el texto se busca en el titulo, el genero, y el nombre/apellido del editor o del escritor.
	 */
	public LinkedList<String> buscar(String texto) {

		String sql = "SELECT l.titulo, l.genero, "
				+ "e.nombre AS nombre_editor, e.apellido AS apellido_editor, "
				+ "es.nombre AS nombre_escritor, es.apellido AS apellido_escritor "
				+ "FROM editor e "
				+ "INNER JOIN libro l ON e.ideditor = l.editor_ideditor "
				+ "INNER JOIN escritor es ON l.idlibro = es.libro_idlibro "
				+ "WHERE l.titulo LIKE ? OR l.genero LIKE ? "
				+ "OR e.nombre LIKE ? OR e.apellido LIKE ? "
				+ "OR es.nombre LIKE ? OR es.apellido LIKE ? "
				+ "ORDER BY l.titulo";

		LinkedList<String> resultados = new LinkedList<>();

		try {
			PreparedStatement stmt = con.prepareStatement(sql);

			String patron = "%" + texto + "%";
			for (int i = 1; i <= 6; i++) {
				stmt.setString(i, patron);
			}

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				resultados.add("Libro: " + rs.getString("titulo")
						+ " (" + rs.getString("genero") + ")"
						+ " | Editor: " + rs.getString("nombre_editor")
						+ " " + rs.getString("apellido_editor")
						+ " | Escritor: " + rs.getString("nombre_escritor")
						+ " " + rs.getString("apellido_escritor"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultados;
	}
}
