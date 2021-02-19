package spiis.server.api;

import java.util.Set;

public class AllDinnerResponse {

    private Set<DinnerResponse> dinners;


    public AllDinnerResponse(Set<DinnerResponse> dinners) {
        this.dinners = dinners;
    }

    public Set<DinnerResponse> getDinners() {
        return dinners;
    }

    public void setDinners(Set<DinnerResponse> dinners) {
        this.dinners = dinners;
    }

}
