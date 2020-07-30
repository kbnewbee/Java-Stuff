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
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@SpringBootApplication
public class LoginApplication {

    @Bean
    UserDetailsManager memoryManager() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    InitializingBean initializer(UserDetailsManager manager) {
        return () -> {
            UserDetails kaybee = User.withDefaultPasswordEncoder().username("kaybee").password("password").roles("USER").build();
            manager.createUser(kaybee);

            UserDetails udit = User.withUserDetails(kaybee).username("udit").build();
            manager.createUser(udit);
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

}

@ControllerAdvice
class PrincipalControllerAdvice {

    @ModelAttribute("currentUser")
    Principal principal(Principal p) {
        return p;
    }
}

@Controller
class LoginController {

    @GetMapping("/")
    String index() {
        return "hidden";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/logout-success")
    String logout() {
        return "logout-success";
    }
}


@EnableWebSecurity
@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout-success");

    }
}
