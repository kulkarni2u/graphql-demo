package com.casper.query;

import com.casper.model.Author;
import com.casper.model.Post;
import com.casper.service.PostService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostQuery {
    @Autowired
    PostService postService;

    @GraphQLQuery(name = "greeting")
    public String getGreeting() {
        return "Hello, World!";
    }

    @GraphQLQuery(name = "allPosts")
    public List<Post> getAllPosts() {
        return (List<Post>) postService.getAllPosts();
    }

    @GraphQLQuery(name = "allAuthors")
    public List<Author> getAuthors() {
        return (List<Author>) postService.getAllAuthors();
    }

    @GraphQLMutation(name = "createAuthor")
    public Author createAuthor(@GraphQLArgument(name = "name") String name) {
        return postService.createAuthor(new Author().setName(name));
    }
}
