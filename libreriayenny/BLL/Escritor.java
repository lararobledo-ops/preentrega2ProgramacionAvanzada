package BLL;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import DLL.ControllerLibro;

public class Escritor extends Usuario {

    private static ControllerLibro libroController = new ControllerLibro();

    public Escritor(int id, String nombre, String apellido, int dni, String mail, String contrasenia) {
        super(id, nombre, apellido, dni, mail, contrasenia);
    }

    public Escritor(String nombre, String apellido, int dni, String mail, String contrasenia) {
        super(nombre, apellido, dni, mail, contrasenia);
    }

    @Override
    public String toString() {
        return "Escritor [" + super.toString() + "]";
    }
    @Override
    public void Menu() {

        String[] opciones = {
            "Ver libros",
            "Salir"
        };

        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,"Bienvenido " + nombre,"Panel escritor",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,opciones,opciones[0]);

            switch (opcion) {
                case 0: {
                    LinkedList<Libro> libros = libroController.mostrarLibros();
                    if (libros.isEmpty()) {
                       JOptionPane.showMessageDialog(null,"No hay libros cargados");
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
        } while (
                opcion != 1
                && opcion != JOptionPane.CLOSED_OPTION
        );
    }
}


