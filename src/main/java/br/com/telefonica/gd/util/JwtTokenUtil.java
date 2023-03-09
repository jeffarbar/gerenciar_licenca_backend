package br.com.telefonica.gd.util;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.telefonica.gd.dto.JwtUsuarioDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenUtil implements Serializable {
	
	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 5; // 5 hora

	@Value("${jwt.secret}")
	private String secret;

	public String recuperaToken(String requestTokenHeader) {
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			return requestTokenHeader.substring(7);
		}
		return null;
	}
	
	// recupera o nome de usuário do token jwt
	public String getEmailFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// recupera perfil token
	public JwtUsuarioDto getJwtUsuarioFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		
		LinkedHashMap<String, Object> objectClaims = (LinkedHashMap<String, Object>) claims.get("USER");
		
		JwtUsuarioDto jwtUsuarioDto = new JwtUsuarioDto();
		//jwtUsuarioDto.setIdUser(objectClaims.get("idUser").toString());
		jwtUsuarioDto.setIdCliente(objectClaims.get("idCliente").toString());
		jwtUsuarioDto.setPerfil( objectClaims.get("perfil").toString());
		jwtUsuarioDto.setNome( objectClaims.get("nome").toString());
			
		return jwtUsuarioDto;
	}
	
	// recupera a data de expiração do token jwt
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
    
	//para recuperar qualquer informação do token, precisaremos da chave secreta
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	//verifica se o token expirou
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	//gera token para usuario
	public String generateToken(String nome, String email, String idCliente, String perfil ) {
		Map<String, Object> claims = new HashMap<>();

		JwtUsuarioDto jwtUsuarioDto = new JwtUsuarioDto();
		jwtUsuarioDto.setPerfil(perfil);
		jwtUsuarioDto.setIdCliente(idCliente);
		jwtUsuarioDto.setNome(nome);
		
		claims.put("USER", jwtUsuarioDto );
		
		return doGenerateToken(claims, email);
	}
	
	/*
	public String generateToken(String email, String idUser, String idCliente, boolean isMaster ) {
		Map<String, Object> claims = new HashMap<>();

		JwtUsuarioDto jwtUsuarioDto = new JwtUsuarioDto();
		jwtUsuarioDto.setIdUser(idUser);
		jwtUsuarioDto.setMaster(isMaster);
		jwtUsuarioDto.setIdCliente(idCliente);
		
		claims.put("USER", jwtUsuarioDto );
		
		return doGenerateToken(claims, email);
	}
	*/

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder()				
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (JWT_TOKEN_VALIDITY * 1000)))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	//valida o token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getEmailFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
