package root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import root.required.models.Credencial;
import root.required.models.Usuario;
import root.required.repository.UsuarioRepository;
import root.security.utils.SenhaUtils;

@SpringBootApplication
public class Application {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public static void main(String[] args) throws Throwable {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			
			Usuario usuario = new Usuario();
			usuario.setEmail("finalwildrimak@gmail.com");
			usuario.setCredencial(Credencial.ROLE_USUARIO);
			usuario.setSenha(SenhaUtils.gerarBCrypt("12345"));
			this.usuarioRepository.save(usuario);
			
			Usuario admin = new Usuario();
			admin.setEmail("vanusa@gmail.com");
			admin.setCredencial(Credencial.ROLE_ADMIN);
			admin.setSenha(SenhaUtils.gerarBCrypt("12345"));
			this.usuarioRepository.save(admin);
			
			
		};
	}

}
