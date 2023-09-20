package com.example.springsecuritysample.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
     * 
     * @Override
     *           protected void doFilterInternal(HttpServletRequest request,
     *           HttpServletResponse response, FilterChain filterChain)
     *           throws ServletException, IOException {
     *           String requestHeader = request.getHeader("Authorization");
     *           logger.info(" Header : {}", requestHeader);
     *           String username = null;
     *           String token = null;
     *           if (requestHeader != null && requestHeader.startsWith("Bearer")) {
     *           token = requestHeader.substring(7);
     *           try {
     *           username = this.jwtService.extractUsername(token);
     *           } catch (IllegalArgumentException e) {
     *           logger.info("Illegal Argument while fetching the username !!");
     *           e.printStackTrace();
     *           } catch (ExpiredJwtException e) {
     *           logger.info("Given jwt token is expired !!");
     *           e.printStackTrace();
     *           } catch (MalformedJwtException e) {
     *           logger.info("Some changed has done in token !! Invalid Token");
     *           e.printStackTrace();
     *           } catch (Exception e) {
     *           e.printStackTrace();
     *           }
     *           } else {
     *           logger.info("Invalid Header Value !! ");
     *           }
     * 
     *           if (username != null &&
     *           SecurityContextHolder.getContext().getAuthentication() == null) {
     *           UserDetails userDetails =
     *           this.userDetailsService.loadUserByUsername(username);
     *           Boolean validateToken = this.jwtService.isTokenValid(token,
     *           userDetails);
     *           if (validateToken) {
     *           UsernamePasswordAuthenticationToken authentication = new
     *           UsernamePasswordAuthenticationToken(
     *           userDetails, null, userDetails.getAuthorities());
     *           authentication.setDetails(new
     *           WebAuthenticationDetailsSource().buildDetails(request));
     *           SecurityContextHolder.getContext().setAuthentication(authentication);
     *           } else {
     *           logger.info("Validation fails !!");
     *           }
     *           }
     *           filterChain.doFilter(request, response);
     *           }
     **/
}
