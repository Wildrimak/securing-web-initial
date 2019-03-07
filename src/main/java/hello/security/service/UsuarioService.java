package hello.security.service;

import java.util.Optional;

import hello.security.entities.Usuario;

public interface UsuarioService {
	Optional<Usuario> buscarPorEmail(String email);
}
