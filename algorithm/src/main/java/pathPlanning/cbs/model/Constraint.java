package pathPlanning.cbs.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Constraint  {
    public Set<VertexConstraint> vertexConstraints = new HashSet<>();
    public Set<EdgeConstraint> edgeConstraints = new HashSet<>();
}
