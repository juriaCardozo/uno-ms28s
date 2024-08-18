package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorderJPanel extends JPanel {

    private Color backgroundColor;
    private final int radius;

    public RoundedBorderJPanel(Color backgroundColor, int radius) {
        this.backgroundColor = backgroundColor;
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    public void setBackground(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        super.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(backgroundColor);
        Graphics2D g2d = (Graphics2D) g.create();
        RoundRectangle2D shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        g2d.fill(shape);
        g2d.draw(shape);
    }
}