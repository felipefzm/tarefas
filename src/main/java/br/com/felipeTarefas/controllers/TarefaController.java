package br.com.felipeTarefas.controllers;

import java.util.List;

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

import br.com.felipeTarefas.domain.dtos.In.TarefaDTOIn;
import br.com.felipeTarefas.domain.dtos.Out.TarefaDTOout;
import br.com.felipeTarefas.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Operation(description = "Cria uma nova tarefa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal-formatada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping()
    public ResponseEntity<TarefaDTOout> criarTarefa(@RequestBody TarefaDTOIn tarefaDTOIn) {
        TarefaDTOout tarefaCriada = tarefaService.criarTarefa(tarefaDTOIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCriada);
    }

    @Operation(description = "Lista todas as tarefas do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Tarefas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public ResponseEntity<List<TarefaDTOout>> listaTarefas() {
        List<TarefaDTOout> tarefasListadas = tarefaService.listarTarefas();
        return ResponseEntity.status(HttpStatus.OK).body(tarefasListadas);
    }

    @Operation(description = "Lista todas as tarefas de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<TarefaDTOout>> listarTarefasPorUsuario(@PathVariable Long usuarioId) {
        List<TarefaDTOout> tarefasListadasPorUsuario = tarefaService.findTarefasByUsuarioId(usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(tarefasListadasPorUsuario);
    }

    @Operation(description = "Atualiza uma tarefa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos/Requisição mal-formatada"),
            @ApiResponse(responseCode = "404", description = "Tarefa inexistente"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TarefaDTOout> atualizaTarefa(@PathVariable Long id,
            @Valid @RequestBody TarefaDTOIn tarefaDTOIn) {
        TarefaDTOout tarefaAtualizada = tarefaService.atualizaTarefa(id, tarefaDTOIn);
        return ResponseEntity.status(HttpStatus.OK).body(tarefaAtualizada);
    }

    @Operation(description = "Deleta uma tarefa do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaTarefa(@PathVariable Long id) {
        tarefaService.deletaTarefa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
