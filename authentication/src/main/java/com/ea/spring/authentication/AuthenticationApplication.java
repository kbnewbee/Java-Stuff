package com.ea.spring.authentication;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class AuthenticationApplication {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //    @Bean
    PasswordEncoder oldpasswordEncoder() {
        String md5 = "MD5";
        return new DelegatingPasswordEncoder(md5,
                Collections.singletonMap(md5, new MessageDigestPasswordEncoder("MD5")));
    }

    @Bean
    CustomUserDetailsService customUserDetailsService() {
        Collection<UserDetails> users = Arrays.asList(
                new CustomUserDetails("kaybee", oldpasswordEncoder().encode("password"), true, "USER"),
                new CustomUserDetails("udit", oldpasswordEncoder().encode("password"), true, "USER", "ADMIN")
        );
        return new CustomUserDetailsService(users);
    }


    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

}

@RestController
class GreetingRest {
    @GetMapping("/greeting")
    public String greet(Principal p) {
        return "greeting, " + p.getName();
    }
}

@Configuration
@EnableWebSecurity
class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

/*    private final CustomAuthenticationProvider ap;

    CustomSecurityConfiguration(CustomAuthenticationProvider ap) {
        this.ap = ap;
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }

 /*   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.ap);
    }*/
}

@Log4j2
class CustomUserDetailsService implements UserDetailsService,
        UserDetailsPasswordService {

    private final Map<String, UserDetails> users = new ConcurrentHashMap<>();

    public CustomUserDetailsService(Collection<UserDetails> seedUsers) {
        seedUsers.forEach(user -> this.users.put(user.getUsername(), user));
        this.users.forEach((k, v) -> log.info(k + "=" + v.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (this.users.containsKey(username)) {
            return this.users.get(username);
        }
        throw new UsernameNotFoundException(String.format("couldnt find %s ", username));
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {

        log.info("prompted to provide new password");
        this.users.put(userDetails.getUsername(), new CustomUserDetails(
                userDetails.getUsername(),
                newPassword,
                userDetails.isEnabled(),
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new)
        ));
        return this.loadUserByUsername(userDetails.getUsername());
    }
}

class CustomUserDetails implements UserDetails {

    private final Set<GrantedAuthority> authorities;
    private final String username, password;
    private final boolean active;

    CustomUserDetails(String username, String password, boolean active, String... authorities) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.authorities = Stream
                .of(authorities)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}

/*@Component
class CustomAuthenticationProvider implements AuthenticationProvider {

    private final List<SimpleGrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("USER"));

    private boolean isValid(String user, String pwd) {
        return user.equals("kaybee") && pwd.equals("password");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        if (isValid(username, pwd)) {
            return new UsernamePasswordAuthenticationToken(username, pwd, this.authorities);
        }
        throw new BadCredentialsException("Couldnt authenticate using" + username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}*/
