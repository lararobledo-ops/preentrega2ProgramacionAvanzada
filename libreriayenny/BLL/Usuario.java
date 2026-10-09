package BLL;

import javax.swing.JOptionPane;

import DLL.ControllerUsuario;
import repository.Hashing;
import repository.Validaciones;
import BLL.Escritor;

public abstract class Usuario {

    protected int id;
    protected String nombre;
    protected String apellido;
    protected int dni;
    protected String mail;
    protected String contrasenia;

    private static ControllerUsuario controller = new ControllerUsuario();

    public Usuario(int id, String nombre, String apellido, int dni, String mail, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.mail = mail;
        this.contrasenia = contrasenia;
    }

    public Usuario(String nombre, String apellido, int dni, String mail, String contrasenia) {
        this(0, nombre, apellido, dni, mail, contrasenia);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public static ControllerUsuario getController() {
        return controller;
    }

    public static void setController(ControllerUsuario controller) {
        Usuario.controller = controller;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id
                + ", nombre=" + nombre
                + ", apellido=" + apellido
                + ", dni=" + dni
                + ", mail=" + mail + "]";
    }

    public static Usuario Login() {

        String nombre = Validaciones.validarIngresoString("Ingrese nombre");

        if (nombre == null) {
            return null;
        }

        String contrasenia = Validaciones.validarIngresoString("Ingrese contraseña");

        if (contrasenia == null) {
            return null;
        }

        return controller.login(nombre, contrasenia);
    }

    public abstract void Menu();

    public static void registrarse() {

        String nombre = Validaciones.validarIngresoString("Ingrese nombre");

        if (nombre == null) {
            return;
        }

        String apellido = Validaciones.validarIngresoString("Ingrese apellido");

        if (apellido == null) {
            return;
        }

        String dniTexto = Validaciones.validarIngresoString("Ingrese DNI");

        if (dniTexto == null) {
            return;
        }

        int dni;

        try {
            dni = Integer.parseInt(dniTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El DNI debe ser numérico.");
            return;
        }

        String mail = Validaciones.validarIngresoString("Ingrese mail");

        if (mail == null) {
            return;
        }

        String contrasenia = Validaciones.validarIngresoString("Ingrese contraseña");

        if (contrasenia == null) {
            return;
        }

        String contraseniaHasheada = Hashing.hash(contrasenia);

        controller.agregarUsuario(new Escritor(nombre,apellido,dni,mail,contraseniaHasheada));

        JOptionPane.showMessageDialog(
                null,
                "Registro procesado. Ya podés iniciar sesión."
        );
    }
}


