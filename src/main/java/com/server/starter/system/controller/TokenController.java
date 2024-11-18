package com.server.starter.system.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * A controller for the token resource.
 *
 * @author Josh Cummings
 */
@RestController
public class TokenController {

    @Autowired
    JwtEncoder encoder;

    @PostMapping("/signin")
    public ResponseEntity<String> token(Authentication authentication, HttpServletResponse response) {
        Instant now = Instant.now();
        long expiry = 36000L;
        // @formatter:off
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        // @formatter:on
        String jwtToken = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        // 设置 Cookie
        response.setHeader("Set-Cookie", "logged_in=" + authentication.getName() +
                "; HttpOnly; Secure; SameSite=Lax; Max-Age=86400; Path=/");

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> logout(Authentication authentication, HttpServletResponse response) {
        // 清除 Cookie
        response.setHeader("Set-Cookie", "logged_in=; HttpOnly; Secure; SameSite=Lax; Max-Age=0; Path=/");

        authentication.setAuthenticated(false);
        return ResponseEntity.ok().build();
    }

}
