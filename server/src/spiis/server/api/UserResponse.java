package spiis.server.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String city;
    private List<String> allergies;
    private boolean admin;
    private boolean blocked;
    @Nullable
    private Float averageHostRating;
}
