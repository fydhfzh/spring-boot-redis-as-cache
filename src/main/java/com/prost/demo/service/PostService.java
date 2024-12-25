package com.prost.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prost.demo.entity.Post;

public interface PostService {
    Post save(Post payload);

    Post one(String id);

    Page<Post> all(Pageable pageable);

    void delete(String id);
}
