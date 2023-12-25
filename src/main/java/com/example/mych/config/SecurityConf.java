package com.example.mych.config;

import com.example.mych.services.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConf {
    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(e->e.disable())
                .authorizeHttpRequests(request->request.requestMatchers(antMatcher("/static/style.css"),
                        antMatcher("/api/users/get"),antMatcher("/api/users/create"),antMatcher("/api/users/create/{id}"),
                        antMatcher("/regPage")).permitAll().anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/loginPage")
                        .defaultSuccessUrl("/home",true)
                        .passwordParameter("password")
                        .usernameParameter("email")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return httpSecurity.build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        return  authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder ( ){
        return  new BCryptPasswordEncoder();
    }
}
