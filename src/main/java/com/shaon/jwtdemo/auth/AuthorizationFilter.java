package com.shaon.jwtdemo.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            filterChain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null){
             String mtoken = token.replace(SecurityConstants.TOKEN_PREFIX,"");

            Map<String, Object> map = new LinkedHashMap<>();
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.TOKEN_SECRET)
                    .parseClaimsJws(mtoken).getBody();

            if (claims != null) {
                    return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, new ArrayList<>());
                }
        }
        return null;
    }
}
