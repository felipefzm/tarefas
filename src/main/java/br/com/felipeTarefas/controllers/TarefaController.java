package br.com.felipeTarefas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeTarefas.domain.dtos.TarefaDTOIn;
import br.com.felipeTarefas.domain.dtos.TarefaDTOout;
import br.com.felipeTarefas.service.TarefaService;


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
    public List<TarefaDTOout> listaTarefas() {
        return tarefaService.listarTarefas();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<TarefaDTOout> listarTarefasPorUsuario(@PathVariable Long usuarioId) {
        return tarefaService.findTarefasByUsuarioId(usuarioId);        
    }

    
    
    

}
