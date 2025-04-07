package br.com.felipeTarefas.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNãoEncontradoException extends RuntimeException {
    
    public UsuarioNãoEncontradoException(Long id){
        super("Usuário com o id "+id+" não encontrado");
    }


}
