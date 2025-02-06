package com.server.book.controller;

import com.server.book.config.TokenProvider;
import com.server.book.domain.Book;
import com.server.book.domain.User;
import com.server.book.dto.UserDTO;
import com.server.book.repo.LibraryDAO;
import com.server.book.repo.UserDAO;
import com.server.book.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class UserController {

    @Autowired
    private UserDAO dao;

    @Autowired
    private LibraryDAO libraryDAO;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;






    

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody User vo){


        log.info("컨트롤러 체크 " + vo);
       if(dao.findById(vo.getEmail()).isPresent()){

           return  ResponseEntity.ok(false);
       }else {
           userService.decodingPassword(vo);
           dao.save(vo);
           return  ResponseEntity.ok(vo.getEmail());
       }


    }











    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User vo){

      User user =  userService.login(vo.getEmail(),vo.getPwd());


        if(user != null){ // 회원이 있을시
            String token = tokenProvider.create(user); // 토큰 발행


            UserDTO dto = UserDTO.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .token(token)
                    .LibraryList(libraryDAO.findList(user.getEmail()))
                    .build();


            return  ResponseEntity.ok(dto
            );
        }
        return  ResponseEntity.ok(false);



    }

    @GetMapping("/private/check")
    public ResponseEntity addCheck (@RequestParam (name = "email") String email){


         log.info("컨트롤러까지 접근");


         return  ResponseEntity.ok( libraryDAO.findList(email));


    }

    @GetMapping("/private/list")
    public  ResponseEntity list (@RequestParam (name = "email") String email){





        return ResponseEntity.ok(userService.list(email));




    }


}
