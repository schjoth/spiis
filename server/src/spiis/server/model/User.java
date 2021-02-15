package spiis.server.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String location;


    protected User() {}

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
