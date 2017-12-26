package com.casper.config;

import com.casper.model.Author;
import com.casper.model.Comment;
import com.casper.model.Post;
import com.casper.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartupListener.class);

    private final String port;

    public ApplicationStartupListener(@Value("${server.port}") String port) {
        this.port = port;
    }

    @Autowired
    private PostService service;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.info("Application is running on port: {}", this.port);
        initData();
    }

    private void initData() {
        Author anirudh = service.createAuthor(new Author().setName("Anirudh"));
        Author rani = service.createAuthor(new Author().setName("Nagarani"));

        Post post = service.createPost(new Post().setContent("First Post").setAuthor(anirudh));
        service.addComment(new Comment().setContent("First Comment").setPost(post));

        service.createPost(new Post().setContent("Another Post").setAuthor(rani));

        service.getPostsByAuthor(anirudh.getId())
                .forEach(System.out::println);
    }
}
