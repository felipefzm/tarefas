package br.com.felipeTarefas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.felipeTarefas.config.exceptions.UsuarioNãoEncontradoException;
import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOin;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOout;
import br.com.felipeTarefas.repositories.UsuarioRepository;

//TO DO - Trocar HTTps pro controller de novo, arrumar TarefaService e colocar
// path variable no UpdateUsuario

@Service
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

        return usuarios.stream().map(usuario -> modelMapper
                            .map(usuarios, UsuarioDTOout.class))
                            .collect(Collectors.toList());
    }


    public UsuarioDTOout criarUsuario(UsuarioDTOin usuarioDTOin) {
        Usuario newUsuario = modelMapper.map(usuarioDTOin, Usuario.class);
        usuarioRepository.save(newUsuario);
        return modelMapper.map(newUsuario, UsuarioDTOout.class);

    }

    public ResponseEntity<UsuarioDTOout> updateUsuario(Long id, UsuarioDTOin usuarioDTOin) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNãoEncontradoException(id));
                modelMapper.map(usuarioDTOin, usuario); // usuário existente
                usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.OK)
            .body(modelMapper.map(usuario, UsuarioDTOout.class));
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

    public ResponseEntity<UsuarioDTOout> findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new UsuarioNãoEncontradoException(id));
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper
        .map(usuario, UsuarioDTOout.class));
    }

}
