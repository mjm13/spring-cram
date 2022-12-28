package cbs;

import cbs.model.*;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Cbs {
    private Environment environment;
    private Map<String, CbsAgent> agents;
    private HighLevelNode start;
    private CbsAStar cbsAStar = new CbsAStar();
    //待处理解决方案
    private Queue<HighLevelNode> openList = new PriorityQueue<HighLevelNode>();
    //已处理解决方案
    private Set<HighLevelNode> closeList = new HashSet<>();

    public HighLevelNode search(Map<String, CbsAgent> agents, Environment env) {
        this.environment = env;
        this.agents = agents;
        this.start = new HighLevelNode();
        //初始化所有解决方案
        for (Map.Entry<String, CbsAgent> entry : this.agents.entrySet()) {
            List<State> path = cbsAStar.searchPath(entry.getValue(), environment);
            start.getSolutions().put(entry.getKey(), path);
        }
        Long cost = start.getSolutions().values().stream().flatMap(cbsNodes -> cbsNodes.stream()).count();
        start.setCost(cost);
        openList.add(start);

        while (!openList.isEmpty()) {
            HighLevelNode hln = openList.poll();
            if (closeList.contains(hln)) {
                continue;
            }
            closeList.add(hln);
            environment.setConstraintDict(hln.getConstraintDict());
            Conflict conflict = getConflict(hln);
            if (conflict == null) {
                return hln;
            }
            Map<String, Constraint> constraintDict = createConstraintsFromConflict(conflict);

            for (Map.Entry<String, Constraint> entry : constraintDict.entrySet()) {
                HighLevelNode newhln = new HighLevelNode();
                Map<String,Constraint> constraintMap = hln.getConstraintDict();
                BeanUtil.copyProperties(hln, newhln);
                //深度克隆避免冲突被覆盖
                newhln.setConstraintDict(new HashMap<>());
                constraintMap.forEach((s, constraint) -> {
                    Constraint constraint1 = new Constraint();
                    BeanUtil.copyProperties(constraint, constraint1);
                    newhln.getConstraintDict().put(s,constraint1);
                });
                if (CollectionUtil.isEmpty(newhln.getConstraintDict()) || newhln.getConstraintDict().get(entry.getKey())==null) {
                    newhln.getConstraintDict().put(entry.getKey(),entry.getValue());
                }else{
                    newhln.getConstraintDict().get(entry.getKey()).getEdgeConstraints().addAll(entry.getValue().getEdgeConstraints());
                    newhln.getConstraintDict().get(entry.getKey()).getVertexConstraints().addAll(entry.getValue().getVertexConstraints());
                }
//                System.out.println(entry.getKey()+"新冲突:"+JSONUtil.toJsonStr(newhln.getConstraintDict()));

                //A*求解
                for (Map.Entry<String, CbsAgent> agentEntry : agents.entrySet()) {
                    environment.setConstraints(newhln.getConstraintDict().get(agentEntry.getKey()));
                    if (environment.getConstraints()==null) {
                        environment.setConstraints(new Constraint());
                    }
                    List<State> path = cbsAStar.searchPath(agentEntry.getValue(), environment);
                    if (path == null) {
                        continue;
                    }
                    newhln.getSolutions().put(agentEntry.getKey(), path);
                }
                cost = newhln.getSolutions().values().stream().flatMap(cbsNodes -> cbsNodes.stream()).count();
                newhln.setCost(cost);
                openList.add(newhln);
            }

        }
        return null;
    }

    public Conflict getConflict(HighLevelNode hln) {
        Map<String, List<State>> solutions = hln.getSolutions();
        String agentId = "";
        Integer agentCost = 0;
        for (Map.Entry<String, List<State>> entry : solutions.entrySet()) {
            String agentIdTemp = entry.getKey();
            Integer agentCostTemp = entry.getValue().size();
            if (StrUtil.isBlank(agentId) || agentCost < agentCostTemp) {
                agentId = agentIdTemp;
                agentCost = agentCostTemp;
            }
        }
        Conflict result = new Conflict();
        List<Map.Entry<String, List<State>>> list = solutions.entrySet().stream().collect(Collectors.toList());
        for (int i = 0; i < agentCost; i++) {
            for (int j = 0; j < list.size(); j += 2) {
                Map.Entry<String, List<State>> agent1 = list.get(j);
                Map.Entry<String, List<State>> agent2 = list.get(j + 1);
                State agent1Node1 = null;
                if (i < agent1.getValue().size()) {
                    agent1Node1 = agent1.getValue().get(i);
                }

                State agent2Node1 = null;
                if (i < agent2.getValue().size()) {
                    agent2Node1 = agent2.getValue().get(i);
                }
                if (agent1Node1 == null || agent2Node1 == null) {
                    continue;
                } else if (agent1Node1.getLocation().equals(agent2Node1.getLocation())) {
                    result.setTime(i);
                    result.setAgent1(agent1.getKey());
                    result.setAgent2(agent2.getKey());
                    result.setConflictType(ConflictType.VERTEX);
                    result.setLocation1(agent1Node1.getLocation());
                    return result;
                }
            }

            for (int j = 0; j < list.size(); j += 2) {
                Map.Entry<String, List<State>> agent1 = list.get(j);
                Map.Entry<String, List<State>> agent2 = list.get(j + 1);
                State agent1Node1 = null;
                if (i < agent1.getValue().size()) {
                    agent1Node1 = agent1.getValue().get(i);
                }
                State agent1Node1Next = null;
                if (i+1 < agent1.getValue().size()) {
                    agent1Node1Next = agent1.getValue().get(i+1);
                }else{
                    agent1Node1Next = agent1.getValue().get(agent1.getValue().size()-1);
                }

                State agent2Node1 = null;
                if (i < agent2.getValue().size()) {
                    agent2Node1 = agent2.getValue().get(i);
                }
                State agent2Node1Next = null;
                if (i+1 < agent2.getValue().size()) {
                    agent2Node1Next = agent2.getValue().get(i+1);
                }else{
                    agent2Node1Next = agent2.getValue().get(agent2.getValue().size()-1);
                }

                if (agent1Node1 == null || agent2Node1 == null) {
                    continue;
                }else if (agent1Node1.getLocation().equals(agent2Node1Next.getLocation()) &&
                        agent2Node1.getLocation().equals(agent1Node1Next.getLocation())) {
                    result.setTime(i);
                    result.setAgent1(agent1.getKey());
                    result.setAgent2(agent2.getKey());
                    result.setConflictType(ConflictType.EDGE);
                    result.setLocation1(agent1Node1.getLocation());
                    result.setLocation2(agent1Node1Next.getLocation());
                    return result;
                }

            }
        }
        return null;
    }

    public Map<String, Constraint> createConstraintsFromConflict(Conflict conflict) {
        Map<String, Constraint> constraintDict = new HashMap<>();

        if (conflict.getConflictType().equals(ConflictType.VERTEX)) {
            Constraint constraint = new Constraint();
            VertexConstraint vertexConstraint = new VertexConstraint(conflict.getTime(), conflict.getLocation1());
            constraint.getVertexConstraints().add(vertexConstraint);
            constraintDict.put(conflict.getAgent1(), constraint);
            constraintDict.put(conflict.getAgent2(), constraint);
        } else if (conflict.getConflictType().equals(ConflictType.EDGE)) {
            Constraint constraint1 = new Constraint();
            EdgeConstraint edgeConstraint1 = new EdgeConstraint(conflict.getTime(), conflict.getLocation1(), conflict.getLocation2());
            constraint1.getEdgeConstraints().add(edgeConstraint1);
            constraintDict.put(conflict.getAgent1(), constraint1);

            Constraint constraint2 = new Constraint();
            EdgeConstraint edgeConstraint2 = new EdgeConstraint(conflict.getTime(), conflict.getLocation2(), conflict.getLocation1());
            constraint2.getEdgeConstraints().add(edgeConstraint2);
            constraintDict.put(conflict.getAgent2(), constraint2);
        }
        return constraintDict;
    }
}
