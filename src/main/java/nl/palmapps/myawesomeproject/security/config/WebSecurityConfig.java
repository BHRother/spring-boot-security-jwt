package nl.palmapps.myawesomeproject.security.config;

import nl.palmapps.myawesomeproject.security.JwtAuthenticationEntryPoint;
import nl.palmapps.myawesomeproject.security.JwtAuthenticationProvider;
import nl.palmapps.myawesomeproject.security.JwtAuthenticationSuccessHandler;
import nl.palmapps.myawesomeproject.security.JwtAuthenticationTokenFilter;
import nl.palmapps.myawesomeproject.security.handler.CustomAuthenticationFailureHandler;
import nl.palmapps.myawesomeproject.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        authenticationTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                // All urls must be authenticated (filter for token always fires (/api/**)
                .authorizeRequests().antMatchers("/api/**").authenticated()
                .and()
                // Call our errorHandler if authentication/authorisation fails
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                .and()
                // don't create session (REST)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Custom JWT based security filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity.headers().cacheControl();

        httpSecurity
            .formLogin()
            .loginProcessingUrl("/loginForm")
            .successHandler(customAuthenticationSuccessHandler)
            .failureHandler(new CustomAuthenticationFailureHandler());
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        //Default users to grant access
        authenticationManagerBuilder
            .inMemoryAuthentication()
            .withUser("user").password("test123").authorities("USER").and()
            .withUser("admin").password("test123").authorities("ADMIN");

        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
