package tankgame04;

/**
 * @author 侯旭~
 * @version 1.0
 */
public class Bomb {
    int x,y;
    int life = 9;//炸弹的生命周期
    boolean isLive =true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //
    public void lifeDown(){
        if(life>0){
            life--;
        }else{
            isLive=false;
        }
    }
}
