package root.required.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExemploController {

	@GetMapping(value = "/admin/{nome}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public String exemplo(@PathVariable("nome") String nome) {
		return "Olá Supremo " + nome;
	}

	@GetMapping(value = "/qualquer/{nome}")
	@PreAuthorize("hasAnyRole('USUARIO, ADMIN')")
	public String outroExemplo(@PathVariable("nome") String nome) {
		return "Olá Usuário " + nome;
	}

	@GetMapping(value = "/usuario/{nome}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public String outroExemploUsuario(@PathVariable("nome") String nome) {
		return "Olá exclusivo usuário " + nome;
	}
}
