package spiis.server.api;

import lombok.Data;

import java.util.List;

@Data
public class SignUpRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private String city;
    private List<String> allergies;
}
