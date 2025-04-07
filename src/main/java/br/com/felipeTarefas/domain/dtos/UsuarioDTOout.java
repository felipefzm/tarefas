package br.com.felipeTarefas.domain.dtos;

import br.com.felipeTarefas.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioDTOout {
    private final Long id;
    private final String username;

    public static UsuarioDTOout toDTOout(Usuario usuario){
        return new UsuarioDTOout(usuario.getId(), usuario.getUsername());
    }
}
