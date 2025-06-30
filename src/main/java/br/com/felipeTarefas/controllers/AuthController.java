package br.com.felipeTarefas.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.In.LoginRequest;
import br.com.felipeTarefas.domain.dtos.In.UsuarioDTOin;
import br.com.felipeTarefas.domain.dtos.Out.TokenResponse;
import br.com.felipeTarefas.repositories.UsuarioRepository;
import br.com.felipeTarefas.security.UsuarioDetails;
import br.com.felipeTarefas.security.jwt.JwtTokenService;
import br.com.felipeTarefas.service.UsuarioService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final JwtTokenService tokenService;

    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

            UsuarioDetails usuarioDetails = (UsuarioDetails) auth.getPrincipal();
            String token = tokenService.gerarToken(usuarioDetails);
            return ResponseEntity.ok(new TokenResponse(usuarioDetails.getUsername(), token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UsuarioDTOin usuarioDTOin) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByEmail(usuarioDTOin.getEmail());

        if (usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            Usuario usuario = usuarioService.registrarUsuario(usuarioDTOin); // testar
            log.info("Usuário criado com sucesso");
            UsuarioDetails usuarioDetails = new UsuarioDetails(usuario);
            String token = tokenService.gerarToken(usuarioDetails); // gera o token baseado nos dados do request
            return ResponseEntity.ok(new TokenResponse(usuarioDetails.getUsername(), token));
        }
        // retorna email e token
    }
}
