package br.com.felipeTarefas.domain.dtos.Out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTOout {

    private Long id; // não retornar no sistema direto, colocar só no teste dps
    private String username;
    private String email; 

}
