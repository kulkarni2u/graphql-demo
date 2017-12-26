package com.casper.service.impl;

import com.casper.model.Author;
import com.casper.model.Comment;
import com.casper.model.Post;
import com.casper.repository.AuthorRepository;
import com.casper.repository.CommentRepository;
import com.casper.repository.PostRepository;
import com.casper.service.PostService;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    @GraphQLQuery(name = "posts")
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Iterable<Post> getPostsByAuthor(Author author) {
        return postRepository.findAll();
    }

    @Override
    public Iterable<Post> getPostsByAuthor(Long authorId) {
        return postRepository.findAll();
    }

    @Override
    public Iterable<Comment> getComments(Post post) {
        return commentRepository.findAll();
    }

    @Override
    public Iterable<Comment> getComments(Long postId) {
        return commentRepository.findAll();
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
