package com.server.book.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @Column(name="book_code")
    private long bookCode;
    @Column
    private String title;
    @Column
    private int price;
    @Column
    private String author;
    @Column
    private String img;
    @Column
    private String publisher;
}
