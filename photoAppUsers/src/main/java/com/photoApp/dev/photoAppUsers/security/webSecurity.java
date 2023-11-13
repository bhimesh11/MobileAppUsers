package com.photoApp.dev.photoAppUsers.security;

import com.photoApp.dev.photoAppUsers.service.userServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class webSecurity {


    private userServices userService;

    private Environment environment;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public webSecurity(userServices userService, Environment environment, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.environment = environment;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception
    {

        AuthenticationManagerBuilder managerBuilder =  http.getSharedObject(AuthenticationManagerBuilder.class);

        managerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authManager = managerBuilder.build();

        AuthenticationFilter authFilter = new AuthenticationFilter(userService,environment,authManager);

        authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests
                (authorizarion ->
                        authorizarion.requestMatchers(new AntPathRequestMatcher("/users")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users/check/auth")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users/check/status")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users/verify/token")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users-rs/actuator/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/users-rs/users/**")).access(new WebExpressionAuthorizationManager("hasIpAddress('"+environment.getProperty("gateway.ip")+"')"))
//                                .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/actuator/circuitbreakerevents").permitAll()
                        .anyRequest()
                        .authenticated())
                .authenticationManager(authManager)
                .addFilter(new AuthorizationFilter(authManager,environment))
                .addFilter(authFilter)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(header->header.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }
}