package com.ea.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//@SpringBootApplication
public class LdapApplication {
    public static void main(String[] args) {
        SpringApplication.run(LdapApplication.class, args);
    }
}
/*
@RestController
class GreetingsRestController {

    @GetMapping("/greetings")
    String greeting(Principal principal) {
        return String.format("Hello %s !!", principal.getName());
    }
}

@Configuration
@EnableWebSecurity
class LdapSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 auth.ldapAuthentication()
                .userDnPatterns(" ou=Accounting")
                .groupSearchBase("ou=Accounting")
                .contextSource()
                .url("ldap://127.0.0.1:8389/dc=willeke,dc=com")
                .and()
                .passwordCompare()
                .passwordAttribute("userPassword")
                .passwordEncoder(new LdapShaPasswordEncoder());


    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }
}*/



