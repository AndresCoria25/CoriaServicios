/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria;

import Coria.servicios.ProveedorServicio;
import Coria.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import seguridad.proveedorSecurity;

/**
 *
 * @author FraNko
 */

  @Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    // Agrega este método
    @Bean
    public proveedorSecurity proveedorSecurity() {
        return new proveedorSecurity();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
        auth.userDetailsService(proveedorServicio).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/", "/index.css", "/login","/reset-password", "/forgot-password", "/login.css",
                        "/registrar", "/registrarP", "/registro", "/registroP", "/registro.css", "/registroP.css",
                        "/contacto", "/contacto.css", "/informacion", "/informacion.css",
                        "/calificacion", "/calificacion.css",
                        "/css/**", "/resources/**", "/static/**", "/js/**", "/imagenes/**", "/Sunday.ttf"
                ).permitAll()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/usuario/**").hasRole("USUARIO")
                .antMatchers("/proveedor/**").hasRole("PROVEEDOR")
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/logincheck")
                .defaultSuccessUrl("/informacion")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .csrf()
                .disable();
    }
}
