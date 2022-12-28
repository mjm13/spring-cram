package cbs.model;

import lombok.Data;

/**
 * @Description cbs中冲突
 * @Author MeiJM
 * @Date 2022/12/14
 **/
@Data
public class Conflict {
    private String agent1;
    private String agent2;
    private Integer time;
    private Location location1;
    private Location location2;
    private ConflictType conflictType;

}
