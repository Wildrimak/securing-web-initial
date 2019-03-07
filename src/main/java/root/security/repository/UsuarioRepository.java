package root.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import root.security.entities.Usuario;

@Transactional(readOnly = true)
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	Usuario findByEmail(String email);
}
