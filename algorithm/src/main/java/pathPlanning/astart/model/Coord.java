package pathPlanning.astart.model;

import lombok.Data;

@Data
public class Coord {
    //坐标集合
    private String id;

    private Integer x;
    
    private Integer y;

    private boolean up;
    
    private boolean down;
    
    private boolean left;
    
    private boolean right;

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