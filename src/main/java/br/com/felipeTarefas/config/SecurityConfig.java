package br.com.felipeTarefas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import br.com.felipeTarefas.security.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;

    public SecurityConfig(UsuarioDetailsService usuarioDetailsService){
        this.usuarioDetailsService = usuarioDetailsService;
    }
}
