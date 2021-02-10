package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.ExampleCreateRequest;
import spiis.server.api.ExampleResponse;
import spiis.server.error.SpiisException;
import spiis.server.error.SpiisNotFoundException;
import spiis.server.model.ExampleEntity;
import spiis.server.repository.ExampleEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExampleEntityService {

    private final ExampleEntityRepository exampleEntityRepository;

    @Autowired
    public ExampleEntityService(ExampleEntityRepository exampleEntityRepository) {
        this.exampleEntityRepository = exampleEntityRepository;
    }

    private ExampleResponse makeResponse(ExampleEntity entity) {
        Objects.requireNonNull(entity.getId());
        return new ExampleResponse(entity.getId(), entity.getName(), entity.getSubjects().size());
    }

    @Transactional(readOnly = true)
    public ExampleResponse makeResponse(Long id) {
        return makeResponse(exampleEntityRepository.findById(id)
                .orElseThrow(SpiisNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public List<ExampleResponse> getAllEntities() {
        List<ExampleResponse> response = new ArrayList<>();
        for (var entity : exampleEntityRepository.findAll())
            response.add(makeResponse(entity));

        return response;
    }

    @Transactional
    public ExampleResponse makeExampleEntity(ExampleCreateRequest request) {
        String name = request.getName();
        if (name == null || name.trim().length() < 3)
            throw new SpiisException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Entity must have name of at least length 3");

        ExampleEntity newEntity = new ExampleEntity(name);

        // Saving to the repository gives the entity an id
        exampleEntityRepository.save(newEntity);

        return makeResponse(newEntity);
    }

    @Transactional
    public void removeEntity(Long id) {
        ExampleEntity entity = exampleEntityRepository.findById(id)
                .orElseThrow(SpiisNotFoundException::new);
        exampleEntityRepository.delete(entity);
    }
}
