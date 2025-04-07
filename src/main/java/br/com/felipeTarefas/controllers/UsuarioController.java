package br.com.felipeTarefas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOin;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOout;
import br.com.felipeTarefas.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(description = "Lista todos os usuários do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista sem conteúdo"),
            @ApiResponse(responseCode = "404", description = "Recurso inexistente"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @Operation(description = "Cria um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal-formatada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTOout> criarUsuario(@RequestBody UsuarioDTOin usuarioDTOin) {
        UsuarioDTOout usuarioCriado = usuarioService.criarUsuario(usuarioDTOin);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @Operation(description = "Atualiza o usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal-formatada - Dados Inválidos"),
            @ApiResponse(responseCode = "404", description = "Recurso inexistente"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados - Algum dado já existe no banco"), // testar esse retorno
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PatchMapping
    public UsuarioDTOout updateUsuario(@RequestBody UsuarioDTOin usuarioDTOin) {
        UsuarioDTOout usuarioAtualizado = usuarioService.updateUsuario(usuarioDTOin.getId(), usuarioDTOin);
        return usuarioAtualizado;
    }

    @Operation(description = "Apaga aquele usuário especificado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        return usuarioService.deleteUsuario(id);
    }

    @Operation(description = "Encontra aquele usuário específicado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário específico retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping({ "{id}" })
    public Optional<Usuario> findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

}
