package pathPlanning.stepAstart;

import cn.hutool.json.JSONUtil;
import pathPlanning.stepAstart.model.*;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class StepAstartTest {
    public static void main(String[] args) {
        SaLocation c11 = new SaLocation("00000000");
        SaLocation c12 = new SaLocation("00010000");
        SaLocation c13 = new SaLocation("00020000");
        SaLocation c21 = new SaLocation("00000001");
        SaLocation c22 = new SaLocation("00010001");
        SaLocation c23 = new SaLocation("00020001");
        SaLocation c31 = new SaLocation("00000002");
        SaLocation c32 = new SaLocation("00010002");
        SaLocation c33 = new SaLocation("00020002");
        Map<String, SaLocation> dimension = new HashMap<>();
        dimension.put(c11.getId(), c11);
        dimension.put(c12.getId(), c12);
        dimension.put(c13.getId(), c13);
        dimension.put(c21.getId(), c21);
        dimension.put(c22.getId(), c22);
        dimension.put(c23.getId(), c23);
        dimension.put(c31.getId(), c31);
        dimension.put(c32.getId(), c32);
        dimension.put(c33.getId(), c33);

        SaEnvironment environment = new SaEnvironment();
        environment.setDimension(dimension);
        environment.setObstacles(new LinkedHashSet<>());

        SaAgent agent1 = new SaAgent();
        agent1.setId("agent0");
        agent1.setDirection(SaDirection.X);
        agent1.setStart("00000000");
        agent1.setGoal("00020002");

        StepAstart stepAstart = new StepAstart();
        List<SaProbeNode> path =  stepAstart.searchPath(agent1,environment);
        System.out.println(JSONUtil.toJsonStr(path));
    }
}
