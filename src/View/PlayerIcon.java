package View;

import Interfaces.UNOConstants_Original;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PlayerIcon implements Icon {
    private int width;
    private int height;
    private final String iconValue;

    public PlayerIcon(int width, int height, String iconValue) {
        this.width = width;
        this.height = height;
        this.iconValue = iconValue;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setClip(x, y, width, height);
        g2d.translate(x, y);
        paintPlayerIcon(g2d, iconValue);
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

    public void setIconSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void paintPlayerIcon(Graphics2D g2d, String iconValue) {
        switch (iconValue) {
            case UNOConstants_Original.UNO_CARDBACK -> paintUnoCard(g2d);
            default -> paintGenericCard(g2d, iconValue);
        }
    }

    private void paintGenericCard(Graphics2D g, String value) {
        int cardWidth = width;
        int cardHeight = height;

        //Paints the external border of the card
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, cardWidth, cardHeight);

        //Paints the color of the card
        int margin = 2;
        g.setColor(new Color(0, 0, 0));
        g.fillRect(margin, margin, cardWidth-2*margin, cardHeight-2*margin);

        //Paints the oval format in the center of the card
        g.setColor(Color.white);
        AffineTransform org = g.getTransform();
        g.rotate(45,cardWidth*3/4,cardHeight*3/4);

        g.fillOval(0,cardHeight/4,cardWidth*3/5, cardHeight);
        g.setTransform(org);

        //Value in the center
        Font defaultFont = new Font("Helvetica", Font.BOLD, cardWidth/2);
        FontMetrics fm = g.getFontMetrics(defaultFont);
        int StringWidth = fm.stringWidth(value)/2;
        int FontHeight = defaultFont.getSize()/4;

        g.setColor(new Color(0, 0, 0));
        g.setFont(defaultFont);
        g.drawString(value, cardWidth/2-StringWidth, cardHeight/2+FontHeight);
    }

    private void paintUnoCard(Graphics2D g) {
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
        int stringWidth = fm.stringWidth(UNOConstants_Original.UNO_CARDBACK) / 2;
        int fontHeight = defaultFont.getSize() / 3;

        g.setColor(Color.YELLOW);
        g.setFont(defaultFont);
        g.drawString(UNOConstants_Original.UNO_CARDBACK, cardWidth / 2 - stringWidth, cardHeight / 2 + fontHeight);
    }
}
