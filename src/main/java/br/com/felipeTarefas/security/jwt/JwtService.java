package br.com.felipeTarefas.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.felipeTarefas.security.UsuarioDetails;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
private final String secretKey = "super-chave-secreta-teste-jwt";

public String gerarToken(UsuarioDetails usuarioDetails){
    return Jwts.builder()
    .setSubject(usuarioDetails.getUsername())
    .claim("role", usuarioDetails.getUsuario().getRole().name())
    .setIssuedAt(new Date())
    .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
    .compact();
}

public String getUsernameDoToken(String token){
    return Jwts.parserBuilder()
        .setSigningKey(secretKey.getBytes())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
}

public boolean tokenValido(String token){
    try {
        Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build()
        .parseClaimsJws(token);        
        return true;
    } catch (JwtException e) {
        return false;
    }
}

}
