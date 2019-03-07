package root.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import root.security.JwtUserFactory;
import root.security.entities.Usuario;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = usuarioService.buscarPorEmail(username);
		
		if(usuario.isPresent()) {
			return JwtUserFactory.create(usuario.get());
		}
		
		throw new UsernameNotFoundException("Email nao encontrado");
	}

}