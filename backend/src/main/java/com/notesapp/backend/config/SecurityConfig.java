package com.notesapp.backend.config;

import com.notesapp.backend.config.jwt.JwtAuthFilter;
import com.notesapp.backend.utils.exceptions.config.EnvironmentVariableNotSetException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.notesapp.backend.config.constants.SecurityConfigConstants.ENV_FRONTEND_URL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OidcUserService oidcUserService;

    private final DefaultOAuth2UserService oAuth2UserService;

    private final AuthenticationSuccessHandler oAuthSuccessHandler;

    private final JwtAuthFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        String allowedOrigin = System.getenv(ENV_FRONTEND_URL);

        if(allowedOrigin == null) {
            throw new EnvironmentVariableNotSetException(ENV_FRONTEND_URL);
        }

        configuration.setAllowedOrigins(List.of(allowedOrigin));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/api/auth/**", "/oauth2/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .oauth2Login(auth -> auth
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/oauth2/callback/**"))
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService)
                                .userService(oAuth2UserService))
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/oauth2/authorize")
                                .authorizationRequestRepository(customAuthorizationRequestRepository()))
                        .successHandler(oAuthSuccessHandler))
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> customAuthorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }
}
