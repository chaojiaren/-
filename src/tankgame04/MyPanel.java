package tankgame04;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author 侯旭~
 * @version 1.0
 * 坦克大战的绘图区
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    Vector<Enemy> enemyTanks = null;

    //定义一个Vector,存放炸弹
    Vector<Bomb> bombs = new Vector<>();

    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    public MyPanel() {
        hero = new Hero(500, 500, 1);//初始化自己的坦克
        enemyTanks = createEnemy(12);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形,默认是黑色
        //画出坦克-方法
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), hero.getType());
        }
        //画出敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            Enemy enemy = enemyTanks.get(i);
            //判断坦克是否存活
            if (enemy.isLive) {
                drawTank(enemy.getX(), enemy.getY(), g, enemy.getDirect(), enemy.getType());
                for (int j = 0; j < enemy.bullets.size(); j++) {
                    Bullet bullet = enemy.bullets.get(j);
                    if (bullet.isLive) {
                        g.fill3DRect(bullet.getX(), bullet.getY(), 2, 2, false);
                        hitHeroTank(bullet, hero);
                    } else {
                        enemy.bullets.remove(bullet);
                    }
                }
            } else {
                enemyTanks.remove(enemy);
                enemy.loop = false;
            }
            if (!hero.isLive) {
                enemy.loop = false;
            }

        }

        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_3.gif"));

        //坦克爆炸
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);

            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();

            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        //画出hero射击子弹
        for (int i = 0; i < hero.shotBullet.size(); i++) {
            Bullet bullet = hero.shotBullet.get(i);
            if (bullet != null && (bullet.isLive)) {
                g.setColor(Color.red);
                g.fill3DRect(bullet.getX(), bullet.getY(), 2, 2, false);
            }
            if (!bullet.isLive) {
                hero.shotBullet.remove(bullet);
            }

        }
    }
    //编写方法画出坦克

    /**
     * @param x      坦克的左上角横坐标
     * @param y      坦克左上角纵坐标
     * @param g      画笔
     * @param direct 方向
     * @param type   类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0: //敌人的坦克
                g.setColor(Color.cyan);
                break;
            case 1: //我们的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克方向来绘制坦克
        //direct (0向上，1向右，2向下 ，3向左)
        switch (direct) {
            case 0://表示向上
                //左轮
                g.fill3DRect(x, y, 10, 60, false);
                //体
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //右轮
                g.fill3DRect(x + 30, y, 10, 60, false);
                //炮塔
                g.fillOval(x + 10, y + 20, 20, 20);
                //炮管
                g.drawLine(x + 20, y + 30, x + 20, y);

                break;
            case 2://表示向下
                //左轮
                g.fill3DRect(x, y, 10, 60, false);
                //体
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //右轮
                g.fill3DRect(x + 30, y, 10, 60, false);
                //炮塔
                g.fillOval(x + 10, y + 20, 20, 20);
                //炮管
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 1://表示向右
                //上轮
                g.fill3DRect(x, y, 60, 10, false);
                //体
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //下轮
                g.fill3DRect(x, y + 30, 60, 10, false);
                //炮塔
                g.fillOval(x + 20, y + 10, 20, 20);
                //炮管
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;

            case 3://表示向左
                //上轮
                g.fill3DRect(x, y, 60, 10, false);
                //体
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //下轮
                g.fill3DRect(x, y + 30, 60, 10, false);
                //炮塔
                g.fillOval(x + 20, y + 10, 20, 20);
                //炮管
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    //direct (0向上，1向右，2向下 ，3向左)
    @Override
    public void keyPressed(KeyEvent e) {
        //向下
        if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);

            hero.moveDown();
            if (!crashEnemyTank(hero)) {
                int y = hero.getY() - hero.getSpeed();
                hero.setY(y);
            }
            //向左
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            hero.moveLeft();
            if (!crashEnemyTank(hero)) {
                int x = hero.getX() + hero.getSpeed();
                hero.setX(x);
            }
            //向右
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            hero.moveRight();
            if (!crashEnemyTank(hero)) {
                int x = hero.getX() - hero.getSpeed();
                hero.setX(x);
            }
            //向上
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            hero.moveUp();
            if (!crashEnemyTank(hero)) {
                int y = hero.getY() + hero.getSpeed();
                hero.setY(y);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_H) {
            int s = hero.getSpeed() + 5;
            if (s <= 10) {
                hero.setSpeed(s);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_N) {
            int s = hero.getSpeed() - 5;
            if (s > 0) {
                hero.setSpeed(s);
            }
        }
        //如果按下J就发射
        if (e.getKeyCode() == KeyEvent.VK_J) {
//            for (int i = 0; i < hero.shotBullet.size(); i++) {
//                Bullet bullet = hero.shotBullet.get(i);
//                if(hero.bullet==null||!hero.bullet.isLive)

            hero.shotEnemyTank();

            //}
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //编写方法创建敌方坦克
    public Vector<Enemy> createEnemy(int n) {
        Vector<Enemy> enemyTanks = new Vector<>();
        int x, y, d;
        for (int i = 0; i < n; i++) {
            x = (i + 1) * 60;
            y = 0;
            //d = 2;
            Enemy enemy = new Enemy(x, y);
            //new Thread(enemy).start();
            //enemy.setDirect(d);
//            Bullet bullet = null;
//            switch (d) {
//                case 0:
//                    bullet = new Bullet(x + 20, y, 0);
//                    break;
//                case 1:
//                    bullet = new Bullet(x + 60, y + 20, 1);
//                    break;
//                case 2:
//                    bullet = new Bullet(x + 20, y + 60, 2);
//                    break;
//                case 3:
//                    bullet = new Bullet(x, y + 20, 3);
//                    break;
//                default:
//                    break;
//            }
//            enemy.bullets.add(bullet);
//            Thread thread = new Thread(bullet);
//            thread.start();
            enemyTanks.add(enemy);

        }
        return enemyTanks;
    }


    //编写方法，判断我方子弹是否击中
    public void hitTank(Bullet bullet, Enemy enemy) {
        switch (enemy.getDirect()) {
            case 0:
            case 2:
                if (bullet.getX() > enemy.getX() &&
                        bullet.getX() < enemy.getX() + 40 &&
                        bullet.getY() > enemy.getY() &&
                        bullet.getY() < enemy.getY() + 60) {
                    bullet.isLive = false;
                    enemy.isLive = false;

                    Bomb bomb = new Bomb(enemy.getX(), enemy.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if (bullet.getX() > enemy.getX() &&
                        bullet.getX() < enemy.getX() + 60 &&
                        bullet.getY() > enemy.getY() &&
                        bullet.getY() < enemy.getY() + 40) {
                    bullet.isLive = false;
                    enemy.isLive = false;

                    Bomb bomb = new Bomb(enemy.getX(), enemy.getY());
                    bombs.add(bomb);
                }
                break;
            default:
                break;

        }
    }

    //编写方法，判断敌方子弹是否击中
    public void hitHeroTank(Bullet bullet, Hero hero) {
        switch (hero.getDirect()) {
            case 0:
            case 2:
                if (bullet.getX() > hero.getX() &&
                        bullet.getX() < hero.getX() + 40 &&
                        bullet.getY() > hero.getY() &&
                        bullet.getY() < hero.getY() + 60) {
                    bullet.isLive = false;
                    hero.isLive = false;

                    Bomb bomb = new Bomb(hero.getX(), hero.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if (bullet.getX() > hero.getX() &&
                        bullet.getX() < hero.getX() + 60 &&
                        bullet.getY() > hero.getY() &&
                        bullet.getY() < hero.getY() + 40) {
                    bullet.isLive = false;
                    hero.isLive = false;

                    Bomb bomb = new Bomb(hero.getX(), hero.getY());
                    bombs.add(bomb);
                }
                break;
            default:
                break;

        }
    }


    //我方碰撞敌方坦克无法移动
    public boolean crashEnemyTank(Tank hero) {
        for (int i = 0; i < enemyTanks.size(); i++) {
            Enemy enemy = enemyTanks.get(i);
            if (!(crashTank(hero, enemy))) {
                return false;
            }
        }
        return true;
    }

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

    /**
     * 设计敌方坦克不会重叠
     */
    public void crashHeroTank(Enemy nowEnemyTank) {
        for (int i = 0; i < enemyTanks.size(); i++) {
            nowEnemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTanks.size(); j++) {
                if (i != j) {
                    Enemy otherEnemy = enemyTanks.get(j);
                    if (!(nowEnemyTank.crashTank(nowEnemyTank, otherEnemy))) {
                        nowEnemyTank.can =false;
                    }
                }
            }

        }
    }




    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否击中了敌人
            for (int j = 0; j < hero.shotBullet.size(); j++) {
                Bullet bullet = hero.shotBullet.get(j);
                if (bullet != null && bullet.isLive) {
                    //遍历敌人所有坦克
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        Enemy enemy = enemyTanks.get(i);
                        hitTank(bullet, enemy);

                    }
                }

            }
            this.repaint();
            if (!hero.isLive) {
                break;
            }
        }
    }
}
