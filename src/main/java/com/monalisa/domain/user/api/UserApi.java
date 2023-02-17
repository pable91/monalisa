package com.monalisa.domain.user.api;

import com.monalisa.domain.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserApi {

    private final UserFindService userFindService;

    @GetMapping("/{userId}")
    public ResponseEntity profile(@PathVariable final Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userFindService.profile(userId));
    }

}
