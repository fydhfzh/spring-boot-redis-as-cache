package com.prost.demo.controller;

import com.prost.demo.dto.APIResponse;
import com.prost.demo.dto.PostRequest;
import com.prost.demo.dto.PostResponse;
import com.prost.demo.entity.Post;
import com.prost.demo.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final ModelMapper modelMapper;

    @GetMapping("/{postId}")
    public ResponseEntity<APIResponse> one(@PathVariable String postId) {
        log.info("Handling request to get post with id: " + postId);

        Post post = postService.one(postId);

        PostResponse postResponse = modelMapper.map(post, PostResponse.class);

        APIResponse response = APIResponse.builder()
                .status("success")
                .message("Post retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(postResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse> save(@RequestBody PostRequest payload) {
        log.info("Handling request to save post with payload: {}", payload);

        Post newPost = modelMapper.map(payload, Post.class);

        Post post = postService.save(newPost);

        PostResponse postResponse = modelMapper.map(post, PostResponse.class);

        APIResponse response = APIResponse.builder()
                .status("success")
                .message("Post saved successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(postResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse> findAll(@RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);

        Iterable<Post> posts = postService.all(pageable).getContent();

        APIResponse response = APIResponse.builder()
                .status("success")
                .message("Posts retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(posts)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<APIResponse> update(@PathVariable String postId, @RequestBody PostRequest payload) {
        Post updatePost = modelMapper.map(payload, Post.class);
        updatePost.setId(postId);

        Post post = postService.save(updatePost);

        PostResponse postResponse = modelMapper.map(post, PostResponse.class);

        APIResponse response = APIResponse.builder()
                .status("success")
                .message("Post updated successfully")
                .statusCode(HttpStatus.OK.value())
                .data(postResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
