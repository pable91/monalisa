package com.monalisa.global.config.security.jwt;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.exception.error.UserErrorCode;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import com.monalisa.global.config.security.jwt.error.JwtErrorCode;
import com.monalisa.global.config.security.jwt.error.exception.InvalidRefreshTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
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
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserFindQueryService userFindQueryService;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access_token_expire_time}")
    private Long accessTokenValidTime;

    private Key key;
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(final User user) {
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

        return accessToken;
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
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException e) {
            request.setAttribute("ERROR_CODE", JwtErrorCode.INCORRECT);
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
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public User getUserByToken(final String token) {
        return userFindQueryService.findByAccountID(getUserAccountId(token));
    }

    public RefreshToken createRefreshToken(final User user) {
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), user.getAccountID());

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public String recreateAccessToken(final String refreshToken, final User user) {
        Optional<RefreshToken> resultToken = refreshTokenRepository.findById(refreshToken);

        if (resultToken.isPresent() && isMyToken(user, resultToken)) {
            return createAccessToken(user);
        }
        throw new InvalidRefreshTokenException(UserErrorCode.INVALID_REFRESH_TOKEN, UserErrorCode.INVALID_REFRESH_TOKEN.getMessage());
    }

    private boolean isMyToken(User user, Optional<RefreshToken> resultToken) {
        return resultToken.get().getAccountId().equals(user.getAccountID());
    }
}
