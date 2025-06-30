package br.com.felipeTarefas.security.jwt;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.felipeTarefas.security.UsuarioDetails;

@Service
public class JwtTokenService {

    // trocar pra storage no app.properties
    private final String secretKey = "super-chave-secreta-teste-jwt";
    private Date expiracao = new Date(System.currentTimeMillis() + 3_600_000);
    private Algorithm algoritmo = Algorithm.HMAC256(secretKey);

    public String gerarToken(UsuarioDetails usuarioDetails) {
        try {
            String token = JWT.create().withIssuer("tarefas-auth-api")
                    .withSubject(usuarioDetails.getUsername())
                    .withExpiresAt(expiracao)
                    .sign(algoritmo);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro na autenticação");
        }
    }
    
        public String validateToken(String token){
            try {
                return JWT.require(algoritmo).build()
                .verify(token).getSubject();
            } catch (JWTVerificationException exception) {
                return null;
            }
        }   
}
