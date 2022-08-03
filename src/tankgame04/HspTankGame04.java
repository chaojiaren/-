package tankgame04;

import javax.swing.*;

/**
 * @author 侯旭~
 * @version 1.0
 */
public class HspTankGame04 extends JFrame {
    //定义一个MyPanel
    MyPanel mp = null;
    public static int x = 1000;
    public static int y = 750;

    public static void main(String[] args) {

        HspTankGame04 hspTankGame01 = new HspTankGame04();
    }

    public HspTankGame04() {
        mp = new MyPanel();
        //将mp放入并启动

        this.add(mp);//把绘图区域面板，加入
        Thread thread = new Thread(mp);

        this.setSize(x, y);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addKeyListener(mp);
        this.setVisible(true);
        thread.start();

    }
}
