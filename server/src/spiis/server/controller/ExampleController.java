package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.ExampleCreateRequest;
import spiis.server.api.ExampleResponse;
import spiis.server.api.SignUpRequest;
import spiis.server.error.SpiisException;
import spiis.server.model.ExampleEntity;
import spiis.server.repository.ExampleEntityRepository;
import spiis.server.service.ExampleEntityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// See: https://www.baeldung.com/spring-boot-start#web-and-the-controller

@RestController
@RequestMapping("/entities")
public class ExampleController {

    private final ExampleEntityService exampleEntityService;

    @Autowired
    public ExampleController(ExampleEntityService exampleEntityService) {
        this.exampleEntityService = exampleEntityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExampleResponse> getAllEntities() {
        return exampleEntityService.getAllEntities();
    }

    @GetMapping("/{id}")
    public ExampleResponse getEntityById(@PathVariable("id") Long id) {
        return exampleEntityService.makeResponse(id);
    }

    @PostMapping
    public ResponseEntity<ExampleResponse> createEntity(@RequestBody ExampleCreateRequest request) {
        ExampleResponse response = exampleEntityService.makeExampleEntity(request);
        return RESTControllerUtil.makePOSTResponse(response, "/entities", response.getId());
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
