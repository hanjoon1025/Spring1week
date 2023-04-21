package com.sparta.spring1week.service;

import com.sparta.spring1week.dto.BlogDeleteDto;
import com.sparta.spring1week.dto.BlogRequestDto;
import com.sparta.spring1week.dto.BlogResponseDto;
import com.sparta.spring1week.entity.Blog;
import com.sparta.spring1week.entity.User;
import com.sparta.spring1week.jwt.JwtUtil;
import com.sparta.spring1week.repository.BlogRepository;
import com.sparta.spring1week.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    //BlogRensponseDto를 사용하여 password빼고 추출
    @Transactional
    public BlogResponseDto createList(BlogRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            } else{
                throw new IllegalArgumentException("Token Error");
            }


        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
            System.out.println("유저 값 확인 "+user);
        // username 받아온 값을 추가 => User 엔티티를 파라미터로 전달
        Blog blog =  blogRepository.saveAndFlush(new Blog(requestDto, user.getUsername()));


        return new BlogResponseDto(blog);
        }else{
            return null;
        }

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
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        //토큰 널값 유무 처리
        if (token != null && jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Error");
        }
        String username = claims.getSubject();
        Blog blog = checkblog(id);

        if (!blog.getUsername().equals(username)) {
            throw new IllegalArgumentException("게시글 작성자만 수정이 가능합니다.");
        }

        blog.update(requestDto);
        return new BlogResponseDto(blog);
    }

    private Blog checkblog(Long id) {
        return blogRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택한 메모가 존재하지 않습니다.")
        );
    }

    @Transactional
    public BlogDeleteDto deleteBlog(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)){
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Error");
        }

        String username = claims.getSubject();

        Blog blog = checkblog(id);

        if(!blog.getUsername().equals(username)){
            throw new IllegalArgumentException("게시글 작성자만 삭제가 가능합니다.");
        }

        blogRepository.deleteById(id);
        BlogDeleteDto blogDeleteDto = new BlogDeleteDto();
        blogDeleteDto.setSuccess(true);

        return blogDeleteDto;

    }

}
