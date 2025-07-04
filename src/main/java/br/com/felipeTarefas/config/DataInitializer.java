package br.com.felipeTarefas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.felipeTarefas.domain.Usuario;
import br.com.felipeTarefas.enums.RoleName;
import br.com.felipeTarefas.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInitializer {

    @Value("${admin.default.email}")
    private String adminEmail;

    @Value("${admin.default.password}")
    private String adminPassword;

    @Bean
    public CommandLineRunner inicializarAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return mov -> {
            if (usuarioRepository.findUsuarioByEmail(adminEmail).isEmpty()) {
                Usuario newAdmin = new Usuario();
                newAdmin.setUsername("ADM");
                newAdmin.setCpf("000000000-11");
                newAdmin.setEmail(adminEmail);
                newAdmin.setRole(RoleName.ADMIN);
                newAdmin.setPassword(passwordEncoder.encode(adminPassword));
                usuarioRepository.save(newAdmin);
                log.info("Admin inicial criado com sucesso");
            }
        };
    }

}