package br.com.felipeTarefas.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.In.LoginRequest;
import br.com.felipeTarefas.domain.dtos.In.UsuarioDTOin;
import br.com.felipeTarefas.domain.dtos.Out.TokenResponse;
import br.com.felipeTarefas.domain.dtos.Out.UsuarioDTOout;
import br.com.felipeTarefas.repositories.UsuarioRepository;
import br.com.felipeTarefas.service.AuthService;
import jakarta.validation.Valid;
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
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            TokenResponse tokenResponse = authService.login(request);
            return ResponseEntity.ok(tokenResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody UsuarioDTOin usuarioDTOin) {
        log.info("DTO recebido: username: {}, email: {}",
                usuarioDTOin.getUsername(),
                usuarioDTOin.getEmail());

        Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByEmail(usuarioDTOin.getEmail());

        if (usuarioOptional.isPresent()) {
            log.info("Tentativa de criação de usuário com email já existente: {}",
                    usuarioDTOin.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            TokenResponse tokenResponse = authService.registrarUsuario(usuarioDTOin); // testar
            log.info("Usuário registrado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/usuarios")
    public ResponseEntity<UsuarioDTOout> criarUsuarioOpçaoAdmin(@Valid @RequestBody UsuarioDTOin usuarioDTOin) {
        log.info("Usuario criado: username: {}, email: {}, cpf: {}, password: {}, role: {}",
                usuarioDTOin.getUsername(), usuarioDTOin.getEmail(), usuarioDTOin.getCpf(),
                usuarioDTOin.getPassword(), usuarioDTOin.getRole());

        UsuarioDTOout usuario = authService.criarUsuarioComOpçaoAdmin(usuarioDTOin);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
}
