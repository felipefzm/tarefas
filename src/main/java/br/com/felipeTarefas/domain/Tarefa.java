package br.com.felipeTarefas.domain;

import java.time.LocalDateTime;

import br.com.felipeTarefas.enums.PrioridadeEnumConverter;
import br.com.felipeTarefas.enums.StatusEnum;
import br.com.felipeTarefas.enums.prioridadeEnum;
import br.com.felipeTarefas.enums.statusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nome;

    public String descricao;

    public LocalDateTime prazo;

    @Column(name = "Status")
    @Convert(converter = StatusEnum.class)
    public statusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    public Usuario usuario;

    @Column(name = "prioridade")
    @Convert(converter = PrioridadeEnumConverter.class)
    public prioridadeEnum prioridade;

    

}
