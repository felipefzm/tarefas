package br.com.felipeTarefas.security.jwt;

import org.springframework.stereotype.Component;

import br.com.felipeTarefas.security.UsuarioDetailsService;

@Component
public class JwtFilter {

    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;

    public JwtFilter(JwtService jwtService, UsuarioDetailsService usuarioDetailsService){
        this.jwtService = jwtService;
        this.usuarioDetailsService = usuarioDetailsService;
    }
    
}
