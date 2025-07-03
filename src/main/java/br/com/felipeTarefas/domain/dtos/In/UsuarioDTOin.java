package br.com.felipeTarefas.domain.dtos.In;

import br.com.felipeTarefas.enums.RoleName;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOin {

    private Long id;

    @NotBlank(message = "Username é obrigatório")
    private String username;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Senha obrigatória")
    private String password;

    private String cpf;

    private RoleName role;

}
