package View;
/*
Code created by Josh Braza 
*/

import Components.RoundedJButton;
import GameModel.Player;
import Interfaces.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class PlayerPanel extends JPanel implements GameConstants {

	private Player player;
	private String name;

	private final Box myLayout;
	private final JLayeredPane cardHolder;
	private Box controlPanel;

	private RoundedJButton draw;
	private RoundedJButton sayUNO;
	private JLabel nameLbl;
	private final MyButtonHandler handler;

	// Constructor
	public PlayerPanel(Player newPlayer) {
		setPlayer(newPlayer);

		myLayout = Box.createHorizontalBox();
		cardHolder = new JLayeredPane();
		cardHolder.setPreferredSize(new Dimension(600, 175));

		// Set
		setCards();
		setControlPanel(newPlayer);

		myLayout.add(cardHolder);
		myLayout.add(Box.createHorizontalStrut(40));
		myLayout.add(controlPanel);
		add(myLayout);

		// Register Listeners
		handler = new MyButtonHandler();
		draw.addActionListener(BUTTONLISTENER);
		draw.addActionListener(handler);

		sayUNO.addActionListener(BUTTONLISTENER);
		sayUNO.addActionListener(handler);
		enableButtons();

	}

	@SuppressWarnings("static-access")
	public void setCards() {
		cardHolder.removeAll();

		// Origin point at the center
		Point origin = getPoint(cardHolder.getWidth(), player.getTotalCards());
		int offset = calculateOffset(cardHolder.getWidth(),
				player.getTotalCards());

		int i = 0;
		for (UNOCard card : player.getAllCards()) {
			card.setBounds(origin.x, origin.y, card.CARDSIZE.width,
					card.CARDSIZE.height);
			cardHolder.add(card, i++);
			cardHolder.moveToFront(card);
			origin.x += offset;
		}
		repaint();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		setPlayerName(player.getName());
	}

	public void setPlayerName(String playername) {
		this.name = playername;
	}

	private void setControlPanel(Player newPlayer) {
		PlayerIcon playerIcon = newPlayer.getPlayerIcon();
		playerIcon.setIconSize(24, 32);
		JLabel playerIconLabel = new JLabel(playerIcon);
		Dimension buttonSize = new Dimension(150, 35);

		draw = new RoundedJButton("Comprar", new Color(79, 129, 189), 20);
		sayUNO = new RoundedJButton("Dizer UNO!", new Color(149, 55, 53), 20);
		nameLbl = new JLabel(name);

		draw.setFont(new Font("Arial", Font.BOLD, 20));
		draw.setPreferredSize(buttonSize);
		draw.setFocusable(false);

		sayUNO.setFont(new Font("Arial", Font.BOLD, 20));
		sayUNO.setPreferredSize(buttonSize);
		sayUNO.setFocusable(false);

		nameLbl.setForeground(Color.WHITE);
		nameLbl.setFont(new Font("Arial", Font.BOLD, 15));

		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		namePanel.setOpaque(false);
		namePanel.setAlignmentX(LEFT_ALIGNMENT);
		namePanel.add(playerIconLabel);
		namePanel.add(Box.createHorizontalStrut(5));
		namePanel.add(nameLbl);

		controlPanel = Box.createVerticalBox();
		controlPanel.add(namePanel);
		controlPanel.add(draw);
		controlPanel.add(Box.createVerticalStrut(10));
		controlPanel.add(sayUNO);
		controlPanel.add(Box.createVerticalStrut(15));

		if (newPlayer.isPC()) {
			draw.setVisible(false);
			sayUNO.setVisible(false);
		}
	}

	private int calculateOffset(int width, int totalCards) {
		int offset = 71;
		if (totalCards <= 8) {
			return offset;
		} else {
			double o = (width - 100) / (totalCards - 1);
			return (int) o;
		}
	}

	private Point getPoint(int width, int totalCards) {
		Point p = new Point(0, 20);
		if (totalCards >= 8) {
			return p;
		} else {
			p.x = (width - calculateOffset(width, totalCards) * totalCards) / 2;
			return p;
		}
	}

	public void enableButtons() {
		draw.setEnabled(player.isMyTurn());
		sayUNO.setEnabled(player.isMyTurn());
	}

	class MyButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (player.isMyTurn()) {

				if (e.getSource() == draw)
					BUTTONLISTENER.drawCard();
				else if (e.getSource() == sayUNO)
					BUTTONLISTENER.sayUNO();
			}
		}
	}
}
