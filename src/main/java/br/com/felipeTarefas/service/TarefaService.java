package br.com.felipeTarefas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipeTarefas.config.exceptions.TarefaNaoEncontradaException;
import br.com.felipeTarefas.config.exceptions.UsuarioNaoEncontradoException;
import br.com.felipeTarefas.domain.Tarefa;
import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.TarefaDTOIn;
import br.com.felipeTarefas.domain.dtos.TarefaDTOout;
import br.com.felipeTarefas.repositories.TarefaRepository;
import br.com.felipeTarefas.repositories.UsuarioRepository;

@Service
public class TarefaService {

    private TarefaRepository tarefaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<TarefaDTOout> listarTarefas() {
        return tarefaRepository.findAll().stream()
                .map(t -> modelMapper
                .map(t, TarefaDTOout.class))
                .collect(Collectors.toList());
    }

    public TarefaDTOout criarTarefa(TarefaDTOIn tarefaDTOIn) {

        Tarefa novaTarefa = modelMapper.map(tarefaDTOIn, Tarefa.class);

        Usuario usuario = usuarioRepository.findById(tarefaDTOIn.getUsuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(tarefaDTOIn.getUsuarioId())); // associando usuário manualmente na tarefa

        novaTarefa.setUsuario(usuario); 

        Tarefa tarefaSalva = tarefaRepository.save(novaTarefa);
        TarefaDTOout tarefaDTOout = modelMapper.map(tarefaSalva, TarefaDTOout.class);
        tarefaDTOout.setUsuarioId(usuario.getId());
        return tarefaDTOout;

    }

    public List<TarefaDTOout> findTarefasByUsuarioId(Long usuarioId) {
        List<Tarefa> tarefas = tarefaRepository.findTarefasByUsuario_Id(usuarioId);

        return tarefas.stream().map(t -> {
            TarefaDTOout tarefaDTOout = modelMapper.map(t, TarefaDTOout.class);
            tarefaDTOout.setUsuarioId(t.getUsuario().getId());
            return tarefaDTOout;
         })
            .collect(Collectors.toList());
    }

    public TarefaDTOIn atualizaTarefa(Long id, TarefaDTOIn tarefaDTO) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));

        if (tarefaDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioService.findEntityById(tarefaDTO.getUsuarioId());
            tarefa.setUsuario(usuario);
        }

        modelMapper.map(tarefaDTO, Tarefa.class);

        
        tarefaRepository.save(tarefa);
        
        return new TarefaDTOIn(tarefa);
    }

    public void deletaTarefa(Long id){
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isEmpty()) {
            throw new TarefaNaoEncontradaException(id);
        } else {
            tarefaRepository.deleteById(id);
        }
    }
    
}
