package astart;

import astart.model.Coord;
import astart.model.Node;
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
            if(x ==1){
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(true);
                mapInfo.setRight(true);
            }else if(x==2 &&  y%5==0){
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(true);
                mapInfo.setRight(true);
            }else if(x==2 ){
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(true);
                mapInfo.setRight(false);
            }else if (x%5 ==0 && y%5==0) {
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(true);
                mapInfo.setRight(true);
            }else if(x%5 ==0){
                mapInfo.setDown(true);
                mapInfo.setUp(true);
                mapInfo.setLeft(false);
                mapInfo.setRight(false);
            }else if(y%5==0){
                mapInfo.setLeft(true);
                mapInfo.setRight(true);
                mapInfo.setDown(false);
                mapInfo.setUp(false);
            }else{
                mapInfo.setDown(false);
                mapInfo.setUp(false);
                mapInfo.setLeft(false);
                mapInfo.setRight(false);
            }
            return mapInfo;
        }).collect(Collectors.toList());

        AStar aStar = new AStar();
        Coord start = new Coord();
        start.setId("0100010001");
        Coord end = new Coord();
        end.setId("0100100009");
        Node endNode = aStar.start(mapInfos,start,end);
        aStar.printMap();

        System.out.println("");
        StringBuffer result = new StringBuffer();

        while (!endNode.getParent().getCoord().equals(endNode.getCoord())){
//            System.out.println(endNode.getCoord().getId());
            result.append(endNode.getCoord().getId()).append("-");
            endNode = endNode.getParent();
        }
        System.out.println(result.toString());
    }
}
