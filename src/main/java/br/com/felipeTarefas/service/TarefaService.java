package br.com.felipeTarefas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.felipeTarefas.domain.Tarefa;
import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.repositories.TarefaRepository;

public class TarefaService {
    
    @Autowired
    private TarefaRepository tarefaRepository;


    public List<Tarefa> findTarefasByUsuario(Usuario usuario){
        return tarefaRepository.findTarefasByUsuario(usuario);
    }


}
