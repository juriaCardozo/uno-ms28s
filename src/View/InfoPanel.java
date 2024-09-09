package View;
import Components.RoundedJButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.*;
/*
Code created by Josh Braza
*/

public class InfoPanel extends JPanel {
	private String error;
	private String text;
	private int panelCenter;
	
	private int you = 0;
	private int pc = 0;
	private int rest = 0;

	private final JButton helpButton;
	
	public InfoPanel(){
		setPreferredSize(new Dimension(275,250));
		setOpaque(false);
		error = "";
		text = "O jogo começou";
		
		helpButton = new RoundedJButton("Ajuda", new Color(79, 129, 189), 20);
		helpButton.setFocusable(false);
		helpButton.addActionListener(e -> showHelpDialog());
		add(helpButton);
		setLayout(null);

		updateText(text);
	}

	private void showHelpDialog() {

		String regras = """
			Regras do Uno:
				1. Cada jogador começa com 7 cartas.
				2. O objetivo é ser o primeiro a se livrar de todas as suas cartas.
				3. No seu turno, você pode jogar uma carta que corresponda à cor ou ao número da carta no centro do jogo.
				4. Se você não puder jogar uma carta, deve comprar uma do baralho.
				5. Existem cartas especiais que podem mudar a cor ou pular a vez do próximo jogador.
				6. Quando tiver duas cartas na mão, clique no botão de dizer "UNO!" e jogue sua carta.
				7. Se um jogador não disser "UNO!", ele deve comprar duas cartas como penalidade.
				8. O jogo continua até que um jogador fique sem cartas.
		""";

		JOptionPane.showMessageDialog(this, regras, "Regras do Jogo", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		panelCenter = getWidth()/2;
		
		printMessage(g);
		printError(g);
		printDetail(g);

		int buttonWidth = 120;
		int buttonHeight = 30;
		int xPos = panelCenter - buttonWidth / 2;
		int yPos = 200;
		helpButton.setBounds(xPos, yPos, buttonWidth, buttonHeight);
	}

	private void printError(Graphics g) {
		if(!error.isEmpty()){
			Font adjustedFont = new Font("Calibri", Font.PLAIN,	25);
			
			//Determine the width of the word to position
			FontMetrics fm = this.getFontMetrics(adjustedFont);
			int xPos = panelCenter - fm.stringWidth(error) / 2;
			
			g.setFont(adjustedFont);
			g.setColor(Color.red);
			g.drawString(error, xPos, 35);
			
			error = "";
		}
	}

	private void printMessage(Graphics g) {
		Font adjustedFont = new Font("Calibri", Font.BOLD,	25);
		
		//Determine the width of the word to position
		FontMetrics fm = this.getFontMetrics(adjustedFont);
		int xPos = panelCenter - fm.stringWidth(text) / 2;
		
		g.setFont(adjustedFont);
		g.setColor(new Color(228,108,10));
		g.drawString(text, xPos, 75);
	}
	
	private void printDetail(Graphics g){
		Font adjustedFont = new Font("Calibri", Font.BOLD,	25);
		FontMetrics fm = this.getFontMetrics(adjustedFont);
		g.setColor(new Color(127,127,127));
		
		//Determine the width of the word to position
		String text = "Cartas Jogadas";
		int xPos = panelCenter - fm.stringWidth(text) / 2;
		
		g.setFont(adjustedFont);
		g.drawString(text, xPos, 115);
		
		text = "Cartas restantes: " + rest;
		xPos = panelCenter - fm.stringWidth(text) / 2;
		g.drawString(text, xPos, 180);
		
		//Details
		adjustedFont = new Font("Calibri", Font.PLAIN,	20);
		g.setFont(adjustedFont);
		fm = this.getFontMetrics(adjustedFont);
		
		text = "Você : "+you + "  PC : " + pc;
		xPos = panelCenter - fm.stringWidth(text) / 2;
		g.drawString(text, xPos, 140);
	}

	public void updateText(String newText) {
		text = newText;
	}
	
	public void setError(String errorMgs){
		error = errorMgs;
	}
	
	public void setDetail(int[] playedCards, int remaining){
		you = playedCards[1];
		pc = playedCards[0];
		rest = remaining;
	}
}
