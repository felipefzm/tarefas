package br.com.felipeTarefas.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.felipeTarefas.enums.PrioridadeEnum;
import br.com.felipeTarefas.enums.PrioridadeEnumConverter;
import br.com.felipeTarefas.enums.StatusEnum;
import br.com.felipeTarefas.enums.StatusEnumConverter;
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
    private Long id;

    private String nome;

    private String descricao;

    private LocalDateTime prazo;

    @Column(name = "Status")
    @Convert(converter = StatusEnumConverter.class)
    public StatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @Column(name = "prioridade")
    @Convert(converter = PrioridadeEnumConverter.class)
    private PrioridadeEnum prioridade;

    
    public Long getUsuarioId(){
        return usuario.getId();
    }

}
