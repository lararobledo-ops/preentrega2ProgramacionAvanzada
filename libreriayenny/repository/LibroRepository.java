package repository;

import BLL.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class LibroRepository {

    /*insertar un libro*/
    public boolean insertarLibro(Libro libro, Connection con)
            throws SQLException {

        String sql = "INSERT INTO libro "
                   + "(titulo, genero, precio, stock) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getGenero());
            ps.setDouble(3, libro.getPrecio());
            ps.setInt(4, libro.getStock());

            return ps.executeUpdate() > 0;
        }
    }

    // mostrar libros
    public LinkedList<Libro> listarLibros(Connection con)
            throws SQLException {

        LinkedList<Libro> libros = new LinkedList<>();

        String sql = "SELECT idlibro, titulo, genero, precio, stock "
                   + "FROM libro";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("idlibro"),
                    rs.getString("titulo"),
                    rs.getString("genero"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getInt("editor_ideditor")
                );

                libros.add(libro);
            }
        }


        
        return libros;
    }

    // modificar libro
    public boolean modificarLibro(Libro libro, Connection con)
            throws SQLException {

        String sql = "UPDATE libro SET titulo = ?, genero = ?, "
                   + "precio = ?, stock = ? +  WHERE idlibro = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getGenero());
            ps.setDouble(3, libro.getPrecio());
            ps.setInt(4, libro.getStock());
            ps.setInt(5, libro.getIdlibro());

            return ps.executeUpdate() > 0;
        }
    }

    // eliminar con id
    public boolean eliminarLibro(int idlibro, Connection con)
            throws SQLException {

        String sql = "DELETE FROM libro WHERE idlibro = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idlibro);

            return ps.executeUpdate() > 0;
        }
    }
}