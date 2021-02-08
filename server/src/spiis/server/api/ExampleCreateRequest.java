package spiis.server.api;

import org.springframework.lang.Nullable;

public class ExampleCreateRequest {
    @Nullable
    private String name;

    protected ExampleCreateRequest() {}

    public ExampleCreateRequest(String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
