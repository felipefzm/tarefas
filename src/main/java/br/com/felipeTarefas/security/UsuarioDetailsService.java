package br.com.felipeTarefas.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Username de email:" +email+ "não encontrado"));
            return new UsuarioDetails(usuario);
    }   
    

}
