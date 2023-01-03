package pathPlanning.stepAstart;

import cn.hutool.core.collection.CollectionUtil;
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
    private static int MAX_COUNT = 100;
    //移动耗时字典
    private Map<Integer, Integer> costMap = ImmutableMap.of(1, 3, 2, 4, 3, 5);

    public List<RgvCommand> searchPath(SaAgent saAgent, SaEnvironment environment, List<SaMoveObstacle> saMoveObstacles) {
        StepAstart stepAstart = new StepAstart();
        int count = 0;
        Map<String, List<SaMoveObstacle>> obstacleMap = saMoveObstacles.stream().
                collect(Collectors.groupingBy(saMoveObstacle -> saMoveObstacle.getSaLocation().getId()));
        List<RgvCommand> commands;
        while (true) {
            //计算路径
            List<SaProbeNode> path = stepAstart.searchPath(saAgent, environment);
            if (path == null) {
                continue;
            }
            //拆分命令
            SaProbeNode start = path.get(0);
            SaDirection lastDirection = start.getSaDirection();
            SaLocation lastLocation = start.getSaLocation();
            commands = new ArrayList<>();
            RgvCommand command = new RgvCommand();
            command.setStartX(lastLocation.getX());
            command.setStartY(lastLocation.getY());
            command.setDirection(lastDirection);
            command.getNodes().add(start);
            for (int i = 1; i < path.size(); i++) {
                SaProbeNode node = path.get(i);
                /**
                 * 1. 当同一个点位连续出现两次表示等待命令
                 * 2.当下一个点位与上一个点位方向不一致时会生成新命令
                 * 3.最后一个点位会生成新命令
                 */
                if (lastLocation.equals(node.getSaLocation()) || !node.getSaDirection().equals(lastDirection) || i == path.size() - 1) {
                    command.setEndX(lastLocation.getX());
                    command.setEndY(lastLocation.getY());
                    commands.add(command);
                    command = new RgvCommand();
                    command.setStartX(node.getSaLocation().getX());
                    command.setStartY(node.getSaLocation().getY());
                    command.setDirection(node.getSaDirection());
                    command.getNodes().add(node);
                } else {
                    command.getNodes().add(node);
                }
                lastLocation = node.getSaLocation();
                lastDirection = node.getSaDirection();
            }
//            log.info("移动命令：{}", JSONUtil.toJsonStr(commands));
            Date startTime = new Date();
            SaProbeNode conflictNode = null;
            for (RgvCommand rgvCommand : commands) {
                //计算命令耗时
                rgvCommand.setStartTime(startTime);
                int cost = costMap.get(rgvCommand.getDistance());
                Date endTime = DateUtil.offsetSecond(startTime, cost);
                rgvCommand.setCostTime(cost);
                rgvCommand.setEndTime(endTime);
                startTime = endTime;
                conflictNode = getConflict(rgvCommand, obstacleMap);
                if (conflictNode != null) {
                    break;
                }
            }
            if (conflictNode == null) {
                return commands;
            } else if (count > MAX_COUNT) {
                break;
            }
            count++;
        }
        return null;
    }

    private SaProbeNode getConflict(RgvCommand rgvCommand, Map<String, List<SaMoveObstacle>> obstacleMap) {
        //检查命令冲突
        for (SaProbeNode node : rgvCommand.getNodes()) {
            Date startTime = rgvCommand.getStartTime();
            Date endTime = rgvCommand.getEndTime();
            List<SaMoveObstacle> tempMos = obstacleMap.get(node.getSaLocation().getId());
            if (CollectionUtil.isEmpty(tempMos)) {
                continue;
            }
            for (SaMoveObstacle tempMo : tempMos) {
                if ((startTime.before(tempMo.getStart()) && endTime.after(tempMo.getStart())) ||
                        (startTime.before(tempMo.getEnd()) && endTime.after(tempMo.getEnd()))) {
                    return node;
                }
            }
        }
        return null;
    }

}
