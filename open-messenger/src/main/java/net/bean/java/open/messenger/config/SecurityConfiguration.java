package net.bean.java.open.messenger.config;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.filter.AllowedPathChecker;
import net.bean.java.open.messenger.filter.CustomAuthenticationFilter;
import net.bean.java.open.messenger.filter.CustomAuthorizationFilter;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AllowedPathChecker allowedPathChecker;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests().antMatchers(GET, "/", "/webjars/**").permitAll();
        http.authorizeHttpRequests().antMatchers("/stomp-endpoint/**").permitAll();
        http.authorizeHttpRequests().antMatchers(GET, "/api/**").hasRole("USER");
        http.authorizeHttpRequests().antMatchers(POST, "/api/**").hasRole("USER");
        http.authorizeHttpRequests().antMatchers(PATCH, "/api/**").hasRole("USER");
        http.authorizeHttpRequests().anyRequest().denyAll();

        http.addFilter(new CustomAuthenticationFilter(this.authenticationManagerBean(), jwtTokenService));
        http.addFilterBefore(new CustomAuthorizationFilter(jwtTokenService, allowedPathChecker), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}