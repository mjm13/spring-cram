package structure;

public class Data {
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
