package com.learnandcode.news_aggregator.security.filters;

import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.service.UserService;
import com.learnandcode.news_aggregator.service.util.JwtUtils;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        String jwtToken = request.getHeader("Authorization");

        if(jwtToken==null){
            filterChain.doFilter(request,response);
        }else{
            jwtToken=jwtToken.substring(7);
            boolean isTokenValid = jwtUtils.validateToken(jwtToken);
            if(isTokenValid){
                String username = jwtUtils.extractUsername(jwtToken);
                User user = userService.getUserByUserName(username);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
    }
}
