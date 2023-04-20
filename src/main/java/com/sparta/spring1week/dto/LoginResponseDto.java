package com.sparta.spring1week.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String msg;
    private int statusCod;

    public LoginResponseDto(String msg, int statusCod) {
        this.msg = msg;
        this.statusCod = statusCod;
    }
}
