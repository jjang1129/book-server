package com.server.book.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {


    @Id
    @Column(name = "library_code")
    private int libraryCode;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name="book_code")
    private long bookCode;

    @Column(name = " added_at ")
    private LocalDateTime addedAt;





}
