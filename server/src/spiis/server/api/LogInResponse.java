package spiis.server.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogInResponse {
    private UserResponse user;
    private String token;
}
