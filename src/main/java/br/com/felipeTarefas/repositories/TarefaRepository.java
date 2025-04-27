package br.com.felipeTarefas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipeTarefas.domain.Tarefa;
import br.com.felipeTarefas.domain.Usuario;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findTarefasByUsuario_Id(Long usuarioId);
    
    
}
