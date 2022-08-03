package tankgame04;

/**
 * @author 侯旭~
 * @version 1.0
 */
public class Tank {
    private int x;//坦克横向坐标
    private int y;//坦克纵向坐标
    private int direct;//direct (0向上，1向右，2向下 ，3向左)
    private int speed = 5;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //上右下左移动方法
    //direct (0向上，1向右，2向下 ，3向左)
    public void moveUp(){
        //direct=0;
        y -= speed;
        if (boundary(x, y)) {
            y +=speed;
        }
    }
    public void moveDown(){
        //direct=2;
        y += speed;
        if (boundary(x, y)) {
            y -= speed;
        }
    }
    public void moveLeft(){
        //direct=3;
        x -= speed;
        if (boundary(x, y)) {
            x += speed;

        }
    }
    public void moveRight(){
        //direct=1;
        x += speed;
        if (boundary(x, y)) {
            x -=speed;
        }
    }

    //坦克出界
    public boolean boundary(int x, int y) {
        return ((x < 0 || y < 0 || x > HspTankGame04.x - 60-15 || y > HspTankGame04.y - 60-40));
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y,int d) {
        this.x = x;
        this.y = y;
        this.direct = d;

    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
