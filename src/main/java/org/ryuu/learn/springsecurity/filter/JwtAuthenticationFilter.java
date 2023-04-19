package org.ryuu.learn.springsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.ryuu.learn.springsecurity.exception.RequestException;
import org.ryuu.learn.springsecurity.service.impl.JwtService;
import org.ryuu.learn.springsecurity.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;

    private final UserService userService;

    private final ObjectMapper objectMapper;

    private final String authorizationKey;

    private final String authorizationValuePrefix;

    private final RoleHierarchy roleHierarchy;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserService userService,
            @Autowired @Qualifier("defaultObjectMapper") ObjectMapper objectMapper,
            @Value("${jwt.authorization.key}") String authorizationKey,
            @Value("${jwt.authorization.value-prefix}") String authorizationValuePrefix,
            @Autowired RoleHierarchy roleHierarchy
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.authorizationKey = authorizationKey;
        this.authorizationValuePrefix = authorizationValuePrefix;
        this.roleHierarchy = roleHierarchy;
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
        String subject = tryGetSubject(jwt, response);
        trySetAuthentication(subject, jwt, request);
        filterChain.doFilter(request, response);
    }

    private String tryGetSubject(String jwt, HttpServletResponse response) throws IOException {
        try {
            return jwtService.getSubject(jwt);
        } catch (ExpiredJwtException expiredJwtException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            RequestException requestException = new RequestException(
                    logger, HttpStatus.UNAUTHORIZED, "token expired", expiredJwtException
            );
            objectMapper.writeValue(response.getOutputStream(), requestException.getRequestExceptionBody());
            throw requestException;
        }
    }

    /**
     * see also {@link AuthorizationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
     */
    private void trySetAuthentication(String userName, String jwt, HttpServletRequest request) {
        if (userName == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        UserDetails userDetails = this.userService.loadUserByUsername(userName);
        if (!jwtService.isTokenValid(jwt, userDetails)) {
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null,
                roleHierarchy.getReachableGrantedAuthorities(userDetails.getAuthorities())
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
