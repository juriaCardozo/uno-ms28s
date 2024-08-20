package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedJButton extends JButton {

    private final Color hoverBackgroundColor;
    private final Color originalBackgroundColor;
    private final int radius;

    public RoundedJButton(String text, Color corFundo, int radius) {
        super(text);
        setOpaque(false); // Necessário para permitir a transparência
        setContentAreaFilled(false); // Evita que o fundo padrão seja desenhado
        setFocusPainted(false); // Remove a linha de foco do botão
        setBorderPainted(false); // Remove a borda padrão do botão
        this.radius = radius;
        originalBackgroundColor = corFundo;
        hoverBackgroundColor = originalBackgroundColor.darker();
        setBackground(originalBackgroundColor);

        // Adiciona um MouseListener para detectar o hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackgroundColor); // Altera a cor de fundo ao entrar com o mouse
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalBackgroundColor); // Restaura a cor original ao sair com o mouse
                repaint();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define a cor de fundo igual ao fundo do JPanel
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }
}