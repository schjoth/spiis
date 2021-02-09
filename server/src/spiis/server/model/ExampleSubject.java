package spiis.server.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ExampleSubject {

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "subjects")
    private Set<ExampleEntity> entities = new HashSet<>();

    protected ExampleSubject() {}

    public ExampleSubject(String name) {
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

    public Set<ExampleEntity> getEntities() {
        return entities;
    }

    public void setEntities(Set<ExampleEntity> entities) {
        this.entities = entities;
    }

    // We don't own the relation, so if we are removed, we must tell the owner
    // See: https://stackoverflow.com/questions/1082095/how-to-remove-entity-with-manytomany-relationship-in-jpa-and-corresponding-join
    @PreRemove
    public void onRemove() {
        for(ExampleEntity entity:entities)
            entity.getSubjects().remove(this);
    }
}
