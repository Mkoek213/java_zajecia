package tree_src;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Light implements XmasShape {
    List<XmasShape> shapes = new ArrayList<>();
    int x;
    int y;

    Light(int x, int y, int range) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < range; i++) {
            Bubble b = new Bubble();
            b.x = x + i*5;
            b.y = y - i*5;
            b.scale = 0.1;
            b.fillColor = new Color(255, 255, 0);
            b.lineColor = new Color(255, 255, 0);
            shapes.add(b);
        }
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d) {
//        g2d.setColor(new Color(192,192,192));
//        g2d.fillRect(0,0,100,100);
        for (var b : shapes) b.draw(g2d);
    }
}
