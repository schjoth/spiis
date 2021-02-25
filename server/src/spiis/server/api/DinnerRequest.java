package spiis.server.api;

import lombok.Data;

@Data
public class DinnerRequest {
    private String title;
    private String description;
    private String expenses;
    private String date;
    private String startTime;
    private String endTime;
    private String addressLine;
    private String postCode;
    private String city;
    private int maxGuests;
}
