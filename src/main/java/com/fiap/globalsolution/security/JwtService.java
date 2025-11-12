package com.fiap.globalsolution.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Service para geração e validação de tokens JWT
 */
@Service
public class JwtService {

    private final Key signingKey;
    private final long expirationMs;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long expiration) {
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(secret.getBytes()));
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expiration;
    }

    /**
     * Gera um token JWT para o usuário
     */
    public String generateToken(String username, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .addClaims(extraClaims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrai o username do token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Valida se o token é válido para o usuário
     */
    public boolean isTokenValid(String token, String username) {
        String sub = extractUsername(token);
        return sub.equals(username) && !isTokenExpired(token);
    }

    /**
     * Extrai um claim específico do token
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }

    /**
     * Verifica se o token está expirado
     */
    private boolean isTokenExpired(String token) {
        Date exp = extractClaim(token, Claims::getExpiration);
        return exp.before(new Date());
    }
}