package tankgame04;

import java.util.Vector;

/**
 * @author 侯旭~
 * @version 1.0
 */
public class Enemy extends Tank implements Runnable {
    Vector<Bullet> bullets = new Vector<>();
    boolean isLive = true;
    int direct = 2;

    //EnemyTankMove move = new EnemyTankMove(getX(),getY(),getDirect());
    public Enemy(int x, int y) {
        super(x, y);
        super.setType(0);
//        super.setDirect(2);


    }


    //int count =0;
    public synchronized void shotTank() {
        //count++;
        if (bullets.size() >= 5) {
            return;
        }
        Bullet bullet = null;
        switch (getDirect()) {
            case 0:
                bullet = new Bullet(getX() + 20, getY(), 0);
                break;
            case 1:
                bullet = new Bullet(getX() + 60, getY() + 20, 1);
                break;
            case 2:
                bullet = new Bullet(getX() + 20, getY() + 60, 2);
                break;
            case 3:
                bullet = new Bullet(getX(), getY() + 20, 3);
                break;
            default:
                break;
        }
        bullets.add(bullet);
        new Thread(bullet).start();


    }

    /**
     * 设计敌方坦克不会重叠
     */
    public boolean can = true;

    //坦克碰撞,过不去返回false
    public boolean crashTank(Tank hero, Tank enemy) {
        int b = 0, a = 0;
        if (enemy.getDirect() == 0 || enemy.getDirect() == 2) {
            a = 40;
            b = 60;
        } else if (enemy.getDirect() == 1 || enemy.getDirect() == 3) {
            a = 60;
            b = 40;
        }
        switch (hero.getDirect()) {
            case 0:
                if (hero.getX() > (enemy.getX() - 40) && hero.getX() < (enemy.getX() + a) && hero.getY() == (enemy.getY() + b - hero.getSpeed())) {
                    return false;
                }

            case 1:
                if (hero.getX() + 60 == enemy.getX() + hero.getSpeed() && hero.getY() > (enemy.getY() - 40) && hero.getY() < (enemy.getY() + b)) {
                    return false;
                }

            case 2:
                if (hero.getX() > (enemy.getX() - 40) && hero.getX() < (enemy.getX() + a) && hero.getY() + 60 == (enemy.getY()) + hero.getSpeed()) {
                    return false;
                }

            case 3:
                if (hero.getX() == (enemy.getX() + a - hero.getSpeed()) && hero.getY() > (enemy.getY() - 40) && hero.getY() < (enemy.getY() + b)) {
                    return false;
                }

        }
        return true;
    }


    int m = 0;
    boolean loop = true;

    @Override
    public void run() {
        synchronized (this) {
            while (loop) {
                shotTank();
                switch (getDirect()) {
                    case 0:
                        //让坦克保持一个方向走三十下
                        for (int i = 0; i < 30; i++) {
                            if (can) {
                                moveUp();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        for (int i = 0; i < 30; i++) {
                            if (can) {
                                moveRight();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < 30; i++) {
                            if (can) {
                                moveDown();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 3:
                        for (int i = 0; i < 30; i++) {
                            if (can) {
                                moveLeft();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
                //m = (int) (Math.random() * 400);
                //m=2;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setDirect((int) (Math.random() * 4));
                if (!isLive) {
                    break;
                }
                //shotTank();
//                //向下
//                if (m >= 0 && m < 100) {
//
//                    setDirect(2);
//                    moveDown();
//                    //向左
//                } else if (m < 200) {
//
//                    setDirect(3);
//                    moveLeft();
//
//                    //向右
//                } else if (m < 300) {
//
//                    setDirect(1);
//                    moveRight();
//
//                    //向上
//                } else if (m < 400) {
//
//                    setDirect(0);
//                    moveUp();
//
//                }
            }
        }
    }
}
