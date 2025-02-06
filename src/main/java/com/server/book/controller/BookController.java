package com.server.book.controller;


import com.server.book.domain.Book;
import com.server.book.domain.Library;
import com.server.book.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class BookController {


    @Autowired
    private LibraryService libraryService;


    @GetMapping("/private/book")
    public ResponseEntity findBook(@RequestParam (name = "title") String title , @RequestParam(name = "page" , defaultValue = "1") int page ){


        log.info("title" + title);
        log.info("내용 " + libraryService.searchBooks(title , page));



        return ResponseEntity.ok(libraryService.searchBooks(title , page));
    }

    @PostMapping("/private/add")

    public ResponseEntity addBook(@RequestBody Book vo){



        return ResponseEntity.ok(libraryService.addBook(vo));
    }

    @DeleteMapping("/private/remove")
    public  ResponseEntity removeBook(@RequestParam String email, @RequestParam long bookCode) {



        return  ResponseEntity.ok(libraryService.removeBook(email,bookCode));
    }



}
