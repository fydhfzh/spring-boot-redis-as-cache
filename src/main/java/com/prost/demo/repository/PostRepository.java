package com.prost.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prost.demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, String> {

}
