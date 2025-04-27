package br.com.felipeTarefas.domain.dtos;

import java.time.LocalDateTime;

import br.com.felipeTarefas.domain.Tarefa;
import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.enums.PrioridadeEnum;
import br.com.felipeTarefas.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime prazo;
    private PrioridadeEnum prioridade;
    private StatusEnum status;
    private Long usuarioId;

    public TarefaDTO (Tarefa tarefa){
        this.descricao = tarefa.getDescricao();
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.prazo = tarefa.getPrazo();
        this.prioridade = tarefa.getPrioridade();
        this.status = tarefa.getStatus();
        this.usuarioId = tarefa.getUsuario() != null ? tarefa.getUsuario().getId() : null;
    }
}
