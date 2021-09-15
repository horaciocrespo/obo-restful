package com.obo.oborestfulapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Profile("test")
@Configuration
@ConditionalOnProperty(name = "obo.security.enable", havingValue = "true")
@EnableWebSecurity
public class TestWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // https://spring.io/guides/gs/securing-web/
        // form based
//        http
//                .csrf().disable()
//                // Configures access to the routes
//                .authorizeRequests()
//                    // "/" and "/home" are public
//                    .antMatchers("/", "/home").permitAll()
//                    // any other request, only accessible for authenticated users.
//                    .anyRequest().authenticated()
//                // Get HttpSecurity again
//                .and()
//                    // get the configurer
//                    .formLogin()
//                        .loginPage("/login")
//                        // ???
//                        .permitAll()
//                // Get HttpSecurity again
//                .and()
//                    .logout()
//                        .permitAll();

        // basic auth
        http
                .csrf().disable()
                // Configures access to the routes
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .antMatchers("/orders/**").authenticated()
                .and().httpBasic()
                // any other request, only accessible for authenticated users.
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // mysql
        auth.userDetailsService(this.userDetailsService);
    }
}
