package pathPlanning.stepAstart;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import pathPlanning.stepAstart.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 在步骤基础之上增加时间维度
 * @Author MeiJM
 * @Date 2022/12/29
 **/
@Slf4j
public class StepTimeAstart {
    //移动耗时字典
    private Map<Integer,Integer> costMap = ImmutableMap.of(1,3,2,4,3,5);

    private static int MAX_COUNT = 100;

    public List<SaProbeNode> searchPath(SaAgent saAgent, SaEnvironment environment, List<SaMoveObstacle> saMoveObstacles) {
        StepAstart stepAstart = new StepAstart();
        int count = 0;
        Map<String,List<SaMoveObstacle>> obstacleMap = saMoveObstacles.stream().
                collect(Collectors.groupingBy(saMoveObstacle -> saMoveObstacle.getSaLocation().getId()));
        while (true) {
            //计算路径
            List<SaProbeNode> path = stepAstart.searchPath(saAgent, environment);
            if (path == null) {
                continue;
            }
            //拆分命令
            List<RgvCommand> commands = new ArrayList<>();
            SaProbeNode start = path.get(0);
            SaDirection direction = start.getSaDirection();
            SaLocation lastLocation = start.getSaLocation();
            RgvCommand command = new RgvCommand();
            command.setStartX(start.getSaLocation().getX());
            command.setStartY(start.getSaLocation().getY());
            command.getNodes().add(start);
            for (int i = 1; i < path.size(); i++) {
                SaProbeNode node = path.get(i);
                if (!node.getSaDirection().equals(direction) || i==path.size()-1) {
                    command.setEndX(node.getSaLocation().getX());
                    command.setEndY(node.getSaLocation().getY());
                    commands.add(command);
                    command = new RgvCommand();
                    command.setStartX(node.getSaLocation().getX());
                    command.setStartY(node.getSaLocation().getY());
                    command.getNodes().add(node);
                }else{
                    command.getNodes().add(node);
                }
            }
            log.info("移动命令：{}", JSONUtil.toJsonStr(commands));
            Date startTime = new Date();
            for (RgvCommand rgvCommand : commands) {
                //计算命令耗时
                rgvCommand.setStartTime(startTime);
                int cost = costMap.get(rgvCommand.getDistance());
                Date endTime = DateUtil.offsetSecond(startTime,cost);
                rgvCommand.setCostTime(cost);
                rgvCommand.setEndTime(endTime);

                SaProbeNode conflictNode = getConflict(rgvCommand,obstacleMap);
                startTime = endTime;
            }
            if (saMoveObstacles.contains(path)) {
                return path;
            }else if(count > MAX_COUNT){
                break;
            }
            count++;
        }

        return null;
    }

    private SaProbeNode getConflict(RgvCommand rgvCommand, Map<String,List<SaMoveObstacle>> obstacleMap){
        //检查命令冲突
        for (SaProbeNode node : rgvCommand.getNodes()) {
            Date startTime = rgvCommand.getStartTime();
            Date endTime = rgvCommand.getEndTime();
            List<SaMoveObstacle> tempMos = obstacleMap.get(node.getSaLocation().getId());
            for (SaMoveObstacle tempMo : tempMos) {
                if ((startTime.before(tempMo.getStart()) && endTime.after(tempMo.getStart())) ||
                        (startTime.before(tempMo.getEnd()) && endTime.after(tempMo.getEnd())) ) {
                    return node;
                }
            }
        }
        return null;
    }

}
