package com.server.book.repo;

import com.server.book.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibraryDAO extends JpaRepository<Library, Integer> {

    @Query(value = "SELECT * FROM library WHERE user_email = :email AND  book_code = :bookCode", nativeQuery = true)
    Library findBook(@Param("email") String userEmail, @Param("bookCode") Long bookCode);

    @Query(value = "SELECT * FROM library WHERE user_email = :email", nativeQuery = true)
    List<Library> findList(@Param("email") String userEmail);

    @Query (value = "SELECT book_code FROM library WHERE user_email =:email", nativeQuery = true)
    List<Long> list (@Param("email")String userEmail);




}
