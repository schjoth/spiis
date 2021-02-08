package spiis.server.service;

import org.springframework.stereotype.Service;
import spiis.server.api.ExampleResponse;
import spiis.server.model.ExampleEntity;

@Service
public class ExampleEntityService {

    public ExampleResponse makeResponse(ExampleEntity entity) {
        return new ExampleResponse(entity.getId(), entity.getName(), entity.getSubjects().size());
    }
}
