package spiis.server.service;

import org.springframework.stereotype.Service;
import spiis.server.api.ExampleResponse;
import spiis.server.model.ExampleEntity;

import java.util.Objects;

@Service
public class ExampleEntityService {

    public ExampleResponse makeResponse(ExampleEntity entity) {
        Objects.requireNonNull(entity.getId());
        return new ExampleResponse(entity.getId(), entity.getName(), entity.getSubjects().size());
    }
}
