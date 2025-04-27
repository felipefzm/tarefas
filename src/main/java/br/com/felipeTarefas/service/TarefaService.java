package br.com.felipeTarefas.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipeTarefas.config.exceptions.UsuarioNãoEncontradoException;
import br.com.felipeTarefas.domain.Tarefa;
import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.TarefaDTO;
import br.com.felipeTarefas.repositories.TarefaRepository;
import br.com.felipeTarefas.repositories.UsuarioRepository;

@Service
public class TarefaService {

    private TarefaRepository tarefaRepository;

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

        Tarefa novaTarefa = new Tarefa();

        novaTarefa.setDescricao(tarefaDTO.getDescricao());
        novaTarefa.setNome(tarefaDTO.getNome());
        novaTarefa.setPrazo(tarefaDTO.getPrazo());
        novaTarefa.setPrioridade(tarefaDTO.getPrioridade());
        novaTarefa.setStatus(tarefaDTO.getStatus());

        Usuario usuario = usuarioService.findById(tarefaDTO.getUsuarioId());

        novaTarefa.setUsuario(usuario);

        Tarefa tarefaSalva = tarefaRepository.save(novaTarefa);

        return new TarefaDTO(tarefaSalva);
    }

    public List<TarefaDTO> findTarefasByUsuarioId(Long usuarioId) {
        return tarefaRepository.findTarefasByUsuario_Id(usuarioId).stream()
                .map(TarefaDTO::new)
                .collect(Collectors.toList());
    }

    public TarefaDTO updateUsuario(Long id, TarefaDTO tarefaDTO) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new UsuarioNãoEncontradoException(id));

        if (tarefaDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioService.findById(tarefaDTO.getUsuarioId());
            tarefa.setUsuario(usuario);
        }

        updateIfNotNull(tarefaDTO.getNome(), tarefa::setNome);
        updateIfNotNull(tarefaDTO.getDescricao(), tarefa::setDescricao);
        updateIfNotNull(tarefaDTO.getPrazo(), tarefa::setPrazo);
        updateIfNotNull(tarefaDTO.getPrioridade(), tarefa::setPrioridade);
        updateIfNotNull(tarefaDTO.getStatus(), tarefa::setStatus);

        tarefaRepository.save(tarefa);

        return new TarefaDTO(tarefa);
    }

    // if (tarefaDTO.getNome() != null){
    // tarefa.setNome(tarefaDTO.getNome());
    // }
    // if (tarefaDTO.getDescricao() != null) {
    // tarefa.setDescricao(tarefaDTO.getDescricao());
    // }
    // if (tarefaDTO.getPrazo() != null) {
    // tarefa.setPrazo(tarefaDTO.getPrazo());
    // }
    // if (tarefaDTO.getPrioridade() != null) {
    // tarefa.setPrioridade(tarefaDTO.getPrioridade());
    // }
    // if (tarefaDTO.getStatus() != null) {
    // tarefa.setStatus(tarefaDTO.getStatus());
    // }

    private <T> void updateIfNotNull(T DTOIn, Consumer<T> entidade) {
        if (DTOIn != null) {
            entidade.accept(DTOIn);
        }
    }

}
