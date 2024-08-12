package View;
/*
Code created by Josh Braza 
*/

import Interfaces.GameConstants;
import ServerController.Observer;
import ServerController.Server;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements GameConstants {

	private Session mainPanel;
	private Server server;
	private Observer observer;

	public final void setObserver(Observer newObserver) {
		observer = newObserver;
	}

	public Observer observer() {
		return observer;
	}

	public MainFrame(Observer observer) {
		setObserver(observer);
		startGame();
	}

	public final void startGame() {
		server = new Server();
		CARDLISTENER.setServer(server);
		BUTTONLISTENER.setServer(server);

		server.setObserver(observer);

		mainPanel = server.getSession();
		add(mainPanel);
	}
}
