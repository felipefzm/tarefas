package br.com.felipeTarefas.domain.dtos;

import java.time.LocalDateTime;

import br.com.felipeTarefas.enums.PrioridadeEnum;
import br.com.felipeTarefas.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TarefaDetalheDTOOut {
    private String nome;

    private String descricao;

    private LocalDateTime prazo;

    private StatusEnum statusEnum;
    
    private PrioridadeEnum prioridade;
}