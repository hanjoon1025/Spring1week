package com.sparta.spring1week.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
public class SignupResponseDto {

    private String msg ;
    private int statusCod;

    public SignupResponseDto(String msg, int statusCod) {
        this.msg = msg;
        this.statusCod = statusCod;
    }

    //메소드의 첫글자는 소문자를 쓰는게 좋다.


}
