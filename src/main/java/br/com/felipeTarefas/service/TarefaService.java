package br.com.felipeTarefas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipeTarefas.config.exceptions.TarefaNaoEncontradaException;
import br.com.felipeTarefas.config.exceptions.UsuarioNaoEncontradoException;
import br.com.felipeTarefas.domain.Tarefa;
import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.TarefaDTO;
import br.com.felipeTarefas.repositories.TarefaRepository;
import br.com.felipeTarefas.repositories.UsuarioRepository;

@Service
public class TarefaService {

    private TarefaRepository tarefaRepository;

    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<TarefaDTO> listarTarefas() {
        return tarefaRepository.findAll().stream()
                .map(TarefaDTO::new).collect(Collectors.toList());
    }

    public TarefaDTO criarTarefa(TarefaDTO tarefaDTO) {

        Tarefa novaTarefa = modelMapper.map(tarefaDTO, Tarefa.class);

        Usuario usuario = usuarioRepository.findById(tarefaDTO.getUsuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(tarefaDTO.getUsuarioId())); // associando usuário manualmente na tarefa

        novaTarefa.setUsuario(usuario); 

        Tarefa tarefaSalva = tarefaRepository.save(novaTarefa);
        return modelMapper.map(tarefaSalva, TarefaDTO.class);

    }

    public List<TarefaDTO> findTarefasByUsuarioId(Long usuarioId) {
        return tarefaRepository.findTarefasByUsuario_Id(usuarioId).stream()
                .map(TarefaDTO::new)
                .collect(Collectors.toList());
    }

    public TarefaDTO atualizaTarefa(Long id, TarefaDTO tarefaDTO) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));

        if (tarefaDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioService.findEntityById(tarefaDTO.getUsuarioId());
            tarefa.setUsuario(usuario);
        }

        modelMapper.map(tarefaDTO, Tarefa.class);

        
        tarefaRepository.save(tarefa);
        
        return new TarefaDTO(tarefa);
    }
    
    // private <T> void updateIfNotNull(T source, Object entidade) { // Método genérico pra reaproveitamento de lógica em outros updates que podem vir a ser nulos, pode ser útil em outro projeto. 
    //     if (source != null) {
        //         modelMapper.map(source, entidade);
        //     }
        // }
        
        // updateIfNotNull(tarefaDTO, tarefa); // usado no atualizaTarefa antes de colocar o mapper direto 
}
