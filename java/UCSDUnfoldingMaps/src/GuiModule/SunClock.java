package GuiModule;

import processing.core.*;

public class SunClock extends PApplet {
	
	private String URL = "http://cdn.zarpo.com.br/magazine/wp-content/uploads/2015/06/O-lazer-de-um-hotel-Fazenda-em-Pernambuco-420x300.jpg";
	private PImage backgroundImg;
			
				
	public void setup(){
		size(500,500);
		backgroundImg = loadImage(URL,"jpg" );
	}
	public void draw(){
		
		backgroundImg.resize(0, height);
		image (backgroundImg, 0,0);
		int currentHour = hour();
		fill(255,(12- Math.abs(12 - 12))*10,(Math.abs(12 -12))*20, (12- Math.abs(12 - 12))*10);
		ellipse(width/8, height/10, width/10, height/10);
		
	    
	}
}

