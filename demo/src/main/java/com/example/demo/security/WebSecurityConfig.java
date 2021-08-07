package com.example.demo.security;

import com.example.demo.web.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/signup" , "/login","/error","*.jpg").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin() //for form based login so if i disabled line 43, spring's default login page will work
                .loginPage("/login")
                .permitAll()  //instead of hasAnyAuthority
                .defaultSuccessUrl("/myprofile")
                .and()
                .logout()
                .logoutSuccessUrl("/") //it will send me to login by default
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied")
                ;

        http
                .sessionManagement().maximumSessions(1);
        http.headers().frameOptions().disable();

    }


        //.antMatchers("/h2-console/**").permitAll()
        //.antMatchers("/").permitAll() //antMatchers() for match paths, antMatchers(/**) means all paths in the current level and any nested level below this
        //.antMatchers("/login", "/signup", "/")//.hasAnyAuthority("STUDENT", "ADMIN") //maybe profile instead of login
        //instead of <- antMatchers("/**").permitAll() //permitAll() means allow any kind of access
        //we can use <-  antMatchers("/**").hasRole("USER")
        //or <-  antMatchers("/**").hasAnyRole("USER"," blah"," ") <- for multiple roles



    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    //other bean classes
    //1. RedisTemplate is like cashing
    //2. Model mapper -> analyze our object model to determine how data should mapped/

}