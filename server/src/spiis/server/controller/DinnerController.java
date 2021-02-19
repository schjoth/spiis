package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spiis.server.api.AllDinnerResponse;
import spiis.server.api.DinnerResponse;
import spiis.server.service.DinnerService;

@RestController
public class DinnerController {

    private final DinnerService dinnerService;

    @Autowired
    public DinnerController(DinnerService dinnerService) {
        this.dinnerService = dinnerService;
    }

    @GetMapping("/{id}")
    public DinnerResponse getDinner(@PathVariable("id") long id) {
        return dinnerService.getDinnerById(id);
    }

    @GetMapping("/dinners")
    public AllDinnerResponse getAllDinners() {
        return dinnerService.getDinners();
    }
}
