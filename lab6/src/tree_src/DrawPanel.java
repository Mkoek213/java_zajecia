package tree_src;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class DrawPanel extends JPanel {

    List<XmasShape> shapes = new ArrayList<>();

//    Dodaj kilka obiektów klasy Bubble i sprawdź, czy wyświetlane są zgodnie z oczekiwaniami. Napisz osobną funkcje do dodawania obiektów w DrawPanel i wywołaj ją w konstruktorze lub funkcji main(). Nie dodawaj obiektów w paintCpmponent()
    public void addShape(XmasShape shape){
        shapes.add(shape);
    }

    public DrawPanel(){
        setBackground(new Color(0,0,50));
        addShape(new Tree(120,180));
        addShape(new Bubble(500,80,0.5,Color.RED,Color.GREEN));
        addShape(new Bubble(490,180,0.5,Color.GREEN,Color.RED));
        addShape(new Bubble(510,310,0.5,Color.BLACK,Color.BLUE));
        addShape(new Bubble(505,380,0.5,Color.BLUE,Color.ORANGE));
        addShape(new Bubble(485,460,0.5,Color.WHITE,Color.PINK));
        addShape(new Bucket(120,180, 220, 520, 150, 350 ));
        addShape(new Bucket(200, 100, 200, 350, 50, 250));
        addShape(new Bucket(200, 100, 200, 350, 50, 150));
        addShape(new Bucket(200, 280, 200, 450, 230, 300));
        addShape(new Light(200, 100, 25));
        addShape(new Light(180, 200, 42));
        addShape(new Light(200, 300, 42));
        addShape(new Star(490, 50, 1));



    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(XmasShape s:shapes){
            s.draw((Graphics2D)g);
        }
        // draw trunk
        g.setColor(new Color(139,69,19));
        g.fillRect(480, 610, 50, 50);
        g.setColor(new Color(139,69,19));
        g.drawRect(480, 610, 50, 50);
    }
}