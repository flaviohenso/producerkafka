package com.example.demo.core.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Configures the HTTP security for the application.
     * 
     * @param http the HttpSecurity object to be configured
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(
                        csrf -> csrf.disable() // Desabilita CSRF
                ) // desabilita CSRF - Cross Site Request Forgery
                .httpBasic(Customizer.withDefaults()) // Habilita HTTP Basic com configurações padrão
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/send").permitAll() // Permite acesso a /produtos sem
                                                                                  // autenticação para o metodo Get
                        .anyRequest()
                        .authenticated() // Exige autenticação para todas as requisições
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Defini
                                                                                                               // o
                                                                                                               // SessionCreationPolicy
                                                                                                               // como
                                                                                                               // STATELESS
        return http.build();
    }

    /**
     * Configure user inmemory.
     * Only used for testing or development purposes.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = withUsername("usuario")
                .password(passwordEncoder().encode("senha"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Method that uses bcrypt to encrypt the password.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
