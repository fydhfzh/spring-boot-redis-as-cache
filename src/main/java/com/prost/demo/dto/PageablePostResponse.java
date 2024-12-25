package com.prost.demo.dto;

import com.prost.demo.entity.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageablePostResponse {
    private Iterable<Post> content;
}
