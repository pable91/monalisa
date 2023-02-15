package com.monalisa.domain.book.api;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
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

    @PostMapping("/add")
    private ResponseEntity<BookResponseDto> addBook(@RequestBody @Valid final BookRequestDto.Add addBookRequestDto) {
        System.out.println(addBookRequestDto.toString());

        final Book book = bookUpdateService.addBookService(addBookRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BookResponseDto.of(book));
    }

    @PutMapping("/update")
    private ResponseEntity<BookResponseDto> updateBook(@RequestBody @Valid final BookRequestDto.Update updateBookRequestDto) {
        System.out.println(updateBookRequestDto.toString());

        final Book book = bookUpdateService.updateBookService(updateBookRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BookResponseDto.of(book));

    }

    @DeleteMapping("/delete/{bookId}")
    private ResponseEntity<BookResponseDto> deleteBook(@PathVariable final Long bookId) {
        final Book book = bookUpdateService.deleteBookService(bookId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BookResponseDto.of(book));
    }

}
