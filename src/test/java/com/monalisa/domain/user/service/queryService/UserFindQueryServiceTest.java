package com.monalisa.domain.user.service.queryService;

import com.monalisa.domain.user.exception.UserNotFoundException;
import com.monalisa.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserFindQueryServiceTest {

    @InjectMocks
    private UserFindQueryService userFindQueryService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("findById 에서 찾는 유저가 안나오면 예외를 던진다")
    public void findByIdExceptionTest() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, () -> {
            userFindQueryService.findById(145613L);
        });
    }
}
