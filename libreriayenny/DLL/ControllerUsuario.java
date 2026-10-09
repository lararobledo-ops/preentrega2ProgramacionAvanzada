
package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import BLL.Editor;
import BLL.Escritor;
import BLL.Usuario;
import repository.Hashing;
import repository.UsuarioRepository;

public class ControllerUsuario implements UsuarioRepository {

    private static Connection con = Conexion.getInstance().getConnection();

    @Override
    public Usuario login(String nombre, String contrasenia) {

        // Buscar primero en la tabla editor
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM editor WHERE nombre = ?"
            );

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String hash = rs.getString("contrasenia");

                if (Hashing.verificar(contrasenia, hash)) {
                    return new Editor(
                        rs.getInt("ideditor"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("mail"),
                        hash
                    );
                }
            }
            /*if (rs.next()) {
                String hash = rs.getString("contrasenia");

                if (Hashing.verificar(contrasenia, hash)) {
                    return new Editor(
                        rs.getInt("ideditor"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("mail"),
                        hash
                    );
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        // si no es editor buscar en la tabla escritor
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM escritor WHERE nombre = ?"
            );

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("contrasenia");

                if (Hashing.verificar(contrasenia, hash)) {
                    return new Escritor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("mail"),
                        hash
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(
            null,
            "Nombre o contraseña incorrectos."
        );

        return null;
    }

    @Override
    public void agregarUsuario(Usuario usuario) {

        try {

            // registrar un escritor
            if (usuario instanceof Escritor) {

                PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO escritor "
                    + "(nombre, apellido, dni, mail, contrasenia, libro_idlibro) "
                    + "VALUES (?, ?, ?, ?, ?, NULL)"
                );

                statement.setString(1, usuario.getNombre());
                statement.setString(2, usuario.getApellido());
                statement.setInt(3, usuario.getDni());
                statement.setString(4, usuario.getMail());
                statement.setString(5, usuario.getContrasenia());

                int filas = statement.executeUpdate();

                if (filas > 0) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Escritor registrado correctamente."
                    );
                }

            // Registrar un editor
            } else if (usuario instanceof Editor) {

                PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO editor "
                    + "(nombre, apellido, mail, contrasenia, dni) "
                    + "VALUES (?, ?, ?, ?, ?)"
                );

                statement.setString(1, usuario.getNombre());
                statement.setString(2, usuario.getApellido());
                statement.setString(3, usuario.getMail());
                statement.setString(4, usuario.getContrasenia());
                statement.setInt(5, usuario.getDni());

                int filas = statement.executeUpdate();

                if (filas > 0) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Editor registrado correctamente."
                    );
                }
            }

        } catch (SQLIntegrityConstraintViolationException e) {

            JOptionPane.showMessageDialog(
                null,
                "No se puede registrar: el DNI, mail o ID ya existe."
            );

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "Ocurrió un error al registrar el usuario. Revisá la consola."
            );
        }
    }

    @Override
    public LinkedList<Usuario> mostrarUsuarios() {

        LinkedList<Usuario> usuarios = new LinkedList<>();

        usuarios.addAll(mostrarEditores());
        usuarios.addAll(mostrarEscritores());

        return usuarios;
    }

    @Override
    public LinkedList<Usuario> mostrarEscritores() {

        LinkedList<Usuario> escritores = new LinkedList<>();

        String sql = "SELECT * FROM escritor";

        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                escritores.add(
                    new Escritor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("mail"),
                        rs.getString("contrasenia")
                    )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return escritores;
    }

    private LinkedList<Usuario> mostrarEditores() {

        LinkedList<Usuario> editores = new LinkedList<>();

        String sql = "SELECT * FROM editor";

        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                editores.add(
                    new Editor(
                        rs.getInt("ideditor"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("mail"),
                        rs.getString("contrasenia")
                    )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return editores;
    }
}
