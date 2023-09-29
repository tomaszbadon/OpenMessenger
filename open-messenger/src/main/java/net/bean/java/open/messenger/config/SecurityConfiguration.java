package net.bean.java.open.messenger.config;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.filter.CustomAuthenticationFilter;
import net.bean.java.open.messenger.filter.CustomAuthorizationFilter;
import net.bean.java.open.messenger.service.JwtTokenService;
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
    private final JwtTokenService jwtTokenService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests().antMatchers(POST, "/api/users").permitAll();
        http.authorizeHttpRequests().antMatchers(GET, "/api/**").hasRole("USER");
        http.authorizeHttpRequests().antMatchers(POST, "/api/**").hasRole("USER");
        http.authorizeHttpRequests().antMatchers(PATCH, "/api/**").hasRole("USER");
        http.authorizeHttpRequests().anyRequest().denyAll();

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(this.authenticationManagerBean(), jwtTokenService);
        customAuthenticationFilter.setFilterProcessesUrl(CustomAuthenticationFilter.FILTER_PROCESSES_URL);
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(jwtTokenService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
