package br.com.felipeTarefas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.felipeTarefas.config.exceptions.UsuarioNãoEncontradoException;
import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOin;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOout;
import br.com.felipeTarefas.repositories.UsuarioRepository;

@Service
public class UsuarioService extends Exception {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        Sort sort = Sort.by("id").ascending();
        return usuarioRepository.findAll(sort);
    }

    public UsuarioDTOout criarUsuario(UsuarioDTOin usuarioDTOin) {
        Usuario newUsuario = new Usuario();
        newUsuario.setId(usuarioDTOin.getId());
        newUsuario.setCpf(usuarioDTOin.getCpf());
        newUsuario.setEmail(usuarioDTOin.getEmail());
        newUsuario.setUsername(usuarioDTOin.getUsername());
        usuarioRepository.save(newUsuario);
        return UsuarioDTOout.toDTOout(newUsuario);
    }

    public UsuarioDTOout updateUsuario(Long id, UsuarioDTOin usuarioDTOin) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNãoEncontradoException(id));

        if (usuarioDTOin.getUsername() != null) {
            usuario.setUsername(usuarioDTOin.getUsername());
        }
        if (usuarioDTOin.getCpf() != null) {
            usuario.setCpf(usuarioDTOin.getCpf());
        }
        if (usuarioDTOin.getEmail() != null) {
            usuario.setEmail(usuarioDTOin.getEmail());
        }
        usuarioRepository.save(usuario);

        return UsuarioDTOout.toDTOout(usuario);
    }

    public ResponseEntity<Void> deleteUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new UsuarioNãoEncontradoException(id);
        } else {
            usuarioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public Optional<Usuario> findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new UsuarioNãoEncontradoException(id);
        } else
            return usuario;
    }

}
