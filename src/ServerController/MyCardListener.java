package ServerController;
/*
Code created by Josh Braza 
*/
import View.UNOCard;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyCardListener extends MouseAdapter {
	
	UNOCard sourceCard;
	Server myServer;
	
	public void setServer(Server server){
		myServer = server;
	}
	
        @Override
	public void mousePressed(MouseEvent e) {
		sourceCard = (UNOCard) e.getSource();
		
		try{
			if(myServer.canPlay)
				myServer.playThisCard(sourceCard);
			
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		
		sourceCard = (UNOCard) e.getSource();
		Point p = sourceCard.getLocation();
		p.y -=20;
		sourceCard.setLocation(p);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		sourceCard = (UNOCard) e.getSource();
		Point p = sourceCard.getLocation();
		p.y +=20;
		sourceCard.setLocation(p);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

}
