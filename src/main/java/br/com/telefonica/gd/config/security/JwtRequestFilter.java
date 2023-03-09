package br.com.telefonica.gd.config.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.telefonica.gd.service.AutenticacaoService;
import br.com.telefonica.gd.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private AutenticacaoService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		
		final String requestTokenHeader = request.getHeader("Authorization");
		
		System.out.println("VALOR "+ requestTokenHeader);
		
		String email = null;

		// Token JWT está no formato "Bearer token". Remova a palavra do portador e obtenha
		// somente o Token
		
		String jwtToken = jwtTokenUtil.recuperaToken(requestTokenHeader);
		
		if (jwtToken != null) {
			try {
				email = jwtTokenUtil.getEmailFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.warn("Não foi possível obter o token JWT");
			} catch (ExpiredJwtException e) {
				logger.warn("Token JWT expirou");
			}
		} else {
			logger.warn("Token JWT não começa com Bearer");
		}

		// Depois de obter o token, valide-o.
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(email);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Depois de definir a Autenticação no contexto, especificamos
				// que o usuário atual é autenticado. Assim passa o
				// Spring Security Configurations com sucesso.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

}