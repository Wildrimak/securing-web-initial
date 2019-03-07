package root.required.controllers;

import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import root.required.dto.LoginDTO;
import root.required.responses.ResponseError;
import root.required.responses.TokenResponse;
import root.security.utils.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") //libera requisicao para todo mundo
public class LoginController {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(LoginController.class);
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostMapping
	public ResponseEntity<ResponseError<TokenResponse>> gerarTokenJwt(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) throws AuthenticationException{
		
		ResponseError<TokenResponse> response = new ResponseError<TokenResponse>();
		
		if (result.hasErrors()) {
			log.error("Erro validando lançamento: {} ", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		log.info("Gerando token para o email {}", loginDTO.getEmail());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
		String token = jwtTokenUtil.obterToken(userDetails);
		response.setData(new TokenResponse(token));
		
		return ResponseEntity.ok(response);

	}
	
	@PostMapping(value  = "/refresh")
	public ResponseEntity<ResponseError<TokenResponse>> gerarRefreshTokenJwt(HttpServletRequest request){
		
		log.info("Gerando refresh token JWT");
		ResponseError<TokenResponse> response = new ResponseError<TokenResponse>();
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));
		
		if(token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
		}
		
		if(!token.isPresent()) {
			response.getErrors().add("Token não informado.");
		} else if(!jwtTokenUtil.tokenValido(token.get())) {
			response.getErrors().add("Token inválido ou expirado.");
		}
		
		if(!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response);
		}
		
		String refreshedToken = jwtTokenUtil.refreshToken(token.get());
		response.setData(new TokenResponse(refreshedToken));
		
		return ResponseEntity.ok(response);
		
	}
	
	
	
}
