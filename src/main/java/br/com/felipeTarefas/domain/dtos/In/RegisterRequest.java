package br.com.felipeTarefas.domain.dtos.In;

public record RegisterRequest(String username, String password, String email, String cpfString) {
    
}
