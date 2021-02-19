package spiis.server.api;

import java.util.List;

public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String location;
    private List<String> allergies;

    protected UserResponse() {}

    //TODO: Use Builder pattern, see User.Builder

    public UserResponse(Long id, String email, String firstName, String lastName, int age,
                        String location, List<String> allergies) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.location = location;
        this.allergies = allergies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
