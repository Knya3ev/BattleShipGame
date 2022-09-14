package graphics;

import javax.swing.*;
import java.awt.*;

public class ImagesGame {
    private final Image emptyBox;
    private final Image ship;
    private final Image hit;
    private final Image miss;
    private final Image aim;

    private final Image[] renderBoxes;


    public ImagesGame() {
        ImageIcon emptyBoxIcon = new ImageIcon("resources/box.png");
        ImageIcon shipIcon = new ImageIcon("resources/boxShip.png");
        ImageIcon hitIcon = new ImageIcon("resources/hit.png");
        ImageIcon missIcon = new ImageIcon("resources/miss.png");

        emptyBox = emptyBoxIcon.getImage();
        ship = shipIcon.getImage();
        hit = hitIcon.getImage();
        miss = missIcon.getImage();
        renderBoxes = new Image[]{emptyBox,ship,hit,miss};

        ImageIcon aimIcon = new ImageIcon("resources/aim.png");
        aim = aimIcon.getImage();
    }

    public Image getRenderBox(int index){
        return renderBoxes[index];
    }
    public Image getAim(){
        return aim;
    }

}
