package com.example.comicbe.utils;


import com.example.comicbe.jpa.entity.User;
import com.example.comicbe.jpa.repository.UserRepository;
import com.example.comicbe.payload.dto.UserPrincipalOauth2;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.PublicKey;
import java.time.Instant;
import java.util.Objects;

@Component
public class CurrentUser {

    @Autowired
    private UserRepository userRepository;

    public Object getSubject(PublicKey publicKey, String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token);
            return claimsJws.getBody().getSubject();
        } catch (Exception ex) {
            return null;
        }
    }

    public Object getClaimWithKey(String key) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaims().get(key);
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean isValidExpireDateAccessToken() {
        try {
            return this.toInstant(this.getClaimWithKey("exp")).isAfter(Instant.now());
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public UserPrincipalOauth2 currentUser() {
        Object username = this.getClaimWithKey("sub");
        if (Objects.nonNull(username)) {
            User user = userRepository.findByUsername(username.toString());
            if (user != null) {
                return UserPrincipalOauth2.createPrincipalOauth2(user);
            }
        }
        return null;
    }

    public String extractUsernameFromRefreshToken(JwtDecoder jwtDecoder, String refreshToken) {
        try {
            Jwt jwt = jwtDecoder.decode(refreshToken);
            return jwt.getClaims().get("sub").toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isValidExpireDate(JwtDecoder jwtDecoder, String refreshToken) {
        try {
            Jwt jwt = jwtDecoder.decode(refreshToken);
            return this.toInstant(jwt.getClaims().get("exp")).isAfter(Instant.now());
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    private Instant toInstant(Object timestamp) {
        if (timestamp != null) {
            Assert.isInstanceOf(Instant.class, timestamp, "timestamps must be of type Instant");
        }

        return (Instant)timestamp;
    }
}
