package br.com.iot.consumer.api.config.security;

import br.com.iot.consumer.api.config.filter.ContextPathFilter;
import br.com.iot.consumer.api.exception.UnauthenticatedException;
import br.com.iot.consumer.api.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@Profile("!test")
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    private final ContextPathFilter contextPathFilter;

    public WebSecurityConfig(@Value("${spring.webflux.base-path}") final String contextPath) {
        this.contextPathFilter = new ContextPathFilter(contextPath);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .addFilterAt(this.contextPathFilter, SecurityWebFiltersOrder.FIRST)
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().and()
                .logout().disable()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((exchange, denied) -> Mono.error(new UnauthorizedException(denied)))
                .authenticationEntryPoint((exchange, e) -> Mono.error(new UnauthenticatedException(e)))
                .and()
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}user")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user, admin);
    }
}