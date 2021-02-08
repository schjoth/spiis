package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.ExampleCreateRequest;
import spiis.server.api.ExampleResponse;
import spiis.server.error.SpiisException;
import spiis.server.model.ExampleEntity;
import spiis.server.repository.ExampleEntityRepository;
import spiis.server.service.ExampleEntityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// See: https://www.baeldung.com/spring-boot-start#web-and-the-controller

@RestController
@RequestMapping("/entities")
public class ExampleController {

    private final ExampleEntityRepository exampleEntityRepository;
    private final ExampleEntityService exampleEntityService;

    @Autowired
    public ExampleController(ExampleEntityRepository exampleEntityRepository, ExampleEntityService exampleEntityService) {
        this.exampleEntityRepository = exampleEntityRepository;
        this.exampleEntityService = exampleEntityService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExampleResponse> getAllEntities() {
        List<ExampleResponse> response = new ArrayList<>();
        for (var entity : exampleEntityRepository.findAll())
            response.add(exampleEntityService.makeResponse(entity));

        return response;
    }

    @GetMapping("/{id}")
    public ExampleResponse getEntityById(@PathVariable("id") Long id) {
        Optional<ExampleEntity> entity = exampleEntityRepository.findById(id);
        return exampleEntityService.makeResponse(entity.orElseThrow());
    }

    /**
     * POST is about creating a new resource
     * @param request the details of the new resource
     * @return a response object with:
     *  - CREATED status code
     *  - A Location: header for the URL of the new resource
     *  - A body containing the object
     */
    @PostMapping
    public ResponseEntity<ExampleResponse> createEntity(ExampleCreateRequest request) {
        String name = request.getName();
        if(name == null || name.trim().length() < 3)
            throw new SpiisException(HttpStatus.UNPROCESSABLE_ENTITY, "Entity must have name of at least length 3");

        ExampleEntity newEntity = new ExampleEntity(name);

        // Saving to the repository gives the entity an id
        exampleEntityRepository.save(newEntity);

        return RESTControllerUtil.makePOSTResponse(
                exampleEntityService.makeResponse(newEntity), "/entities", newEntity.getId());
    }

    /*
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeEntity() {

    }
    */

    /*
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntity(@PathVariable("id") Long id) {

    }
    */
}
