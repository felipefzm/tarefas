package br.com.felipeTarefas.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.In.LoginRequest;
import br.com.felipeTarefas.domain.dtos.In.UsuarioDTOin;
import br.com.felipeTarefas.domain.dtos.Out.TokenResponse;
import br.com.felipeTarefas.repositories.UsuarioRepository;
import br.com.felipeTarefas.security.UsuarioDetails;
import br.com.felipeTarefas.security.jwt.JwtTokenService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public TokenResponse registrarUsuario(UsuarioDTOin usuarioDTOin) {
        Usuario newUsuario = modelMapper.map(usuarioDTOin, Usuario.class);
        String senhaPura = usuarioDTOin.getPassword();
        newUsuario.setPassword(passwordEncoder.encode(senhaPura));
        log.info("Senha recebida: {}", newUsuario.getPassword());
        usuarioRepository.save(newUsuario);
        log.info("Usuário registrado e salvo no banco.");

        UsuarioDetails usuarioDetails = new UsuarioDetails(newUsuario);
        String token = tokenService.gerarToken(usuarioDetails);
        return new TokenResponse(usuarioDetails.getUsername(), token);
    }

    public TokenResponse login(LoginRequest request){
        Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

            UsuarioDetails usuarioDetails = (UsuarioDetails) auth.getPrincipal();
            String token = tokenService.gerarToken(usuarioDetails);
            return new TokenResponse(usuarioDetails.getUsername(), token);
    }

}
