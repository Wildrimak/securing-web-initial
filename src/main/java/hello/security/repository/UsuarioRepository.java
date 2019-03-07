package hello.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import hello.security.entities.Usuario;

@Transactional(readOnly = true)
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	Usuario findByEmail(String email);
}
