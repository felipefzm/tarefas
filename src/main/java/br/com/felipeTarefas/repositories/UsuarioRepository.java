package br.com.felipeTarefas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.domain.dtos.UsuarioDTOout;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<UsuarioDTOout> findUsuarioByUsername(String username);


}
