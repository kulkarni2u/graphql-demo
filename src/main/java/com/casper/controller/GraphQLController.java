package com.casper.controller;

import com.casper.query.PostQuery;
import graphql.*;
import graphql.language.SourceLocation;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GraphQLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphQLController.class);
    private final GraphQL graphQL;

    @Autowired
    public GraphQLController(PostQuery postQuery) {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                    // For Annotated Query resolvers
                    new AnnotatedResolverBuilder(),
                    new PublicResolverBuilder("com.casper")
                )
                // Add all your queries objects here.
                .withOperationsFromSingleton(postQuery)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();

        graphQL = GraphQL.newGraphQL(schema).build();
        LOGGER.info("Generated GraphQL instance using SPQR");
    }
    // Single endpoint for GraphQL
    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromAnnotated(@RequestBody Map<String, Object> request) {
        ExecutionResult executionResult = graphQL.execute(
                ExecutionInput.newExecutionInput()
                        .query((String) request.get("query"))
                        .operationName((String) request.get("operationName"))
                        .context(null) // you can pass your SpringSecurityContext here.
                        .build());

        // Error Handling
        if (!executionResult.getErrors().isEmpty()) {
            return sanitize(executionResult);
        }

        return executionResult;
    }

    private ExecutionResult sanitize(ExecutionResult executionResult) {
        return new ExecutionResultImpl(
            executionResult.getErrors().stream()
                .peek(err -> LOGGER.error(err.getMessage()))
                .map(this::sanitize)
                .collect(Collectors.toList()));
    }

    // Error Handling and Sanitizing for display
    private GraphQLError sanitize(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            return new GraphQLError() {
                @Override
                public String getMessage() {
                    Throwable cause = ((ExceptionWhileDataFetching) error).getException().getCause();
                    return cause instanceof InvocationTargetException ? ((InvocationTargetException) cause).getTargetException().getMessage() : cause.getMessage();
                }

                @Override
                public List<SourceLocation> getLocations() {
                    return error.getLocations();
                }

                @Override
                public ErrorType getErrorType() {
                    return error.getErrorType();
                }
            };
        }

        return error;
    }
}
