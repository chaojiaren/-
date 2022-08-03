package tankgame04;

/**
 * @author 侯旭~
 * @version 1.0
 */
public class Bullet implements Runnable {
    private int speed = 10;
    private int x, y;
    private int bulletDirect;
    boolean isLive = true;
    //int count = 0;


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //count++;
            switch (bulletDirect) {

                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
                default:
                    break;
            }
            System.out.println(x + " " + y);
            if (!(x >= 0 && x <= HspTankGame04.x && y >= 0 && y <= HspTankGame04.y && isLive)) {
                isLive = false;
                //count--;
                break;
            }

        }
    }

    public int getBulletDirect() {
        return bulletDirect;
    }

    public void setBulletDirect(int bulletDirect) {
        this.bulletDirect = bulletDirect;
    }

    public Bullet(int x, int y, int bulletDirect) {
        // this.speed = speed;
        //this.color = color;
        this.x = x;
        this.y = y;
        this.bulletDirect = bulletDirect;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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
