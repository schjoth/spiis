package spiis.server.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValueWrapper<T> {
    private T value;
}
