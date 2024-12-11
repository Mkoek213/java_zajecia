package tree_src;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tree implements XmasShape {
//    Możesz utworzyć klasę Tree zawierającą listę XmasShape i złożyć gotowe drzewko z gałęzi
    List<XmasShape> branches = new ArrayList<>();
    int x;
    int y;

    public Tree(int x, int y){
        this.x = x;
        this.y = y;
        for(int i=0; i<5; i++){
            Branch b = new Branch();
            b.x = x + i*25;
            b.y = y - i*80;
            b.scale = 1.0 - i*0.1;
            b.xPoints = new int[]{0, 250, 500};
            b.yPoints = new int[]{250, 0, 250};
            b.fillColor = new Color(0, 128, 0);
            b.lineColor = new Color(0, 128, 0);
            branches.add(b);
        }
    }

    @Override
    public void transform(Graphics2D g2d){
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d){
        for(var b:branches)b.draw(g2d);
    }
}
