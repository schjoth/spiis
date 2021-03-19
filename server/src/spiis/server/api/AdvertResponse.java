package spiis.server.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertResponse {
    private Long id;
    private String title;
    private String companyName;
    private String link;
    private byte[] picture;
}
