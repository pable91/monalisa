package com.monalisa.global.config.security.jwt;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserFindQueryService userFindQueryService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access_token_expire_time}")
    private Long accessTokenValidTime;

    @Value("${jwt.refresh_token_expire_time}")
    private Long refreshTokenValidTime;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token createToken(final User user) {
        Date now = new Date();

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "ACCESS_TOKEN")
                .setHeaderParam("alg", "HS256")
                .setSubject(user.getAccountID())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .claim("role", user.getRole().toString())
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "REFRESH_TOKEN")
                .setHeaderParam("alg", "HS256")
                .setSubject(user.getAccountID())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .claim("role", user.getRole().toString())
                .signWith(secretKey)
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String resolveToken(final HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validateToken(final String jwtToken, ServletRequest request) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return true;
        } catch(ExpiredJwtException ex) {
            request.setAttribute("ERROR_CODE", JwtErrorCode.EXPIRED);
        }
        return false;
    }

    public Authentication getAuthentication(final String token) {
        User user = getUserByToken(token);
        return new UsernamePasswordAuthenticationToken(user, "", null);
    }

    public String getUserAccountId(final String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("accountId");
    }

    public User getUserByToken(final String token) {
        return userFindQueryService.findByAccountID(getUserAccountId(token));
    }
}
