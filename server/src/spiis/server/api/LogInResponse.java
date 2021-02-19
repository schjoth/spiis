package spiis.server.api;

public class LogInResponse {
    private UserResponse user;
    private String token;

    protected LogInResponse() {}

    public LogInResponse(UserResponse user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
