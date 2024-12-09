package pathPlanning.dstart.model;

import cn.hutool.core.util.StrUtil;

/**
 * 坐标点类
 */
public class Coord {
    private final int x;
    private final int y;
    private boolean up = true;
    private boolean down = true;
    private boolean left = true;
    private boolean right = true;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return "01" + StrUtil.padPre(String.valueOf(x), 4, "0") +
               StrUtil.padPre(String.valueOf(y), 4, "0");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}