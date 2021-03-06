package com.example.tacocloud;

import com.example.tacocloud.user.UserRepository;
import com.example.tacocloud.user.UserRepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final DataSource dataSource;
    private UserDetailsService userDetailsService;

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // Set X-Frame-Options to SameOrigin to make h2-console work
                .headers().frameOptions().sameOrigin()

                .and().authorizeRequests()
                    .antMatchers("/design", "/orders").hasRole("USER")
                    .anyRequest().permitAll()
                .and().httpBasic();
                //.authorizeRequests().anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .withUser("user")
                .password(new BCryptPasswordEncoder().encode("123"))
                .authorities("ROLE_USER")
                .and()
                .withUser("jack")
                .password(new BCryptPasswordEncoder().encode("321"))
                .authorities("ROLE_USER");*/

        /*auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM Users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, authority FROM UserAuthorities WHERE username=?")
        ;*/
        /*auth.ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("member={0}")
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("passcode")
                .and()
                .contextSource()
                .url("ldap://localhost:8389/dc=tacocloud,dc=com");*/
        auth.userDetailsService(userDetailsService);
    }

    @Autowired
    public void setUserDetails(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
