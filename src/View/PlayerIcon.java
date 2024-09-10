package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PlayerIcon implements Icon {
    private final int width;
    private final int height;

    public PlayerIcon(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);
        paintCardBack(g2d);
        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    private void paintCardBack(Graphics2D g) {
        int cardWidth = width;
        int cardHeight = height;

        int margin = 2;
        g.setColor(Color.BLACK);
        g.fillRect(margin, margin, cardWidth - 2 * margin, cardHeight - 2 * margin);

        g.setColor(Color.RED);
        AffineTransform org = g.getTransform();
        g.rotate(45, cardWidth * 3 / 4, cardHeight * 3 / 4);
        g.fillOval(0, cardHeight / 4, cardWidth * 3 / 5, cardHeight);
        g.setTransform(org);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, cardWidth, margin);
        g.fillRect(0, cardHeight - margin, cardWidth, margin);
        g.fillRect(0, margin, margin, cardHeight);
        g.fillRect(cardWidth - margin, 0, margin, cardHeight);

        Font defaultFont = new Font("Helvetica", Font.BOLD, cardWidth / 3);
        FontMetrics fm = g.getFontMetrics(defaultFont);
        int stringWidth = fm.stringWidth("UNO") / 2;
        int fontHeight = defaultFont.getSize() / 3;

        g.setColor(Color.YELLOW);
        g.setFont(defaultFont);
        g.drawString("UNO", cardWidth / 2 - stringWidth, cardHeight / 2 + fontHeight);
    }
}
