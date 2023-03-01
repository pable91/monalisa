package com.monalisa.domain.user.service.queryService;

import com.monalisa.domain.order.domain.Order;
import com.monalisa.domain.order.repository.OrderRepository;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.exception.UserNotFoundException;
import com.monalisa.domain.user.exception.error.UserErrorCode;
import com.monalisa.domain.user.repository.UserRepository;
import com.monalisa.global.config.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFindQueryService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    public User findById(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException(userId, UserErrorCode.USER_NOT_FOUND);
        });
    }

    public boolean existByAccountId(final String ID) {
        return userRepository.existsByAccountID(ID);
    }

    public User findByAccountID(final String accountId) {
        return userRepository.findByAccountID(accountId).orElseThrow(() -> {
            throw new UserNotFoundException(accountId, UserErrorCode.USER_NOT_FOUND);
        });
    }

    public List<Order> findOrderListByUserId(final Long userId) {
        return orderRepository.findByBuyerId(userId);
    }
}
