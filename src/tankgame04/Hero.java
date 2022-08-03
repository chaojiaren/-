package tankgame04;

import java.util.Vector;

/**
 * @author 侯旭~
 * @version 1.0
 */
public class Hero extends Tank {
    private int type;
    public Hero(int x, int y, int d) {
        super(x, y, d);
        type=1;

    }
    boolean isLive=true;
    //定义一个Bullet对象
    Vector<Bullet> shotBullet = new Vector<>();;
    Bullet bullet =null;
    int count =0;
    public void shotEnemyTank(){
        if(shotBullet.size()>=5){
            return;
        }
        count++;
        switch(getDirect()){
            case 0:
                bullet=new Bullet(getX() + 20, getY(),0);
                break;
            case 1:
                bullet=new Bullet(getX() + 60, getY()+20,1);
                break;
            case 2:
                bullet=new Bullet(getX() + 20, getY()+60,2);
                break;
            case 3:
                bullet=new Bullet(getX(), getY()+20,3);
                break;
            default:
                break;
        }
        shotBullet.add(bullet);
        new Thread(bullet).start();


    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
