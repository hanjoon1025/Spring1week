package com.sparta.spring1week.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogRequestDto {

    private String title;
    private String username;
    private String contents;
    private long password;
    private LocalDateTime CreatedAt;
    private LocalDateTime ModifiedAt;


}
