package com.monalisa.domain.order.api;

import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.order.dto.request.OrderRequestDto;
import com.monalisa.domain.order.dto.response.OrderResponseDto;
import com.monalisa.domain.order.service.OrderBuyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderApi {

    private final OrderBuyService orderBuyService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> buyBook(@RequestBody @Valid final OrderRequestDto.Buy requestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderBuyService.createOrder(requestDto));
    }
}
