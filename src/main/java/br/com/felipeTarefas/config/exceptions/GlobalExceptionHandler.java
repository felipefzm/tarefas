package br.com.felipeTarefas.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class) // fazer cenário de teste
    public ResponseEntity<String> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException exception) {
        log.warn("Usuario não encontrado: {}", exception.getMessage()); // log de aviso
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class) // fazer cenário de teste
    public ResponseEntity<String> handleGeneric(Exception ex) {
        log.error("Erro inesperado", ex); // log do erro com stack trace
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception){
            return ResponseEntity.badRequest().body("Requisição mal formatada: " +exception.getMostSpecificCause()
            .getMessage());
    }

    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<String> handleTarefaNaoEncontrada(TarefaNaoEncontradaException exception){
        log.warn("Tarefa não encontrada: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
