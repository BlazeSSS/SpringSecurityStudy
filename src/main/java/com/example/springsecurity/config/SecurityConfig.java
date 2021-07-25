package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 用户认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("avalon").password("123").roles("admin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略掉的 URL 地址
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/byebye");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                // 使用 form 模式登录，相对于 httpbasic
                .formLogin()
                // 设置登录页面，一般用于前后端未分离的项目，后面一定要加 permitAll()，否则会不断重定向
//                .loginPage("/login.html")
                // 配置 post 登录接口，不配置则默认 url 同 loginPage 一致
//                .loginProcessingUrl("/doLogin")
                // 指定登录接口的用户名参数名，不配置默认为 username
//                .usernameParameter("name")
                // 指定登录接口的用户密码参数名，不配置默认为 password
//                .passwordParameter("passwd")
                // 若登陆前访问其他需要权限的页面，则登录成功后访问该需要权限的页面，否则重定向（客户端跳转）至 defaultSuccessUrl 指定的 url
//                .defaultSuccessUrl("/index")
                // 登录成功后，一律跳转（服务端跳转 / 转发）到 successForwardUrl 指定的 url，主要该 url 是 post 类型的请求
                .successForwardUrl("/index")
                // 登录失败后，重定向（客户端跳转）至 failureUrl 指定的 url
//                .failureUrl("/byebye")
                // 登录失败后，服务端跳转（转发）至 failureForwardUrl 指定的 url
//                .failureForwardUrl("/byebye")
                .permitAll()
                .and()
                .logout()
                // 配置注销的 url，默认为 /logout，是 get 请求
//                .logoutUrl("/doLogout")
                // 配置注销的 url，以及请求方式
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "post"))
                .logoutSuccessUrl("/byebye")
                // 注销后删除 cookie，可指定 cookie 的名字
//                .deleteCookies()
                // 清除认证信息，默认为 true，可不手动配置
//                .clearAuthentication(true)
                // 使 HttpSession，默认为 true，可不手动配置
//                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf().disable();
    }
}
