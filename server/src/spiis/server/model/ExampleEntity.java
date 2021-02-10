package spiis.server.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Based on: https://www.baeldung.com/jpa-entities
 */

@Entity
public class ExampleEntity {
    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false)
    private String name;

    // We own this many-to-many because we don't use mappedBy
    @ManyToMany
    private Set<ExampleSubject> subjects = new HashSet<>();

    // All entities must have a default constructor
    protected ExampleEntity() {}

    public ExampleEntity(String name) {
        this.name = name;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ExampleSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<ExampleSubject> subjects) {
        this.subjects = subjects;
    }
}
