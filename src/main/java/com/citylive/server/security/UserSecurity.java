package com.citylive.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class UserSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(rawPassword);
            }
        })
                .usersByUsernameQuery("select \"UserName\",\"Password\", true as enabled from \"CityLiveDev\".\"User\" where \"UserName\"=?")
                .authoritiesByUsernameQuery("select \"UserName\",\"Role\" from \"CityLiveDev\".\"User\" where \"UserName\"=?");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/user")
                .hasAnyRole("USER", "ADMIN").antMatchers("user/all").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/jpa/find").hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin()
                .permitAll().and().logout().permitAll();

        http.csrf().disable();
    }
}
