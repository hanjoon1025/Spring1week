package com.sparta.spring1week.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlogDeleteDto {
    private boolean success;

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
