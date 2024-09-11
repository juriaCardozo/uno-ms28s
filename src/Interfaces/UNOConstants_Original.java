package Interfaces;
import java.awt.Color;
/*
Code created by Josh Braza 
*/
public interface UNOConstants_Original {
	public static Color BLACK = new Color(0,0,0);
	
	//Types
	public static int NUMBERS = 1;
	public static int ACTION = 2;
	public static int WILD = 3;

	//ActionCard Functions
	String REVERSE = "\uD83D\uDDD8";	//Unicode
	String SKIP	= "\uD83D\uDEC7";		//Unicode
	String DRAW2PLUS = "+2";
	
	//Wild card functions
	String W_COLORPICKER = "\uD83C\uDF10";
	String W_DRAW4PLUS = "+4";
	String UNO_CARDBACK = "UNO";
}
