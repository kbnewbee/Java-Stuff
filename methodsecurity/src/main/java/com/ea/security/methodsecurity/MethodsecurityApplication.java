package com.ea.security.methodsecurity;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import javax.persistence.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@EnableJpaAuditing
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true,
        securedEnabled = true
)
@SpringBootApplication
public class MethodsecurityApplication {

    @Bean
    AuditorAware<String> auditor() {
        return () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                return Optional.ofNullable(authentication.getName());
            }
            return Optional.empty();
        };
    }

    @Bean
    SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    public static void main(String[] args) {
        SpringApplication.run(MethodsecurityApplication.class, args);
    }

}

@Transactional
@Component
@Log4j2
class Runner implements ApplicationRunner {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final AuthorityRepository authorityRepository;
    private final UserDetailsService userDetailsService;

    Runner(UserRepository userRepository, MessageRepository messageRepository, AuthorityRepository authorityRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.authorityRepository = authorityRepository;
        this.userDetailsService = userDetailsService;
    }

    private void authenticate(String username) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Authority user = this.authorityRepository.save(new Authority("USER")),
                admin = this.authorityRepository.save(new Authority("ADMIN"));

        MyUser kaybee = this.userRepository.save(new MyUser("kaybee", "password", admin, user));
        log.info("audited -> {}", this.messageRepository.save(new Message("this is a test bro", kaybee)));

        Message messageK = this.messageRepository.save(new Message("hey yo whatsup !!", kaybee));
        MyUser udit = this.userRepository.save(new MyUser("udit", "password", user));

        this.messageRepository.save(new Message("hey yo guess whos back", kaybee));
        this.messageRepository.save(new Message("hey yo guess whos back again", kaybee));
        this.messageRepository.save(new Message("sup sup sup party on the house", udit));


        Message messageU = this.messageRepository.save(new Message("hey yo broski, udit here nigga !!", udit));

        log.info("kaybee -> {}", kaybee.toString());
        log.info("msg -> {}", messageK);
        log.info("udit -> {} ", udit.toString());
        log.info("msg -> {}", messageU);

        attemptAccess(kaybee.getEmail(), udit.getEmail(),
                messageK.getId(), this.messageRepository::findByIdRolesAllowed);

        attemptAccess(kaybee.getEmail(), udit.getEmail(),
                messageK.getId(), this.messageRepository::findByIdSecured);

        attemptAccess(kaybee.getEmail(), udit.getEmail(),
                messageK.getId(), this.messageRepository::findByIdPreAuthorize);

        attemptAccess(kaybee.getEmail(), udit.getEmail(),
                messageK.getId(), this.messageRepository::findByIdPostAuthorize);

        authenticate(kaybee.getEmail());
        this.messageRepository.findMessagesFor(PageRequest.of(0, 5))
                .forEach(log::info);

        log.info("audited -> {}", this.messageRepository.save(new Message("this is a test bro", kaybee)));

        authenticate(udit.getEmail());
        this.messageRepository.findMessagesFor(PageRequest.of(0, 5))
                .forEach(log::info);

        log.info("audited -> {}", this.messageRepository.save(new Message("this is a test bro", kaybee)));

    }

    private void attemptAccess(String adminUser,
                               String regUser,
                               Long msgId,
                               Function<Long, Message> fn) {
        authenticate(adminUser);
        log.info(msgId);
        log.info("results for kaybee -> {}", fn.apply(msgId));

        try {
            authenticate(regUser);
            log.info("results for udit -> {}", fn.apply(msgId));
        } catch (Throwable e) {
            log.error("oops ! couldnt obtain the results for udit bro");
        }
    }
}

@Service
class UserRepoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    UserRepoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static class MyUserDetails implements UserDetails {
        private final MyUser user;
        private final Set<GrantedAuthority> authorities;

        public MyUserDetails(MyUser user) {
            this.user = user;
            this.authorities = this.user.getAuthorities()
                    .stream()
                    .map(au -> new SimpleGrantedAuthority("ROLE_" + au.getAuthority()))
                    .collect(Collectors.toSet());
        }

        public MyUser getUser() {
            return user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.authorities;
        }

        @Override
        public String getPassword() {
            return this.user.getPwd();
        }

        @Override
        public String getUsername() {
            return this.user.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = this.userRepository.findByEmail(username);
        if (user != null) {
            return new MyUserDetails(user);

        } else {
            throw new UsernameNotFoundException("Cannot find user with username -> " + username);
        }
    }
}

interface MessageRepository extends JpaRepository<Message, Long> {

    String QUERY = "select m from Message m where m.id = ?1";

    @Query(QUERY)
    @RolesAllowed("ROLE_ADMIN")
    Message findByIdRolesAllowed(Long id);

    @Query(QUERY)
    @Secured("ROLE_ADMIN")
    Message findByIdSecured(Long id);

    @Query(QUERY)
    @PreAuthorize("hasRole('ADMIN')")
    Message findByIdPreAuthorize(Long id);

    @Query(QUERY)
    @PostAuthorize("@authz.check(returnObject, principal?.user)")
    Message findByIdPostAuthorize(Long id);

    @Query("select m from Message m where m.to.id = ?#{principal?.user?.id }")
    Page<Message> findMessagesFor(Pageable pageable);
}

@Log4j2
@Service("authz")
class AuthService {
    public boolean check(Message msg, MyUser user) {
        log.info("checking ->" + user.getEmail());
        return msg.getTo().getId().equals(user.getId());
    }
}


interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByEmail(String email);

}

interface AuthorityRepository extends JpaRepository<Authority, Long> {

}


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EntityListeners(AuditingEntityListener.class)
class Message {
    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @OneToOne
    private MyUser to;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date creates;


    public Message(String text, MyUser user) {
        this.text = text;
        this.to = user;
    }
}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "authorities")
@Data
class MyUser {
    @Id
    @GeneratedValue
    private Long id;

    private String email, pwd;

    @ManyToMany(mappedBy = "users")
    private List<Authority> authorities = new ArrayList<>();

    public MyUser(String email, String pwd, Set<Authority> authorities) {
        this.email = email;
        this.pwd = pwd;
        this.authorities.addAll(authorities);
    }

    public MyUser(String email, String pwd) {
        this(email, pwd, new HashSet<>());
    }

    public MyUser(String email, String pwd, Authority... authorities) {
        this(email, pwd, new HashSet<>(Arrays.asList(authorities)));
    }
}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "users")
@Data
class Authority {
    @Id
    @GeneratedValue
    private Long id;

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority(String authority, Set<MyUser> users) {
        this.authority = authority;
        this.users.addAll(users);
    }

    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(name = "authority_user",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<MyUser> users = new ArrayList<>();

}
