package com.ejercicio.EggNews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ejercicio.EggNews.servicios.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    public UsuarioService us;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(us).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/Admin/**").hasAnyRole("ADMIN","PERIODISTA")
                .antMatchers("/USER/**").hasAnyRole()
                .antMatchers("/css/**", "/js/**", "/img/**", "/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .csrf()
                .disable();

    }

    // @Bean SPRING 3 EN ADELANTE
    // protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    // {
    // http
    // .authorizeHttpRequests((authz) -> authz
    // .requestMatchers("/js/*","/css/*","/img/*","/**/*" )
    // .permitAll()
    // ).formLogin((login)->login
    // .loginPage("/login")
    // .usernameParameter("")
    // ).logout((logout)->logout
    // .logoutUrl("/logout")
    // ).csrf((csrf)-> csrf.disable());
    // return http.build();
    // }
}