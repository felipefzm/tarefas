package br.com.felipeTarefas.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.felipeTarefas.domain.dtos.ErroDTO;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class) // fazer cenário de teste
    public ResponseEntity<ErroDTO> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException exception) {
        log.warn("Usuario não encontrado: {}", exception.getMessage()); // log de aviso
        ErroDTO erroDTO = new ErroDTO("Usuário não encontrado", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDTO);
    }

    @ExceptionHandler(Exception.class) // fazer cenário de teste
    public ResponseEntity<ErroDTO> handleGeneric(Exception ex) {
        log.error("Erro inesperado", ex); // log do erro com stack trace
        ErroDTO erroDTO = new ErroDTO("Erro inesperado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErroDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException exception){
            log.warn("Requisição mal formatada", exception);
            String detalhes = "Verifique o corpo da requisição. Certifique-se de que todos os valores estejam corretamente formatados e escapados.";
            ErroDTO erroDTO = new ErroDTO("Requisição mal formatada", detalhes);
            return ResponseEntity.badRequest().body(erroDTO);
        }

    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<ErroDTO> handleTarefaNaoEncontrada(TarefaNaoEncontradaException exception){
        log.warn("Tarefa não encontrada: {}", exception.getMessage());
        ErroDTO erroDTO = new ErroDTO("Tarefa não encontrada", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDTO);
    }
}
