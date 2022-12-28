package pathPlanning.cbs.model;

import lombok.Data;

@Data
public class VertexConstraint {
    public VertexConstraint(State state){
        this.time = state.getStep();
        this.location = state.getLocation();
    }
    public VertexConstraint(int time, Location location){
        this.time = time;
        this.location = location;
    }
    private int time;
    private Location location;
}
