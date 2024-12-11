package tree_src;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bucket implements XmasShape {
    List<XmasShape> shapes = new ArrayList<>();
    int x;
    int x_lower_bound;
    int x_upper_bound;
    int y_lower_bound;
    int y_upper_bound;
    int y;
    Bucket(int x,int y, int x_lower_bound, int x_upper_bound, int y_lower_bound, int y_upper_bound){
        this.x=x;
        this.y=y;
        Random r = new Random();
        for(int i=0;i<10;i++){
            Bubble b = new Bubble();
            b.x=r.nextInt(x_lower_bound, x_upper_bound);
            b.y=r.nextInt(y_lower_bound, y_upper_bound);
            b.scale=r.nextDouble()*0.5;
            b.fillColor=new Color(r.nextFloat(),r.nextFloat(),r.nextFloat());
            b.lineColor=new Color(r.nextFloat(),r.nextFloat(),r.nextFloat());
            shapes.add(b);
        }
    }
    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
    }

    @Override
    public void render(Graphics2D g2d) {
//        g2d.setColor(new Color(192,192,192));
//        g2d.fillRect(0,0,100,100);
        for(var b:shapes)b.draw(g2d);
    }
}
