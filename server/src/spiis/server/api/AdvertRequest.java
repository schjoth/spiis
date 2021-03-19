package spiis.server.api;

import lombok.Data;

@Data
public class AdvertRequest {
    private String title;
    private String companyName;
    private String link;
    private byte[] picture;
}
