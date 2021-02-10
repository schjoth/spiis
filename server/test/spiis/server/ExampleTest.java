package spiis.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.ExampleCreateRequest;
import spiis.server.api.ExampleResponse;
import spiis.server.controller.ExampleController;
import spiis.server.error.SpiisException;
import spiis.server.service.ExampleEntityService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExampleTest {

    @Autowired
    private ExampleController exampleController;

    @Autowired
    private ExampleEntityService exampleEntityService;

    @Test
    void createExampleEntity() {
        ExampleCreateRequest request = new ExampleCreateRequest("Fredrik");

        ResponseEntity<ExampleResponse> responseEntity = exampleController.createEntity(request);
        ExampleResponse response = responseEntity.getBody();
        assertNotNull(response);
        Long id = response.getId();

        assertEquals("Fredrik", response.getName());
        assertEquals(0, response.getSubjectCount());

        // check that we can request it again
        ExampleResponse response2 = exampleEntityService.makeResponse(id);

        assertEquals(id, response.getId());
        assertEquals("Fredrik", response.getName());
        assertEquals(0, response.getSubjectCount());

        // remove it from the database
        exampleEntityService.removeEntity(id);

        // This new Entity has an illegal name!
        ExampleCreateRequest request2 = new ExampleCreateRequest("   x   ");
        SpiisException ex = assertThrows(SpiisException.class, () -> {
            exampleController.createEntity(request2);
        });
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getStatusCode());
    }
}
