package com.casper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull private String name;

    @OneToMany(mappedBy = "author", targetEntity = Post.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Post> posts;

    public Author setName(String name) {
        this.name = name;
        return this;
    }

    @GraphQLQuery(name = "id")
    public Long getId() {
        return id;
    }

    @GraphQLQuery(name = "name")
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return Objects.equals(getId(), author.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
