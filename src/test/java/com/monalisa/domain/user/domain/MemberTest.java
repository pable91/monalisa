package com.monalisa.domain.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    @DisplayName("User 생성 테스트")
    public void createUserTest() {
        User newUser = User.of("kim");

        Assertions.assertThat(newUser.getName()).isEqualTo("kim");
    }
}