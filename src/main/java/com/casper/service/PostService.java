package com.casper.service;

import com.casper.model.Author;
import com.casper.model.Comment;
import com.casper.model.Post;

public interface PostService {
    Iterable<Post> getAllPosts();
    Iterable<Author> getAllAuthors();

    Iterable<Post> getPostsByAuthor(Author author);
    Iterable<Post> getPostsByAuthor(Long authorId);

    Iterable<Comment> getComments(Post post);
    Iterable<Comment> getComments(Long postId);

    Author createAuthor(Author author);
    Post createPost(Post post);
    Comment addComment(Comment comment);
}
