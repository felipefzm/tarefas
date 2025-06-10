package br.com.felipeTarefas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.felipeTarefas.config.exceptions.UsuarioNaoEncontradoException;
import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOin;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOout;
import br.com.felipeTarefas.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

// TO DO - Arrumar e revisar camadas de Tarefas
// TO DO - Testes unitários
// TO DO - Terminar de implementar logs
// TO DO - Começar autenticação com JWT
// TO DO - Dividir DTOs de tarefas e associar automaticamente tarefa criada ao usuário logado

@Service
@Slf4j
public class UsuarioService {

    private UsuarioRepository usuarioRepository; 

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

    public List<UsuarioDTOout> listarUsuarios() { 
        Sort sort = Sort.by("id").ascending();
        List<Usuario> usuarios = usuarioRepository.findAll(sort);

        if (usuarios.isEmpty()) {
            log.info("Nenhum usuário encontrado na base de dados.");
        } else {
            log.info("Foram encontrados {} usuários.", usuarios.size());
        }

        return usuarios.stream().map(usuario -> modelMapper
                            .map(usuarios, UsuarioDTOout.class))
                            .collect(Collectors.toList());
    }


    public UsuarioDTOout criarUsuario(UsuarioDTOin usuarioDTOin) {
        Usuario newUsuario = modelMapper.map(usuarioDTOin, Usuario.class);
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

    public Usuario findEntityById(Long id){
        return usuarioRepository.findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

}
