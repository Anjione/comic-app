//package com.example.comicbe.service.auth;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.gson.GsonFactory;
//import com.ielts.server.dto.AuthDTO;
//import com.ielts.server.dto.UserPrincipalOauth2;
//import com.ielts.server.dto.user.VerifyTokenFacebookResp;
//import com.ielts.server.service.AuthService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class AuthServiceImpl implements AuthService {
//
//    @Value("${google.clientid:}")
//    private String googleClientId;
//
//    @Value("${facebook.url.verify:}")
//    private String facebookUrlVerify;
//
//    @Value("${token.expire.seconds:28800}")
//    private Long expireSeconds;
//
//    @Value("${token.refresh.seconds:115200}")
//    private Long expireRefreshSeconds;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private JwtEncoder jwtEncoder;
//
//    @Override
//    public String generateToken(Authentication authentication, UserPrincipalOauth2 userPrincipalOauth2) {
//
//        Instant now = Instant.now();
//
//        String scope = authentication.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuedAt(now)
//                .expiresAt(now.plusSeconds(expireSeconds))
//                .subject(authentication.getName())
//                .claim("scope", scope)
//                .claim("username", userPrincipalOauth2.getUsername())
//                .claim("email", userPrincipalOauth2.getEmail())
//                .claim("premium", userPrincipalOauth2.getIsPremium())
//                .build();
//        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }
//
//    @Override
//    public AuthDTO.RefreshTokenGenResponse generateRefreshToken(UserPrincipalOauth2 userPrincipalOauth2) {
//        Instant now = Instant.now();
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuedAt(now)
//                .expiresAt(now.plusSeconds(expireRefreshSeconds))
//                .subject(userPrincipalOauth2.getUsername())
//                .build();
//        return new AuthDTO.RefreshTokenGenResponse(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(), LocalDateTime.now().plusSeconds(expireRefreshSeconds));
//    }
//
//    @Override
//    public GoogleIdToken.Payload verifyTokenGoogle(String idTokenString) {
//        try {
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
//                    new NetHttpTransport(),
//                    GsonFactory.getDefaultInstance())
//                    .setAudience(Collections.singletonList(googleClientId))
//                    .build();
//
//            GoogleIdToken idToken = verifier.verify(idTokenString);
//            return idToken != null ? idToken.getPayload() : null;
//
//        } catch (Exception e) {
//            log.error("verifyTokenGoogle has error: {} - stackTrace: {}", e.getMessage(), e.getStackTrace());
//            return null;
//        }
//    }
//
//    @Override
//    public VerifyTokenFacebookResp verifyTokenFacebook(String accessTokenFacebook) {
//        String url = facebookUrlVerify + accessTokenFacebook;
//        Map<String, Object> userInfo = restTemplate.getForObject(url, Map.class);
//        return VerifyTokenFacebookResp.builder()
//                .pass(userInfo != null)
//                .userId(userInfo != null && userInfo.get("id") != null ? userInfo.get("id").toString() : null)
//                .email(userInfo != null && userInfo.get("email") != null ? userInfo.get("email").toString() : null)
//                .name(userInfo != null && userInfo.get("name") != null ? userInfo.get("name").toString() : null)
//                .build();
//    }
//}
