package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spiis.server.api.DinnerResponse;
import spiis.server.service.DinnerService;

import java.util.List;

@RestController
@RequestMapping("/dinner")
public class DinnerController {

    private final DinnerService dinnerService;

    @Autowired
    public DinnerController(DinnerService dinnerService) {
        this.dinnerService = dinnerService;
    }

    @GetMapping
    public List<DinnerResponse> getAllDinners() {
        return dinnerService.getDinners();
    }

    @GetMapping("/{id}")
    public DinnerResponse getDinner(@PathVariable("id") long id) {
        return dinnerService.getDinnerById(id);
    }
}
