package spiis.server.api;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class DinnerRequest {
    private String title;
    private String description;
    private String expenses;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String addressLine;
    private String postCode;
    private String city;
    private int maxGuests;
}
