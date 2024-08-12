/*
Code created by Josh Braza
*/

import ServerController.Observer;
import View.MainFrame;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main::startNewGame);
	}

	static public void startNewGame() {
		Observer observer = new Observer();
		MainFrame frame = new MainFrame(observer);

		observer.setFunc(() -> {
                    frame.setVisible(false);
                    startNewGame();
                });

		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocation(200, 100);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
