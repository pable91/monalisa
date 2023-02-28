package com.monalisa.global.config.security.util;

import com.monalisa.domain.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public static Optional<User> getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다. 로그인 먼저 해주세요");
            return Optional.empty();
        }

        User user = null;
        if(authentication.getPrincipal() instanceof User) {
            user = (User) authentication.getPrincipal();
        }

        return Optional.ofNullable(user);
    }
}
