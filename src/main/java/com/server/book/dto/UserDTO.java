package com.server.book.dto;

import com.server.book.domain.Book;
import com.server.book.domain.Library;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDTO {


    private String email;

    private String nickname;

    private String token;

    private List<Library> LibraryList;

}
