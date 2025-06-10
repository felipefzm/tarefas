package br.com.felipeTarefas.domain.dtos;

import java.time.LocalDateTime;

import br.com.felipeTarefas.domain.Tarefa;
import br.com.felipeTarefas.enums.PrioridadeEnum;
import br.com.felipeTarefas.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {

    private Long id;

    @NotBlank(message = "Nome da tarefa é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição da tarefa é obrigatória")
    private String descricao;


    private LocalDateTime prazo;

    @NotNull(message = "Prioridade é obrigatório")
    private PrioridadeEnum prioridade;


    private StatusEnum status;

    @NotNull(message = "O ID do usuário é obrigatório") // após desenvolver autenticação e associação automática, remover associação manual que está sendo enviada no corpo da requisição. 
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
