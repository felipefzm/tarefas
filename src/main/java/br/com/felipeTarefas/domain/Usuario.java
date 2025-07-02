package br.com.felipeTarefas.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.felipeTarefas.enums.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String cpf;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Tarefa> tarefa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName role = RoleName.USER;


}
