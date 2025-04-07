package br.com.felipeTarefas.domain.dtos;

import br.com.felipeTarefas.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTOin {

    private Long id;
    private String username;
    private String email;
    private String cpf;

    public UsuarioDTOin(Usuario usuario){
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.cpf = usuario.getCpf();
        this.email = usuario.getEmail();
    }




}
