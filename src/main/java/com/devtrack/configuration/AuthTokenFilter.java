package com.devtrack.configuration;

import com.devtrack.utils.CustomUserDetailsService;
import com.devtrack.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try{
            // No procesar solicitudes con el token de confirmación de cuenta
            if (request.getRequestURI().contains("/api/auth/confirm-account")) {
                // No hacer nada, continuar con el filtro
                filterChain.doFilter(request, response);
                return;
            }
            String jwt = parseJwt(request);
            //Verify token is not null and validate de sign
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                //Get the email for load the user
                String email = jwtUtil.getEmailFromToken(jwt);
                //Loading user by email and saving UserDetails
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);//By email
                //Creating authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(userDetails);
                //Saving the context for validate all the endpoints
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            System.out.println("Cannot set user authentication" + e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        System.out.println("Este es el header authorization: " + headerAuth);
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            //Getting token deleting Bearer text
            return headerAuth.substring(7);
        }
        return null;
    }
}
