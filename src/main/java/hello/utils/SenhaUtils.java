package hello.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtils {
	
	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static String gerarBCrypt(String senha) {
		
		if(senha == null) {
			return senha;
		}

		return passwordEncoder.encode(senha);
	}
	
	public static boolean senhaValida(String senha, String senhaEncoded) {
		return passwordEncoder.matches(senha, senhaEncoded);
	}
	
}
