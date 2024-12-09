package pathPlanning.dstarm;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class Coord {

    private Integer x;
    
    private Integer y;

    private boolean up;
    
    private boolean down;
    
    private boolean left;
    
    private boolean right;

    public String getId() {
        return "01" + StrUtil.padPre(String.valueOf(x), 4, "0") +
                StrUtil.padPre(String.valueOf(y), 4, "0");
    }

    @Override
    public String toString() {
        return getId();
    }

    public String getMark(){
        String result = "";
        if (up) {
            result += "U";
        }else{
            result += " ";
        }
        if (down) {
            result += "D";
        }else{
            result += " ";
        }
        if (left) {
            result += "L";
        }else{
            result += " ";
        }
        if (right) {
            result += "R";
        }else{
            result += " ";
        }
        return result;
    }
}