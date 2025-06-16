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

import br.com.felipeTarefas.domain.dtos.TarefaDTOIn;
import br.com.felipeTarefas.domain.dtos.TarefaDTOout;
import br.com.felipeTarefas.service.TarefaService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping()
    public ResponseEntity<TarefaDTOout> criarTarefa(@RequestBody TarefaDTOIn tarefaDTOIn) {
        TarefaDTOout tarefaCriada = tarefaService.criarTarefa(tarefaDTOIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCriada);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTOout>> listaTarefas() {
        List<TarefaDTOout> tarefasListadas = tarefaService.listarTarefas();
        return ResponseEntity.status(HttpStatus.OK).body(tarefasListadas);
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<TarefaDTOout>> listarTarefasPorUsuario(@PathVariable Long usuarioId) {
        List<TarefaDTOout> tarefasListadasPorUsuario = tarefaService.findTarefasByUsuarioId(usuarioId);        
        return ResponseEntity.status(HttpStatus.OK).body(tarefasListadasPorUsuario);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TarefaDTOout> atualizaTarefa(@PathVariable Long id, @Valid @RequestBody TarefaDTOIn tarefaDTOIn){
        TarefaDTOout tarefaAtualizada = tarefaService.atualizaTarefa(id, tarefaDTOIn);
        return ResponseEntity.status(HttpStatus.OK).body(tarefaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaUsuario(@PathVariable Long id){
        tarefaService.deletaTarefa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    
    
    

}
