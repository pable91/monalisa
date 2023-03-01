package com.monalisa.domain.user.dto.response;

import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.order.domain.Order;
import com.monalisa.domain.order.dto.response.OrderResponseDto;
import com.monalisa.domain.user.domain.Role;
import com.monalisa.domain.user.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserResponseDto {

    @Getter
    public static class SignUp {
        private String accountId;

        private String name;

        private String email;

        public SignUp(User user) {
            this.accountId = user.getAccountID();
            this.name = user.getName();
            this.email = user.getEmail();
        }

        public static SignUp of(final User user) {
            return new SignUp(user);
        }
    }

    @Getter
    public static class Profile {
        private String userName;
        private String name;
        private String email;
        private Role role;
        private List<BookResponseDto> registerBookList;

        public Profile(User user) {
            this.userName = user.getName();
            this.name = user.getName();
            this.email = user.getEmail();
            this.role = user.getRole();
            this.registerBookList = user.getRegisterBooks().stream()
                    .map(book -> BookResponseDto.of(book))
                    .collect(Collectors.toList());
        }

        public static Profile of(User user) {
            return new Profile(user);
        }
    }

    @Getter
    public static class Login {
        private String userName;

        private List<BookResponseDto> registerBookList;

        private String accessToken;
        private String refreshToken;

        public Login(String accessToken, String refreshToken, User user) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.userName = user.getName();
            this.registerBookList = user.getRegisterBooks().stream()
                    .map(book -> BookResponseDto.of(book))
                    .collect(Collectors.toList());
        }

        public static Login from(String accessToken, String refreshToken, User user) {
            return new Login(accessToken, refreshToken, user);
        }
    }

    @Getter
    public static class OrderList {
        private List<OrderResponseDto.Find> orderList;

        private OrderList(List<Order> orderList) {
            this.orderList = orderList.stream()
                    .map(order -> OrderResponseDto.Find.of(order))
                    .collect(Collectors.toList());
        }

        public static OrderList of(List<Order> orderList) {
            return new OrderList(orderList);
        }
    }
}
