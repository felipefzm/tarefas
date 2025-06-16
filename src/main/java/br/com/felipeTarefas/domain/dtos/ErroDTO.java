package br.com.felipeTarefas.domain.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErroDTO {
    private String erro;
    private String detalhes;
    private LocalDateTime timestamp;

    public ErroDTO(String erro, String detalhes){
        this.erro = erro;
        this.detalhes = detalhes;
        this.timestamp = LocalDateTime.now();
    }
    
}