package br.com.felipeTarefas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TarefaService(TarefaRepository tarefaRepository){
        this.tarefaRepository = tarefaRepository;
}

    public List<TarefaDTO> listarTarefas(){
        return tarefaRepository.findAll().stream()
        .map(TarefaDTO::new).collect(Collectors.toList());
    }

    public TarefaDTO criarTarefa(TarefaDTO tarefaDTO){



        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setDescricao(tarefaDTO.getDescricao());
        novaTarefa.setId(null);
        novaTarefa.setNome(tarefaDTO.getNome());
        novaTarefa.setPrazo(tarefaDTO.getPrazo());
        novaTarefa.setPrioridade(tarefaDTO.getPrioridade());
        novaTarefa.setStatus(tarefaDTO.getStatus());

        Usuario usuario = usuarioRepository.findById(tarefaDTO.getUsuarioId())
        .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        novaTarefa.setUsuario(usuario);
        
        Tarefa tarefaSalva = tarefaRepository.save(novaTarefa);

        return new TarefaDTO(tarefaSalva);
    }

    public List<TarefaDTO> findTarefasByUsuarioId(Long usuarioId){
        return tarefaRepository.findTarefasByUsuario_Id(usuarioId).stream()
        .map(TarefaDTO::new)
        .collect(Collectors.toList());
    }

}
