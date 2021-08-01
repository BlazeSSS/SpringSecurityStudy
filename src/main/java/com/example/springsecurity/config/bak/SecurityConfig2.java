package com.example.springsecurity.config.bak;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

//@Configuration
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("123").roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                // 登录成功的回调
                .successHandler((req, res, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    res.setContentType("application/json; charset=utf-8");
                    PrintWriter out = res.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                // 登录失败的回调
                .failureHandler((req, res, e) -> {
                    res.setContentType("application/json; charset=utf-8");
                    PrintWriter out = res.getWriter();
                    out.write(e.getMessage());
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler((req, res, authentication) -> {
                    res.setContentType("application/json; charset=utf-8");
                    PrintWriter out = res.getWriter();
                    out.write("注销成功");
                    out.flush();
                    out.close();
                })
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((req, res, authException) -> {
                    res.setContentType("application/json; charset=utf-8");
                    PrintWriter out = res.getWriter();
                    out.write("请先登录");
                    out.flush();
                    out.close();
                });
    }
}
