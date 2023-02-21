package com.monalisa.global.config.security.jwt;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserFindQueryService userFindQueryService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-seconds}")
    private Long tokenValidTime;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(final String accountId) {
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject("access_token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime));

        claims.put("accountId", accountId);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(final HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validateToken(final String jwtToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();

            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(final String token) {
        User user = getUserByToken(token);
        return new UsernamePasswordAuthenticationToken(user, "", null);
    }

    public String getUserPk(final String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("accountId");
    }

    public User getUserByToken(final String token) {
        return userFindQueryService.findByAccountID(getUserPk(token));
    }
}
