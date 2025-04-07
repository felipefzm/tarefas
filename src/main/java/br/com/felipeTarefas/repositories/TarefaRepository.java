package br.com.felipeTarefas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipeTarefas.domain.Tarefa;
import br.com.felipeTarefas.domain.Usuario;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Optional<Tarefa> findTarefaById(Long id);
    List<Tarefa> findTarefasByUsuario(Usuario usuario);
    
    
}
