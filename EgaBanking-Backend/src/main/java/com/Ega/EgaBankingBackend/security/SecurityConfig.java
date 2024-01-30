package com.Ega.EgaBankingBackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final LoginSuccessHandler loginSuccessHandler;
    private final UserDetailsService userService;
    private final AuthenticationConfiguration configuration;

    public SecurityConfig(LoginSuccessHandler loginSuccessHandler, UserDetailsService userService, AuthenticationConfiguration configuration) {
        //this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.userService = userService;
        this.configuration = configuration;
    }

    @Bean
    //@ApplicationScope
    public ClientActive utilisateurActiveBean(){
        return  new ClientActive();
    }


    //@Lazy
    //@Bean
    //public AuthenticationManager authenticationManagerBean() {
    //  return authenticationManager;
    //return super.authenticationManagerBean();
    //}


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        //http.csrf().disable();
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement((sessionManagement) ->
                sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/clients/**").permitAll());
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/comptes/**").permitAll());
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/debit/**").permitAll());
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/credit/**").permitAll());
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/transfer/**").permitAll());
        //http.authorizeRequests().anyRequest().authenticated();//Si j'enlève cette ligne, je pourrai avoir accès à tout sans authentification
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new JwtAuthentificationFilter(authenticationManager(), loginSuccessHandler, utilisateurActiveBean()));

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Autowired
    void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
