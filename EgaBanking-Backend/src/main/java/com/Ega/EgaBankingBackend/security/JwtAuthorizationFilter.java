package com.Ega.EgaBankingBackend.security;

import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwtAuthprizationToken = request.getHeader(constants.HEADER_STRING);
            System.err.println(" Token: "+jwtAuthprizationToken);
            if (jwtAuthprizationToken == null || !jwtAuthprizationToken.startsWith(constants.TOKEN_PREFIX)){
                filterChain.doFilter(request, response);
                System.err.println(" Filtring entrance request..............");
                return;
            }else {
                String token = jwtAuthprizationToken.replace(constants.TOKEN_PREFIX, "");
                Algorithm algorithm = Algorithm.HMAC256(constants.SIGNING_KEY);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT jwt = JWT.decode(token);
                if( jwt.getExpiresAt().before(new Date())) {
                    System.err.println("token is expired");
                }
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (String st : roles) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(st));
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.addHeader("error-message", e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }
}

