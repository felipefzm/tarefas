package br.com.felipeTarefas.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TarefaNaoEncontradaException extends RuntimeException {
    
    public TarefaNaoEncontradaException(Long id){
        super("Tarefa com id "+id+" não foi encontrada");
    }
}
