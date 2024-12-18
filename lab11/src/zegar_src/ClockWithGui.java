package zegar_src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

public class ClockWithGui extends JPanel {

    LocalTime time = LocalTime.now();
    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        ClockWithGui clockPanel = new ClockWithGui();
        frame.setContentPane(clockPanel);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        ClockThread clockThread = clockPanel.new ClockThread();
        clockThread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 50;

        g2d.translate(width / 2, height / 2);
        drawClockFace(g2d, radius);
        drawHands(g2d, radius);
    }

    private void drawClockFace(Graphics2D g2d, int radius) {
        g2d.setStroke(new BasicStroke(4));
        g2d.drawOval(-radius, -radius, 2 * radius, 2 * radius);

        for (int i = 1; i <= 12; i++) {
            AffineTransform at = new AffineTransform();
            at.rotate(Math.toRadians(30 * i));
            Point2D src = new Point2D.Float(0, -radius + 30);
            Point2D trg = new Point2D.Float();
            at.transform(src, trg);
            String number = Integer.toString(i);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(number);
            int textHeight = fm.getAscent();
            g2d.drawString(number, (int) trg.getX() - textWidth / 2, (int) trg.getY() + textHeight / 2);
        }

        // Draw minute ticks
        g2d.setStroke(new BasicStroke(1));
        for (int i = 0; i < 60; i++) {
            AffineTransform saveAT = g2d.getTransform();
            g2d.rotate(Math.toRadians(6 * i));
            if (i % 5 != 0) {
                g2d.drawLine(0, -radius + 10, 0, -radius + 20);
            }
            g2d.setTransform(saveAT);
        }
    }

    private void drawHands(Graphics2D g2d, int radius) {
        LocalTime currentTime = time;

        double hourAngle = Math.toRadians(360 / 12 * (currentTime.getHour() % 12) + 360 / 12 * (currentTime.getMinute() / 60.0));
        double minuteAngle = Math.toRadians(360 / 60 * currentTime.getMinute() + 360 / 60 * (currentTime.getSecond() / 60.0));
        double secondAngle = Math.toRadians(360 / 60 * currentTime.getSecond());

        AffineTransform saveAT = g2d.getTransform();

        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2d.rotate(hourAngle);
        g2d.drawLine(0, 0, 0, -radius / 2);
        g2d.setTransform(saveAT);

        g2d.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2d.rotate(minuteAngle);
        g2d.drawLine(0, 0, 0, -3 * radius / 4);
        g2d.setTransform(saveAT);

        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2d.setColor(Color.RED);
        g2d.rotate(secondAngle);
        g2d.drawLine(0, 0, 0, -radius + 30);
        g2d.setTransform(saveAT);
    }

    class ClockThread extends Thread {
        @Override
        public void run() {
            while (true) {
                time = LocalTime.now();
                repaint();
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
