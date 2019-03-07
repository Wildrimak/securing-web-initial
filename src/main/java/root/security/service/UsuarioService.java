package root.security.service;

import java.util.Optional;

import root.security.entities.Usuario;

public interface UsuarioService {
	Optional<Usuario> buscarPorEmail(String email);
}
