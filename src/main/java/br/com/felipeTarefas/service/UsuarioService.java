package br.com.felipeTarefas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.felipeTarefas.config.exceptions.UsuarioNaoEncontradoException;
import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.In.UsuarioDTOin;
import br.com.felipeTarefas.domain.dtos.Out.UsuarioDTOout;
import br.com.felipeTarefas.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTOout> listarUsuarios() {
        Sort sort = Sort.by("id").ascending();
        List<Usuario> usuarios = usuarioRepository.findAll(sort);

        if (usuarios.isEmpty()) {
            log.info("Nenhum usuário encontrado na base de dados.");
        } else {
            log.info("Foram encontrados {} usuários.", usuarios.size());
        }

        return usuarios.stream().map(usuario -> modelMapper
                .map(usuario, UsuarioDTOout.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTOout criarUsuario(UsuarioDTOin usuarioDTOin) {
        Usuario newUsuario = modelMapper.map(usuarioDTOin, Usuario.class);
        newUsuario.setPassword(passwordEncoder.encode(usuarioDTOin.getPassword()));
        usuarioRepository.save(newUsuario);
        log.info("Usuário criado e salvo no banco.");
        return modelMapper.map(newUsuario, UsuarioDTOout.class);

    }

    public UsuarioDTOout atualizaUsuario(Long id, UsuarioDTOin usuarioDTOin) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        modelMapper.map(usuarioDTOin, usuario);
        usuarioRepository.save(usuario);
        log.info("Usuário atualizado e salvo.");
        return modelMapper.map(usuario, UsuarioDTOout.class);
    }

    public void deleteUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new UsuarioNaoEncontradoException(id);
        } else {
            usuarioRepository.deleteById(id);
        }
    }

    public UsuarioDTOout findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        return modelMapper.map(usuario, UsuarioDTOout.class);
    }

    public Usuario findEntityById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

}
