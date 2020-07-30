package com.ea.spring.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.security.Principal;

//@SpringBootApplication
public class JdbcSecurityApplication {

    /*@Bean
    UserDetailsManager memoryManager(DataSource ds) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(ds);
        return jdbcUserDetailsManager;
    }

    @Bean
    InitializingBean initializer(UserDetailsManager manager) {
        return () -> {
            UserDetails kaybee = User.withDefaultPasswordEncoder().username("kaybee").password("password").roles("USER").build();
            manager.createUser(kaybee);

            UserDetails udit = User.withUserDetails(kaybee).username("udit").build();
            manager.createUser(udit);
        };
    }*/


    public static void main(String[] args) {
        SpringApplication.run(JdbcSecurityApplication.class, args);
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

@EnableWebSecurity
@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();

    }
}*/
