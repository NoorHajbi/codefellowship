package com.example.demo.security;
//ready

import com.example.demo.web.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //to tell that its a Web Security Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean //to do password encoding
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll() //antMatchers() for match paths, antMatchers(/**) means all paths in the current level and any nested level below this
                .antMatchers("/login", "/signup", "/")//.hasAnyAuthority("STUDENT", "ADMIN") //maybe profile instead of login
                .permitAll() //instead of hasAnyAuthority
                .anyRequest().authenticated()
                .and()
                .formLogin() //for form based login
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
        http.csrf().disable();
        http.headers().frameOptions().disable();


        //notes
        //instead of <- antMatchers("/**").permitAll() //permitAll() means allow any kind of access
        //we can use <-  antMatchers("/**").hasRole("USER")
        //or <-  antMatchers("/**").hasAnyRole("USER"," blah"," ") <- for multiple roles
    }
}