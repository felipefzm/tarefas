package br.com.felipeTarefas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.In.LoginRequest;
import br.com.felipeTarefas.domain.dtos.Out.TokenResponse;
import br.com.felipeTarefas.repositories.UsuarioRepository;
import br.com.felipeTarefas.security.UsuarioDetails;
import br.com.felipeTarefas.security.jwt.JwtTokenService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;  

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request){
        Usuario usuario = usuarioRepository.findUsuarioByEmail(request.email())
            .orElseThrow(() -> new UsernameNotFoundException("Usuario com esse email não encontrado"));
            UsuarioDetails usuarioDetails = new UsuarioDetails(usuario);
            
        if (!passwordEncoder.matches(request.password(), usuarioDetails.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            String token = tokenService.gerarToken(usuarioDetails); // gera o token baseado nos dados vindos do LoginRequest
            return ResponseEntity.ok(new TokenResponse(usuarioDetails.getUsername(), token));
            // retorna email e token
        }
    }
    
}
