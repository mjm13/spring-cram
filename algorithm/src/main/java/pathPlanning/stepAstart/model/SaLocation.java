package pathPlanning.stepAstart.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.ToString;

/**
 * @Description 坐标信息
 * @Author MeiJM
 * @Date 2022/12/29
 **/
@Data
@ToString(exclude = {"x","y"})
public class SaLocation {
    private String id;
    private int x;
    private int y;
    public SaLocation(String id){
        this.x = Integer.valueOf(id.substring(0,4));
        this.y = Integer.valueOf(id.substring(4,8));
        this.id = id;
    }
    public SaLocation(Integer x, Integer y){
        this.x = x;
        this.y = y;
        String xIndex = StrUtil.padPre(String.valueOf(x),4,"0");
        String yIndex = StrUtil.padPre(String.valueOf(y),4,"0");
        id = xIndex+yIndex;
    }
    public String getId() {
        if (StrUtil.isBlank(id)){
            String xIndex = StrUtil.padPre(String.valueOf(x),4,"0");
            String yIndex = StrUtil.padPre(String.valueOf(y),4,"0");
            id = xIndex+yIndex;
        }
        return id;
    }
}
