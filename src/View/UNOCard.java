package View;
/*
Code created by Josh Braza 
*/
import Interfaces.CardInterface;
import Interfaces.GameConstants;
import Interfaces.UNOConstants_Original;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class UNOCard extends JPanel implements CardInterface, UNOConstants_Original {
	
	private Color cardColor = null;
	private String value = null;
	private int type = 0;
	private boolean showValue = false;
	private boolean minimalistStyle = false;

	private int FontHeight;
	private int StringWidth;
	
	private final Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
	private final  Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);
	
	public UNOCard(){
	}
	
	public void setShowValue(boolean showValue) {
		this.showValue = showValue;
		this.repaint();
	}
	
	public UNOCard(Color cardColor, int cardType, String cardValue, boolean minimalistStyle){
		this.cardColor = cardColor;
		this.type = cardType;
		this.value = cardValue;
		this.showValue = true;
		this.minimalistStyle = minimalistStyle;
		
		this.setPreferredSize(CARDSIZE);
		this.setBorder(defaultBorder);
		
		this.addMouseListener(new MouseHandler());
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (minimalistStyle) {
			if(showValue) paintMinimalistCardFront(g2);
			else paintMinimalistCardBack(g2);
		} else {
			if(showValue) paintCardFront(g2);
			else paintCardBack(g2);
		}
	}
	
	private void paintCardFront(Graphics2D g) {
		int cardWidth = CARDSIZE.width;
		int cardHeight = CARDSIZE.height;
		
		//Paints the external border of the card
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, cardWidth, cardHeight);
		
		//Paints the color of the card
		int margin = 5;
		g.setColor(cardColor);
		g.fillRect(margin, margin, cardWidth-2*margin, cardHeight-2*margin);
		
		//Paints the oval format in the center of the card
		g.setColor(Color.white);
		AffineTransform org = g.getTransform();
		g.rotate(45,cardWidth*3/4,cardHeight*3/4);

		g.fillOval(0,cardHeight*1/4,cardWidth*3/5, cardHeight);
		g.setTransform(org);
		
		//Value in the center
		Font defaultFont = new Font("Helvetica", Font.BOLD, cardWidth/2+5);
		FontMetrics fm = this.getFontMetrics(defaultFont);
		StringWidth = fm.stringWidth(value)/2;
		FontHeight = defaultFont.getSize()*1/3;
		
		g.setColor(cardColor);
		g.setFont(defaultFont);
		g.drawString(value, cardWidth/2-StringWidth, cardHeight/2+FontHeight);
		
		//Value in the corner
		defaultFont = new Font("Helvetica", Font.ITALIC, cardWidth/5);
		fm = this.getFontMetrics(defaultFont);
		StringWidth = fm.stringWidth(value)/2;
		FontHeight = defaultFont.getSize()*1/3;
		
		g.setColor(Color.white);
		g.setFont(defaultFont);
		g.drawString(value, 2*margin,5*margin);
	}
	
	private void paintCardBack(Graphics2D g) {
		
		int cardWidth = CARDSIZE.width;
		int cardHeight = CARDSIZE.height;

		//Paints the color of the card
		int margin = 5;
		g.setColor(Color.BLACK);
		g.fillRect(margin, margin, cardWidth-2*margin, cardHeight-2*margin);
		
		//Paints the oval format in the center of the card
		g.setColor(GameConstants.unoConstants.getRED(GameConstants.selectedPalette));
		AffineTransform org = g.getTransform();
		g.rotate(45,cardWidth*3/4,cardHeight*3/4);

		g.fillOval(0,cardHeight*1/4,cardWidth*3/5, cardHeight);
		g.setTransform(org);

		//Paints the external border of the card
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, cardWidth, margin); //Top
		g.fillRect(0, cardHeight-margin, cardWidth, margin); //Bottom
		g.fillRect(0, margin, margin, cardHeight); //Left
		g.fillRect(cardWidth-5, 0, margin, cardHeight); //Right

		//Value in the center
		Font defaultFont = new Font("Helvetica", Font.BOLD, cardWidth/3);
		FontMetrics fm = this.getFontMetrics(defaultFont);
		StringWidth = fm.stringWidth("UNO")/2;
		FontHeight = defaultFont.getSize()*1/3;
		
		g.setColor(GameConstants.unoConstants.getYELLOW(GameConstants.selectedPalette));
		g.setFont(defaultFont);
		g.drawString("UNO", cardWidth/2-StringWidth, cardHeight/2+FontHeight);
	}

	private void paintMinimalistCardFront(Graphics2D g) {
		int cardWidth = CARDSIZE.width;
		int cardHeight = CARDSIZE.height;

		//Borda da carta
		g.setColor(cardColor);
		g.fillRect(0, 0, cardWidth, cardHeight);

		//Cor da carta
		int margin = 5;
		g.setColor(cardColor);
		g.fillRect(margin, margin, cardWidth-2*margin, cardHeight-2*margin);

		//Valor no centro da carta
		Font defaultFont = new Font("SansSerif", Font.PLAIN, cardWidth/3);
		FontMetrics fm = this.getFontMetrics(defaultFont);
		StringWidth = fm.stringWidth(value)/2;
		FontHeight = defaultFont.getSize()*1/3;

		g.setColor(Color.white);
		g.setFont(defaultFont);
		g.drawString(value, cardWidth/2-StringWidth, cardHeight/2+FontHeight);

		//Valores nos cantos da carta
		defaultFont = new Font("SansSerif", Font.PLAIN, cardWidth/6);

		g.setFont(defaultFont);
		g.drawString(value, 2*margin,4*margin);

		FontMetrics cornerMetrics = this.getFontMetrics(defaultFont);
		int cornerStringWidth = cornerMetrics.stringWidth(value);

		g.drawString(value, cardWidth-cornerStringWidth-2*margin, cardHeight-2*margin);
	}

	private void paintMinimalistCardBack(Graphics2D g) {

		int cardWidth = CARDSIZE.width;
		int cardHeight = CARDSIZE.height;

		//Paints the external border of the card
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, cardWidth, cardHeight);

		//Paints the color of the card
		int margin = 5;
		g.setColor(Color.BLACK);
		g.fillRect(margin, margin, cardWidth-2*margin, cardHeight-2*margin);

		//Paints the oval format in the center of the card
		g.setColor(GameConstants.unoConstants.getRED(GameConstants.selectedPalette));
		AffineTransform org = g.getTransform();
		g.rotate(45,cardWidth*3/4,cardHeight*3/4);

		g.fillOval(0,cardHeight*3/11,cardWidth*3/5-5, cardHeight-15);
		g.setTransform(org);

		g.setColor(Color.BLACK);
		AffineTransform org2 = g.getTransform();
		g.rotate(45, cardWidth*3/4, cardHeight*3/4);

		g.fillOval(5, cardHeight*3/9,cardWidth*3/5-15, cardHeight-30);
		g.setTransform(org2);

		//Value in the center
		Font defaultFont = new Font("SansSerif", Font.PLAIN, cardWidth/3);
		FontMetrics fm = this.getFontMetrics(defaultFont);
		StringWidth = fm.stringWidth("UNO")/2;
		FontHeight = defaultFont.getSize()*1/3;

		g.setColor(GameConstants.unoConstants.getYELLOW(GameConstants.selectedPalette));
		g.setFont(defaultFont);
		g.drawString("UNO", cardWidth/2-StringWidth, cardHeight/2+FontHeight);

		int lineHeight = 12;
		g.setColor(GameConstants.unoConstants.getGREEN(GameConstants.selectedPalette));
		g.fillRect(0, cardHeight - lineHeight, cardWidth, lineHeight);

		lineHeight = 12;
		g.setColor(GameConstants.unoConstants.getBLUE(GameConstants.selectedPalette));
		g.fillRect(25, cardHeight - lineHeight, cardWidth, lineHeight);

		lineHeight = 12;
		g.setColor(GameConstants.unoConstants.getRED(GameConstants.selectedPalette));
		g.fillRect(50, cardHeight - lineHeight, cardWidth, lineHeight);

		lineHeight = 12;
		g.setColor(GameConstants.unoConstants.getYELLOW(GameConstants.selectedPalette));
		g.fillRect(75, cardHeight - lineHeight, cardWidth, lineHeight);

		lineHeight = 10;
		g.setColor(Color.BLACK);
		g.fillRect(0, cardHeight - lineHeight, cardWidth, lineHeight);
	}

	/**
	 * My Mouse Listener
	 */
	class MouseHandler extends MouseAdapter {
		
        @Override
		public void mouseEntered(MouseEvent e){
			setBorder(focusedBorder);
		}
		
		@Override
		public void mouseExited(MouseEvent e){
			setBorder(defaultBorder);
		}
	}
	
	public void setCardSize(Dimension newSize){
		this.setPreferredSize(newSize);
	}
	
	@Override
	public void setColor(Color newColor) {
		this.cardColor = newColor;
	}

	@Override
	public Color getColor() {
		return cardColor;
	}

	@Override
	public void setValue(String newValue) {
		this.value = newValue;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setType(int newType) {
		this.type = newType;
	}

	@Override
	public int getType() {
		return type;
	}
}
