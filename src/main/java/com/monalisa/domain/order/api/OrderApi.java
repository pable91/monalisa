package com.monalisa.domain.order.api;

import com.monalisa.domain.order.dto.request.OrderRequestDto;
import com.monalisa.domain.order.dto.response.OrderResponseDto;
import com.monalisa.domain.order.service.OrderBuyService;
import com.monalisa.domain.user.domain.User;
import com.monalisa.global.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderApi {

    private final OrderBuyService orderBuyService;

    @PostMapping("/single-book")
    public ResponseEntity<OrderResponseDto.CreateSingle> buyBook(@RequestBody @Valid final OrderRequestDto.SingleBook requestDto, @LoginUser final User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderBuyService.createOrderBySingleBook(requestDto, user));
    }

    @PostMapping("/multi-book")
    public ResponseEntity<OrderResponseDto.CreateMulti> buyBooks(@RequestBody final OrderRequestDto.MultiBook requestDto, @LoginUser final User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderBuyService.createOrderByMultiBook(requestDto, user));
    }

}
