package cbs.model;

import lombok.Data;

@Data
public class EdgeConstraint {
    public EdgeConstraint(State state1,State state2){
        this.time = state1.getStep();
        this.location1 = state1.getLocation();
        this.location2 = state2.getLocation();
    }
    public EdgeConstraint(int time, Location location1, Location location2){
        this.time = time;
        this.location1 = location1;
        this.location2 = location2;
    }
    private int time;
    private Location location1;
    private Location location2;
}
