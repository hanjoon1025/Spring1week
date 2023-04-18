package com.sparta.spring1week.service;

import com.sparta.spring1week.dto.BlogDeleteDto;
import com.sparta.spring1week.dto.BlogRequestDto;
import com.sparta.spring1week.dto.BlogResponseDto;
import com.sparta.spring1week.entity.Blog;
import com.sparta.spring1week.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;


    //BlogRensponseDto를 사용하여 password빼고 추출
    public BlogResponseDto createList(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return new BlogResponseDto(blog);
    }


    public List<BlogResponseDto> getlist(){
        List<Blog> bloglist = blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogResponseDto> blogResponseDto = new ArrayList<>();
        //내림차순된 정보를 가지고와서 responsedto에 넣어줌
        for (Blog blog : bloglist){
            BlogResponseDto a = new BlogResponseDto(blog);
            blogResponseDto.add(a);
        }
        return blogResponseDto;
    }



    public BlogResponseDto getidlist(Long id) {
        Blog blog = checkblog(id);

        return new BlogResponseDto(blog);
    }


    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = checkblog(id);
        blog.update(requestDto);
        return new BlogResponseDto(blog);
    }

    private Blog checkblog(Long id) {
        return blogRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 메모가 존재하지 않습니다.")
        );
    }

    @Transactional
    public BlogDeleteDto deleteBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = checkblog(id);
        BlogDeleteDto blogDeleteDto = new BlogDeleteDto();
        if(requestDto.getPassword() == blog.getPassword()){
            blogRepository.deleteById(id);
            blogDeleteDto.setSuccess(true);
        }
        else{
            blogDeleteDto.setSuccess(false);
        }


        return blogDeleteDto;

    }

}
