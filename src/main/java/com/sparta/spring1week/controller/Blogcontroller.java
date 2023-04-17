package com.sparta.spring1week.controller;

import com.sparta.spring1week.dto.BlogRequestDto;
import com.sparta.spring1week.dto.BlogResponseDto;
import com.sparta.spring1week.entity.Blog;
import com.sparta.spring1week.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Blogcontroller {

    private final BlogService blogService;

    @PostMapping("/create")
    public Blog createList(@RequestBody BlogRequestDto requestDto){

        return blogService.createList(requestDto);
    }

    @GetMapping("/list")
    public List<Blog> getlist(){
        return blogService.getlist();
    }

    @GetMapping("/{id}")
    public BlogResponseDto getidList(@PathVariable Long id) {
        return (BlogResponseDto) blogService.getidlist(id);
    }

    @PutMapping("/update/{id}")
    public BlogResponseDto updateCourse(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.updateBlog(id, requestDto);
    }



}
