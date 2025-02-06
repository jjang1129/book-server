package com.server.book.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @Data @NoArgsConstructor
@Entity
@Builder
public class User {


    @Id
    private String email;
    @Column
    private String pwd;
    @Column
    private String nickname;
}
