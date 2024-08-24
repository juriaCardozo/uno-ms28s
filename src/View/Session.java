package View;
/*
Code created by Josh Braza
*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import GameModel.Game;

import static Interfaces.GameConstants.BUTTONLISTENER;
import static java.awt.Image.*;

public class Session extends JPanel {
	private PlayerPanel player1;
	private PlayerPanel player2;
	private final TablePanel table;

	private final Game game;

	private JButton audioControl;
	private JButton volumeUp;
	private JButton volumeDown;

	private AudioControlHandler audioControlHandler;
	private VolumeUpHandler volumeUpHandler;
	private VolumeDownHandler volumeDownHandler;

	private final String AUDIO_ON_ICON_PATH = "src/Icons/audio_on_icon.png";
	private final String AUDIO_OFF_ICON_PATH = "src/Icons/audio_off_icon.png";

	public Session(Game newGame, UNOCard firstCard){
		setPreferredSize(new Dimension(960,720));
		setLayout(new BorderLayout());

		game = newGame;

		setPlayers();
		table = new TablePanel(firstCard);
		table.setBackgroundColor(firstCard);
		player1.setOpaque(false);
		player2.setOpaque(false);

		add(player1,BorderLayout.NORTH);
		add(table, BorderLayout.EAST);
		add(player2, BorderLayout.SOUTH);

		setVolumeUp();
		setAudioControl();
		setVolumeDown();

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.setOpaque(false);
		topPanel.add(volumeUp);
		topPanel.add(audioControl);
		topPanel.add(volumeDown);
		add(topPanel, BorderLayout.CENTER);

		audioControlHandler = new AudioControlHandler();
		audioControl.addActionListener(BUTTONLISTENER);
		audioControl.addActionListener(audioControlHandler);
		audioControl.setEnabled(true);

		volumeUpHandler = new VolumeUpHandler();
		volumeUp.addActionListener(BUTTONLISTENER);
		volumeUp.addActionListener(volumeUpHandler);
		volumeUp.setEnabled(true);

		volumeDownHandler = new VolumeDownHandler();
		volumeDown.addActionListener(BUTTONLISTENER);
		volumeDown.addActionListener(volumeDownHandler);
		volumeDown.setEnabled(true);
	}

	private void setPlayers() {
		player1 = new PlayerPanel(game.getPlayers()[0]);
		player2 = new PlayerPanel(game.getPlayers()[1]);
	}

	private void setAudioControl() {
		audioControl = new JButton();
		setAudioButtonIcon(AUDIO_ON_ICON_PATH);
		setButtonStyle(audioControl);
	}

	private void setAudioButtonIcon(String iconPath) {
		ImageIcon audioIcon = new ImageIcon(
				new ImageIcon(iconPath).getImage()
						.getScaledInstance(18, 18, SCALE_SMOOTH));

		audioControl.setIcon(audioIcon);
	}

	private void setVolumeUp() {
		volumeUp = new JButton("+");
		setButtonStyle(volumeUp);
	}

	private void setVolumeDown() {
		volumeDown = new JButton("-");
		setButtonStyle(volumeDown);
	}

	private void setButtonStyle(JButton botao) {
		botao.setBackground(Color.WHITE);
		botao.setForeground(Color.BLACK);
		botao.setFont(new Font("Arial", Font.BOLD, 16));
		botao.setPreferredSize(new Dimension(43, 30));
		botao.setFocusable(false);
	}

	public void refreshPanel(){
		player1.setCards();
		player2.setCards();

		table.revalidate();
		revalidate();

		player1.enableButtons();
		player2.enableButtons();
	}

	public void updatePanel(UNOCard playedCard){
		table.setPlayedCard(playedCard);
		refreshPanel();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	class VolumeDownHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			volumeDown.setEnabled(game.volumeDown());
			volumeUp.setEnabled(true);
		}
	}

	class VolumeUpHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			volumeUp.setEnabled(game.volumeUp());
			volumeDown.setEnabled(true);
		}
	}

	class AudioControlHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean isBackgroundMusicOn = game.controlBackgroundMusic();

			if (isBackgroundMusicOn) setAudioButtonIcon(AUDIO_ON_ICON_PATH);
			else setAudioButtonIcon(AUDIO_OFF_ICON_PATH);
		}
	}
}
