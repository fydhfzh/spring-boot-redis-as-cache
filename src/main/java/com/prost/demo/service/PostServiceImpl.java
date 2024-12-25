package com.prost.demo.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prost.demo.entity.Post;
import com.prost.demo.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Transactional
    @Override
    @CachePut(value = "posts", key = "#post.id")
    public Post save(Post post) {
        if (post.getId() != null) {
            postRepository.findById(post.getId()).orElseThrow(
                    () -> new NoSuchElementException("No post present with id: " + post.getId()));
        }

        return postRepository.save(post);
    }

    @Override
    @Cacheable(value = "posts", key = "#id")
    public Post one(String id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No post present with id: " + id));

        return post;
    }

    @Override
    @Cacheable(value = "posts")
    public Page<Post> all(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    @CacheEvict(value = "posts", key = "#id")
    @Caching(evict = { @CacheEvict(value = "posts", allEntries = true), @CacheEvict(value = "posts", key = "#id") })
    public void delete(String id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No post present with id: " + id));

        post.setDeletedAt(LocalDateTime.now());

        postRepository.save(post);
    }
}
