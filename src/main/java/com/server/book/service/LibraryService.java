package com.server.book.service;

import com.server.book.domain.Book;
import com.server.book.domain.Library;
import com.server.book.domain.User;
import com.server.book.repo.BookDAO;
import com.server.book.repo.LibraryDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Slf4j
@Service
public class LibraryService {


    @Autowired
    private LibraryDAO libraryDAO;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private UserService userService;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/search/book.json";

    public String searchBooks(String query, int page) {

        int start = page *10 -9;
        String apiUrl = NAVER_API_URL + "?query=" + query + "&display=10&start="+start;


        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        log.info("클라이언트 id" + clientId);
        log.info("시크릿 키 " + clientSecret);


        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);


        return response.getBody();
    }

    public boolean addBook(Book vo){

     User user =   userService.getLoginUser();

     if(bookDAO.findById(vo.getBookCode()).isEmpty()){
         bookDAO.save(vo); // 책정보 저장
     }

     if(libraryDAO.findBook(user.getEmail(),vo.getBookCode()) == null){
         Library library = new Library();
         library.setBookCode(vo.getBookCode());
         library.setAddedAt(LocalDateTime.now());
         library.setUserEmail(user.getEmail());
         libraryDAO.save(library);
         return true;
     }





       return false;
    }


    public boolean removeBook(String email, long bookCode ){

        Library vo = libraryDAO.findBook(email,bookCode);

        if(vo != null) {

            libraryDAO.deleteById(vo.getLibraryCode());
            return  true;
        }


     return  false;
    }

}
