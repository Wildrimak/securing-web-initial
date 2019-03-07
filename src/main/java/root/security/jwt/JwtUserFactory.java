package root.security.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import root.required.models.Credencial;
import root.required.models.Usuario;

public class JwtUserFactory {

	public JwtUserFactory() {
	}

	public static UserDetails create(Usuario usuario) {
		return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getSenha(),
				mapToGrantedAuthorities(usuario.getCredencial()));
	}

	private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(Credencial credencial) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(credencial.toString()));

		return authorities;
	}

}
