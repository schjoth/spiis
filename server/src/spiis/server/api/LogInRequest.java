package spiis.server.api;

import lombok.Data;

@Data
public class LogInRequest {
    private String email;
    private String password;
}
