package com.nadarzy.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/** Created by Julian Nadarzy on 15/09/2021 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery(
            "select username, password, enabled" + " from users " + "where username = ?")
        .authoritiesByUsernameQuery(
            "select username, authority from authorities where username = ?");

    // to preload some users into db

    //        .withDefaultSchema()
    //        .withUser(User.withUsername("user").password("pass").roles("USER"))
    //        .withUser(User.withUsername("admin").password("pass").roles("ADMIN"));

    // without jdbc, just provide user/pw/role combo

    //    auth.inMemoryAuthentication()
    //        .withUser("blah")
    //        .password("blah")
    //        .roles("USER")
    //        .and()
    //        .withUser("foo")
    //        .password("foo")
    //        .roles("ADMIN");

  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/admin")
        .hasRole("ADMIN")
        .antMatchers("/user")
        .hasAnyRole("USER", "ADMIN")
        .antMatchers("/", "static/css", "static/js")
        .permitAll()
        .and()
        .formLogin();
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
    //        return new BCryptPasswordEncoder();
  }
}
