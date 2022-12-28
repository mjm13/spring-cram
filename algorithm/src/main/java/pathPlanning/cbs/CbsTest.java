package pathPlanning.cbs;

import cbs.model.*;
import cn.hutool.json.JSONUtil;
import pathPlanning.cbs.model.*;

import java.util.*;

public class CbsTest {
    public static void main(String[] args) {
        Cbs cbs = new Cbs();
        Location c11 = new Location("0100000000");
        Location c12 = new Location("0100010000");
        Location c13 = new Location("0100020000");
        Location c22 = new Location("0100010001");
        Location c31 = new Location("0100010002");
        Location c32 = new Location("0100010002");
        Location c33 = new Location("0100010002");
        Map<String, Location> dimension = new HashMap<>();
        dimension.put(c11.getId(),c11);
        dimension.put(c12.getId(),c12);
        dimension.put(c13.getId(),c13);
        dimension.put(c22.getId(),c22);
        dimension.put(c31.getId(),c31);
        dimension.put(c32.getId(),c32);
        dimension.put(c33.getId(),c33);

        Environment environment = new Environment();
        environment.setDimension(dimension);
        environment.setObstacles(new LinkedHashSet<>());
        environment.setConstraints(new Constraint());

        Map<String, CbsAgent> agents = new HashMap<>();
        CbsAgent agent1 = new CbsAgent();
        agent1.setId("agent0");
        agent1.setStart("0100000000");
        agent1.setGoal("0100020000");
        CbsAgent agent2 = new CbsAgent();
        agent2.setId("agent1");
        agent2.setStart("0100020000");
        agent2.setGoal("0100000000");
        agents.put(agent1.getId(),agent1);
        agents.put(agent2.getId(),agent2);
        HighLevelNode result = cbs.search(agents,environment);
        System.out.println("最终结果："+JSONUtil.toJsonStr(result.getSolutions()));
    }
}
