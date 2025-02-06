package com.server.book.service;

import com.server.book.domain.Book;
import com.server.book.domain.User;
import com.server.book.repo.BookDAO;
import com.server.book.repo.LibraryDAO;
import com.server.book.repo.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {

      @Autowired
      private UserDAO userDAO;

      @Autowired
      private BookDAO bookDAO;

      @Autowired
      private LibraryDAO libraryDAO;


    @Autowired
    private PasswordEncoder bcpe;

    public User getLoginUser(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth!= null && auth.isAuthenticated()){
                User user = (User) auth.getPrincipal();
                User result = userDAO.findById(user.getEmail()).get();

                return result;
            }
            return null;
        } catch (Exception e) {

            return null;
        }

    }

    @Transactional
    public void decodingPassword (User vo){
        vo.setPwd(bcpe.encode(vo.getPwd()));
        userDAO.save(vo);


    }

      public User login(String email, String password){


          User user = userDAO.findById(email).orElse(null);

          if(user != null){

              if( bcpe.matches(password,user.getPwd())){

                  return  user;
              }


          }



          return null;
      }

      public List<Book> list(String email){

        List<Long> list = libraryDAO.list(email);

        if(list.size() > 0){

            List<Book> bookList = new ArrayList<>();

            for (int i=0; i<list.size(); i++){

               bookList.add( bookDAO.findById(list.get(i)).orElse(null));

            }

            return bookList;
        }else {
            return  null;
        }




      }
}
