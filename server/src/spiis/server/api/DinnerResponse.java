package spiis.server.api;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class DinnerResponse {
    private Long id;
    private String title;
    private String description;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    @Nullable private String addressLine;
    private String postCode;
    private String city;
    private boolean cancelled;
    private UserResponse host;
    @Nullable private List<UserResponse> guests;
}
