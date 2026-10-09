
package BLL;

public class Libro {

    private int idlibro;
    private String titulo;
    private String genero;
    private double precio;
    private int stock;
    private int editor_ideditor;

    public Libro(int idlibro, String titulo, String genero, double precio, int stock, int editor_ideditor) {
        this.idlibro = idlibro;
        this.titulo = titulo;
        this.genero = genero;
        this.precio = precio;
        this.stock = stock;
        this.editor_ideditor = editor_ideditor;
    }

    public Libro(String titulo, String genero, double precio, int stock, int editor_ideditor) {
        this(0, titulo, genero, precio, stock, editor_ideditor);
    }

    public int getIdlibro() {
        return idlibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public int getEditor_ideditor() {
        return editor_ideditor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setEditor_ideditor(int editor_ideditor) {
        this.editor_ideditor = editor_ideditor;
    }

    @Override
    public String toString() {
        return "#" + idlibro + " - " + titulo;
    }
}

