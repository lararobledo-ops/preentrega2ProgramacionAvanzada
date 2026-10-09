package repository;

import java.util.List;

import BLL.Usuario;

public interface UsuarioRepository {
	void agregarUsuario(Usuario usuario);

	List<Usuario> mostrarUsuarios();

	List<Usuario> mostrarEscritores();

	Usuario login(String nombre, String password);
	


}

