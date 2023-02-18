package com.monalisa.domain.book.api;

import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.order.service.OrderBuyService;
import com.monalisa.domain.book.service.BookFindService;
import com.monalisa.domain.book.service.BookUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookApi {

    private final BookUpdateService bookUpdateService;
    private final BookFindService bookFindService;

    @PostMapping("/add")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody @Valid final BookRequestDto.Add addBookRequestDto) {
        System.out.println(addBookRequestDto.toString());

        final BookResponseDto bookResponseDto = bookUpdateService.registerBook(addBookRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookResponseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<BookResponseDto> updateBook(@RequestBody @Valid final BookRequestDto.Update updateBookRequestDto) {
        System.out.println(updateBookRequestDto.toString());

        final BookResponseDto bookResponseDto = bookUpdateService.updateBook(updateBookRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookResponseDto);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<BookResponseDto> deleteBook(@PathVariable final Long bookId) {
        final BookResponseDto bookResponseDto = bookUpdateService.deleteBook(bookId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookResponseDto);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> findBookById(@PathVariable final Long bookId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookFindService.findById(bookId));
    }
}
