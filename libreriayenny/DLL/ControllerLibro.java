package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import BLL.Libro;

public class ControllerLibro {

	private static Connection con = Conexion.getInstance().getConnection();

	//para agregar libro
	public void agregarLibro(Libro libro) {
	    try {
	        PreparedStatement statement = con.prepareStatement(
	            "INSERT INTO libro (titulo, genero, precio, stock, editor_ideditor) VALUES (?, ?, ?, ?, ?)"
	        );

	        statement.setString(1, libro.getTitulo());
	        statement.setString(2, libro.getGenero());
	        statement.setDouble(3, libro.getPrecio());
	        statement.setInt(4, libro.getStock());
	        statement.setInt(5, libro.getEditor_ideditor());

	        int filas = statement.executeUpdate();

	        if (filas > 0) {
	            JOptionPane.showMessageDialog(null, "Libro agregado correctamente");
	        }

	    } catch (SQLIntegrityConstraintViolationException e) {
	        JOptionPane.showMessageDialog(null, "No se puede crear el libro.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//buscar por titulo

	public LinkedList<Libro> buscarPorTitulo(String titulo) {

	    String sql = "SELECT * FROM libro WHERE titulo LIKE ?";

	    LinkedList<Libro> libros = new LinkedList<>();

	    try {
	        PreparedStatement stmt = con.prepareStatement(sql);

	        stmt.setString(1, "%" + titulo + "%");

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {

	            libros.add(new Libro(
	                rs.getInt("idlibro"),
	                rs.getString("titulo"),
	                rs.getString("genero"),
	                rs.getDouble("precio"),
	                rs.getInt("stock"),
	                rs.getInt("editor_ideditor")
	            ));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return libros;
	}

	
	//mostrar libros con usuarios
	public LinkedList<String> mostrarLibrosConUsuarios() {

	    LinkedList<String> resultados = new LinkedList<>();

	    String sql =
	        "SELECT "
	        + "e.nombre AS nombre_editor, "
	        + "e.apellido AS apellido_editor, "
	        + "l.titulo, "
	        + "l.genero, "
	        + "l.precio, "
	        + "l.stock, "
	        + "es.nombre AS nombre_escritor, "
	        + "es.apellido AS apellido_escritor "
	        + "FROM editor e "
	        + "INNER JOIN libro l "
	        + "ON e.ideditor = l.editor_ideditor "
	        + "INNER JOIN escritor es "
	        + "ON l.idlibro = es.libro_idlibro";

	    try {

	        PreparedStatement stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {

	            resultados.add(
	                "Editor: "
	                + rs.getString("nombre_editor") + " "
	                + rs.getString("apellido_editor")
	                + " | Libro: "
	                + rs.getString("titulo")
	                + " | Escritor: "
	                + rs.getString("nombre_escritor") + " "
	                + rs.getString("apellido_escritor")
	            );
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return resultados;
	}
	
	//mostrar libros
	public LinkedList<Libro> mostrarLibros() {
	    return consultar("SELECT * FROM libro");
	}

	private LinkedList<Libro> consultar(String sql) {

	    LinkedList<Libro> libros = new LinkedList<>();

	    try {
	        PreparedStatement stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {

	            libros.add(new Libro(
	                rs.getInt("idlibro"),
	                rs.getString("titulo"),
	                rs.getString("genero"),
	                rs.getDouble("precio"),
	                rs.getInt("stock"),
	                rs.getInt("editor_ideditor")
	            ));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return libros;
	}
	
	
	
	
}
