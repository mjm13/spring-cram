package pathPlanning.astart;

import pathPlanning.astart.model.Coord;
import pathPlanning.astart.model.Node;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AstartTest {
    public static void main(String[] args) {
        List<Coord> mapInfos = IntStream.range(0,100).mapToObj(operand -> {
            int x =  operand%10+1;
            int y =  operand/10+1;
            Coord mapInfo = new Coord();
            String zIndex = "01";
            String xIndex = StrUtil.padPre(String.valueOf(x),4,"0");
            String yIndex = StrUtil.padPre(String.valueOf(y),4,"0");
            mapInfo.setId(zIndex+xIndex+yIndex);
            mapInfo.setX(x);
            mapInfo.setY(y);
             if(x==2 &&  y%5==0){
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(true);
                mapInfo.setRight(true);
            }else if(x%5 ==0 && y!= 1 && y!= 5 && y!= 10){
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(false);
                mapInfo.setRight(false);
            }else if(x ==1){
                 mapInfo.setDown(true);
                 mapInfo.setUp(true);
                 mapInfo.setLeft(true);
                 mapInfo.setRight(true);
             }else if( y==1){
                mapInfo.setLeft(true);
                mapInfo.setRight(true);
                mapInfo.setDown(true);
                mapInfo.setUp(true);
            }else if(x==2 ){
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(true);
                mapInfo.setRight(false);
            }else if (y%5==0) {
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(true);
                mapInfo.setRight(true);
            }else{
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(false);
                mapInfo.setRight(false);
            }
            return mapInfo;
        }).collect(Collectors.toList());

        AStar aStar = new AStar();
        Coord start = new Coord();
        start.setId("0100030001");
        Coord end = new Coord();
        end.setId("0100070010");
        Node endNode = aStar.start(mapInfos,start,end);
        aStar.printMap();

        System.out.println("");
        StringBuffer result = new StringBuffer();

        do{
            result.append(endNode.getCoord().getId()).append(")").append("-");
            endNode = endNode.getParent();
        }while (!endNode.getParent().getCoord().equals(endNode.getCoord()));
        result.append(endNode.getCoord().getId()).append(")");
        System.out.println(result.toString());
    }
}
