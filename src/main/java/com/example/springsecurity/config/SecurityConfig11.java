package com.example.springsecurity.config;

import com.example.springsecurity.config.authentication.details.MyWebAuthenticationDetailsSource;
import com.example.springsecurity.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig11 extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    MyWebAuthenticationDetailsSource authenticationDetailsSource;

    /**
     * 密码加密方式
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 角色权限继承
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    /**
     * "remember-me" 持久化 Repository
     * @return
     */
    @Bean
    JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    HttpFirewall httpFirewall() {
        // 对请求的 URI 的限制，即
        // localhost:8080/sss/hello?name=avalon 中的 /sss/hello 部分
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        // 允许所有请求方式
        firewall.setUnsafeAllowAnyHttpMethod(true);
        // 允许分号 ';'
        firewall.setAllowSemicolon(true);
        // 允许双斜杠 '//'
        firewall.setAllowUrlEncodedDoubleSlash(true);
        // 允许百分号 '%'
        firewall.setAllowUrlEncodedPercent(true);
        // 允许正反斜杠 '/' , '\'
        firewall.setAllowBackSlash(true);
        firewall.setAllowUrlEncodedSlash(true);
        // 允许点 '.'
        firewall.setAllowUrlEncodedPeriod(true);
        return firewall;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/vc.jpg").permitAll()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .antMatchers("/rememberme").rememberMe()
                .antMatchers("/fullyAuth").fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler((req, res, authentication) -> {
                    res.setContentType("application/json; charset=utf8");
                    PrintWriter out = res.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(authentication));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, res, e) -> {
                    res.setContentType("application/json; charset=utf8");
                    PrintWriter out = res.getWriter();
                    out.write(e.getMessage());
                    out.flush();
                    out.close();
                })
                .and()
                .rememberMe()
                // 若不指定 key，则 key 为每次启动应用时获取的 UUID，会使之前派发出去的令牌失效
                .key("avalon")
                // 将用户 token 持久化至数据库中
                .tokenRepository(jdbcTokenRepository())
                .and()
                .csrf().disable()
                .sessionManagement()
                .maximumSessions(1)
                // 默认为 false，即后登录的踢掉先登录的，设为 true 时，则为不允许新的登录
//                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(event -> {
                    HttpServletResponse response = event.getResponse();
                    response.setContentType("application/json; charset=utf8");
                    response.setStatus(401);
                    PrintWriter out = response.getWriter();
                    out.write("您的账号在另一台设备登录，本次登录已下线");
                    out.flush();
                    out.close();
                });
    }

}
