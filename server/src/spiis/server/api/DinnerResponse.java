package spiis.server.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class DinnerResponse {
    private Long id;
    private String title;
    private String description;
    private String expenses;
    private String date;
    private String startTime;
    private String endTime;
    @Nullable private String addressLine;
    private String postCode;
    private String city;
    private int maxGuests;
    private boolean cancelled;
    private boolean lockedByAdmin;
    private UserResponse host;
    private String registrationDeadlineDate;
    private String registrationDeadlineTime;
    @Nullable private List<UserResponse> guests;
    private OffsetDateTime createdTime;
}
