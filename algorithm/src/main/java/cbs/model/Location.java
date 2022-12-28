package cbs.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @Description 坐标实体
 * @Author MeiJM
 * @Date 2022/12/14
 **/
@Data
public class Location {

    public Location(String id){
        this.setId(id);
    }
    public Location(Integer x,Integer y){
        this.x = x;
        this.y = y;
        String zIndex = "01";
        String xIndex = StrUtil.padPre(String.valueOf(x),4,"0");
        String yIndex = StrUtil.padPre(String.valueOf(y),4,"0");
        id = zIndex+xIndex+yIndex;
    }
    //坐标id为横纵坐标拼接
    private String id;
    //横坐标
    private Integer x;
    //纵坐标
    private Integer y;

    public String getId() {
        if (StrUtil.isBlank(id)){
            String zIndex = "01";
            String xIndex = StrUtil.padPre(String.valueOf(x),4,"0");
            String yIndex = StrUtil.padPre(String.valueOf(y),4,"0");
            id = zIndex+xIndex+yIndex;
        }
        return id;
    }
    public void setId(String id) {
        if (StrUtil.isNotBlank(id)){
            String zIndex = "01";
            this.x = Integer.valueOf(id.substring(2,6));
            this.y = Integer.valueOf(id.substring(6,10));
        }
        this.id = id;
    }

}