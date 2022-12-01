package vn.edu.fpt.authentication.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.edu.fpt.authentication.filter.JwtFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:20
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@Slf4j
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${app.application-context}")
    private String applicationContext;

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    AuthenticationEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated())
                .authenticationManager(authenticationManager)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic(withDefaults());

        http.exceptionHandling().authenticationEntryPoint(authEntryPoint);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web
                .ignoring()
                .antMatchers("/"+applicationContext+"/actuator/health")
                .antMatchers(HttpMethod.POST,"/"+applicationContext+"/public/api/v1/accounts/account")
                .antMatchers(HttpMethod.POST, "/"+applicationContext+"/public/api/v1/accounts/account/login")
                .antMatchers(HttpMethod.POST,"/"+applicationContext+"/public/api/v1/accounts/token/refresh")
                .antMatchers(HttpMethod.POST, "/"+applicationContext+"/public/api/v1/accounts/password/reset");
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
