package org.ryuu.learn.springsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.ryuu.learn.springsecurity.dto.exception.RequestExceptionBody;
import org.ryuu.learn.springsecurity.service.impl.JwtService;
import org.ryuu.learn.springsecurity.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UserService userService;

    private final ObjectMapper objectMapper;

    private final String authorizationKey;

    private final String authorizationValuePrefix;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserService userService,
            @Autowired @Qualifier("defaultObjectMapper") ObjectMapper objectMapper,
            @Value("${jwt.authorization.key}") String authorizationKey,
            @Value("${jwt.authorization.value-prefix}") String authorizationValuePrefix
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.authorizationKey = authorizationKey;
        this.authorizationValuePrefix = authorizationValuePrefix;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader(authorizationKey);
        if (authorization == null || !authorization.startsWith(authorizationValuePrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authorization.substring(authorizationValuePrefix.length());
        String userName;
        try {
            userName = jwtService.getSubject(jwt);
        } catch (ExpiredJwtException e) {
            String message = "token expired";
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(
                    response.getOutputStream(),
                    new RequestExceptionBody(HttpStatus.UNAUTHORIZED, message)
            );
            logger.warn(message, e);
            return;
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
