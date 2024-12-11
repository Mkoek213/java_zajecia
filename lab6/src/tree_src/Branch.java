package tree_src;

import java.awt.*;

public class Branch implements XmasShape {
//    Dodaj klasę Branch - zielona gałąź drzewa
    int x;
    int y;
    int[] xPoints;
    int[] yPoints;
    double scale;
    Color fillColor;
    Color lineColor;


    public Branch(){}


    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(fillColor);
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setColor(lineColor);
        g2d.drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void transform(Graphics2D g2d){
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }
}
