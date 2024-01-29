package com.Ega.EgaBankingBackend.security;

import com.Ega.EgaBankingBackend.dto.ClientDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final LoginSuccessHandler loginSuccessHandler;

    private ClientActive clientActive;

    public JwtAuthentificationFilter(AuthenticationManager authenticationManager, LoginSuccessHandler loginSuccessHandler, ClientActive utilisateurActives) {
        this.authenticationManager = authenticationManager;
        this.loginSuccessHandler = loginSuccessHandler;
        this.clientActive = clientActive;
        setFilterProcessesUrl("/utilisateurs/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.err.println("Dans la methode username = " + username + " and password = " + password);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(constants.SIGNING_KEY);
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + constants.ACCESS_TOKEN_VALIDITY_MILLISECONDS))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);

        String jwtRefeshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (15 * 60 * 1000)))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        Map<String, String> idToken = new HashMap<>();
        idToken.put("access-token", jwtAccessToken);
        idToken.put("refresh-token", jwtRefeshToken);
        response.addHeader(constants.HEADER_STRING, jwtAccessToken);
        response.setContentType("application/json");
        loginSuccessHandler.onAuthenticationSuccess(user.getUsername(), request.getRemoteAddr(), request.getRemoteHost());
        List<ClientDTO> logins = clientActive.getLogins();
        ClientDTO connecte = new ClientDTO();
        connecte.setLogin(user.getUsername());
        Integer indexe = -1;
        for (ClientDTO l : logins) {
            System.err.println(l.getLogin());
            System.err.println(connecte.getLogin());
            if (l.getLogin().equalsIgnoreCase(connecte.getLogin())) {
                indexe=logins.indexOf(l);
            }
        }
        System.err.println(indexe);
        if (indexe != -1) {
            Boolean b=    logins.remove(logins.get(indexe));
            System.err.println(b);
        }
        logins.add(connecte);
        new ObjectMapper().writeValue(response.getOutputStream(), idToken);

    }
}
