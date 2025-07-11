package br.com.felipeTarefas.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.felipeTarefas.domain.Usuario;

public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario){
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (usuario.getRole() == null) {
            return Collections.emptyList();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()));
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override 
    public boolean isAccountNonExpired() { 
        return true; 
    }
    @Override 
    public boolean isAccountNonLocked(){ 
        return true; 
    }
    @Override 
    public boolean isCredentialsNonExpired(){ 
        return true; 
    }
    @Override 
    public boolean isEnabled(){ 
        return true; 
    }

    public Usuario getUsuario(){
        return usuario;
    }
    
}