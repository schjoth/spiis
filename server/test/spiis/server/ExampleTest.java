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
import spiis.server.model.ExampleEntity;
import spiis.server.repository.ExampleEntityRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExampleTest {

    @Autowired
    private ExampleEntityRepository exampleEntityRepository;

    @Autowired
    private ExampleController exampleController;

    @Test
    @Transactional
    void createExampleEntity() {
        ExampleCreateRequest request = new ExampleCreateRequest("Fredrik");

        ResponseEntity<ExampleResponse> response = exampleController.createEntity(request);
        ExampleResponse body = response.getBody();

        assertNotNull(body);
        assertEquals(response.getHeaders().getFirst("Location"), String.format("/entities/%d", body.getId()));

        ExampleEntity entity = exampleEntityRepository.findById(body.getId()).orElseThrow();

        assertEquals(entity.getId(), body.getId());
        assertEquals(entity.getName(), body.getName());
        assertEquals(0, body.getSubjectCount());
        assertEquals(0, entity.getSubjects().size());

        exampleEntityRepository.delete(entity);

        // This Entity has an illegal name!
        ExampleCreateRequest request2 = new ExampleCreateRequest("   x   ");
        var ex = assertThrows(SpiisException.class, () -> {
            exampleController.createEntity(request2);
        });

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getStatusCode());
    }
}
