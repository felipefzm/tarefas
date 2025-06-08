package br.com.felipeTarefas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeTarefas.domain.dtos.UsuarioDTOin;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOout;
import br.com.felipeTarefas.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @Operation(description = "Lista todos os usuários do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    
    @GetMapping
    public ResponseEntity<List<UsuarioDTOout>> listarUsuarios() {
        log.info("Requisição de listagem de usuários recebida");
        List<UsuarioDTOout> usuarios = usuarioService.listarUsuarios();
        log.info("Requisição retornou lista de usuários");
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @Operation(description = "Cria um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal-formatada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTOout> criarUsuario(@Valid @RequestBody UsuarioDTOin usuarioDTOin) {
        log.info("Requisição de criação de novo usuário recebida.");
        UsuarioDTOout usuarioCriado = usuarioService.criarUsuario(usuarioDTOin);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @Operation(description = "Atualiza o usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal-formatada"),
            @ApiResponse(responseCode = "404", description = "Recurso inexistente"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTOout> atualizaUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTOin usuarioDTOin) {
        log.info("Requisição de atualização de usuário recebida para o id {}.", id);
        UsuarioDTOout usuarioAtualizado = usuarioService.atualizaUsuario(id, usuarioDTOin);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
    }

    @Operation(description = "Apaga aquele usuário especificado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        log.info("Requisição de exlusão de usuário com id: {}", id);
        usuarioService.deleteUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Encontra aquele usuário específicado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário específico retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping({"/{id}"})
    public ResponseEntity<UsuarioDTOout> findById(@PathVariable Long id) {
        log.info("Requisição de busca de usuário por id: {}", id);
        UsuarioDTOout usuarioDTOout = usuarioService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTOout);
        
        
    }

}
