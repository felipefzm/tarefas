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
import br.com.felipeTarefas.domain.dtos.In.TarefaDTOIn;
import br.com.felipeTarefas.domain.dtos.Out.TarefaDTOout;
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
                .orElseThrow(() -> new UsuarioNaoEncontradoException(tarefaDTOIn.getUsuarioId())); 
                // associando usuário manualmente na tarefa

        novaTarefa.setUsuario(usuario);

        Tarefa tarefaSalva = tarefaRepository.save(novaTarefa);
        TarefaDTOout tarefaDTOout = modelMapper.map(tarefaSalva, TarefaDTOout.class);
        tarefaDTOout.setUsuarioId(usuario.getId());
        return tarefaDTOout;

    }

    public List<TarefaDTOout> findTarefasByUsuarioId(Long usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        List<Tarefa> tarefas = tarefaRepository.findTarefasByUsuario_Id(usuarioId);

        return tarefas.stream().map(t -> {
            TarefaDTOout tarefaDTOout = modelMapper.map(t, TarefaDTOout.class);
            tarefaDTOout.setUsuarioId(t.getUsuario().getId());
            return tarefaDTOout;
        })
                .collect(Collectors.toList());
    }

    public TarefaDTOout atualizaTarefa(Long id, TarefaDTOIn tarefaDTOIn) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));

        if (tarefaDTOIn.getUsuarioId() != null) {
            Usuario usuario = usuarioService.findEntityById(tarefaDTOIn.getUsuarioId());
            tarefa.setUsuario(usuario);
        }

        modelMapper.map(tarefaDTOIn, tarefa);
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        TarefaDTOout tarefaDTOout = modelMapper.map(tarefaSalva, TarefaDTOout.class);
        tarefaDTOout.setUsuarioId(tarefaSalva.getUsuario().getId());
        return tarefaDTOout;
    }

    public void deletaTarefa(Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isEmpty()) {
            throw new TarefaNaoEncontradaException(id);
        } else {
            tarefaRepository.deleteById(id);
        }
    }

}
