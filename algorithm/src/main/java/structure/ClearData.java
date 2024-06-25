package structure;

import java.util.*;
import java.util.stream.Collectors;

public class ClearData {
    public static void main(String[] args) {
        // 示例数据
        List<Data> dataList = Arrays.asList(
                new Data(4, 0),
//                new Data(1, 10),
                new Data(2, 1),
                new Data(3, 1),
                new Data(6, 3),
                new Data(10, 0),
                new Data(12, 11),
                new Data(5, 4)
        );

        // 最终保留的列表
        List<Data> filteredList = filterData(dataList);

        // 打印结果
        filteredList.forEach(System.out::println);
    }
    
    private static List<Data> filterData(List<Data> dataList) {
        Map<Integer,Data> map = dataList.stream().collect(Collectors.toMap(data -> data.getId(),o->o,(o, o2) -> o));
        boolean error;
        List<Data> result = new ArrayList<>(dataList);
        List<Data> checks = new ArrayList<>(dataList);
        do{
            error = false;
            for (Data data : result) {
                if(data.getPid()!=0 && map.get(data.getPid()) ==null){
                    checks.remove(data);
                    map.remove(data.getId());
                    error = true;
                }
            }
            result = new ArrayList<>(checks);
        }while (error);
        return new ArrayList<>(result);
    }
}


class Data {
    private final int id;
    private final int pid;

    public Data(int id, int pid) {
        this.id = id;
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    @Override
    public String toString() {
        return "Data{id=" + id + ", pid=" + pid + '}';
    }
}
