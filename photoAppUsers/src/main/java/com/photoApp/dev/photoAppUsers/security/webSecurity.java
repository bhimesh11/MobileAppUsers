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
//
//       http.csrf(AbstractHttpConfigurer::disable)
//               .authorizeHttpRequests(req -> req.requestMatchers(new AntPathRequestMatcher("/users")).permitAll()
//                       .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll())
//               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//       http.headers(header->header.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        //configuring Authentication manger builder

      AuthenticationManagerBuilder managerBuilder =  http.getSharedObject(AuthenticationManagerBuilder.class);

      managerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authManager = managerBuilder.build();

        AuthenticationFilter authFilter = new AuthenticationFilter(userService,environment,authManager);

        authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests//(authorizarion -> authorizarion.requestMatchers(mvc.pattern(HttpMethod.POST,"/users")).permitAll()
                      (authorizarion -> authorizarion.requestMatchers(new AntPathRequestMatcher("/users")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                              .requestMatchers(new AntPathRequestMatcher("/users/check/auth")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users/check/status")).permitAll())
                .authenticationManager(authManager)
                .addFilter(authFilter)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(header->header.frameOptions(frameOptions -> frameOptions.sameOrigin()));
       return http.build();
    }
}
