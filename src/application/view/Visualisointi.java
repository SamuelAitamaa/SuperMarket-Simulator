package application.view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Visualisointi extends Canvas {
	
	@FXML
	private Canvas canvas;
	private GraphicsContext gc;
	
	double i = 20;
	double j = 20;
	
	
	public Visualisointi(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	

	public void tyhjennaNaytto() {
		System.out.println("Näyttö tyhjennettiin");
		gc.setFill(Color.GREEN);
	}
	
	public void piirraPallo(int x, int y) {
		gc.setFill(Color.DARKGOLDENROD);
		gc.fillOval(x,y,10,10);
	}
	
	public void avaaKassa(int x, int y) {
		gc.setFill(Color.BLACK);
		gc.fillOval(x-2,y-2,24,24);
		
		gc.setFill(Color.GREEN);
		gc.fillOval(x,y,20,20);
	}
	
	public void varaaKassa(int x, int y) {
		gc.setFill(Color.BLACK);
		gc.fillOval(x-2,y-2,24,24);
		
		gc.setFill(Color.YELLOW);
		gc.fillOval(x,y,20,20);
	}
	
	public void suljeKassa(int x, int y) {
		gc.clearRect(x, y, 20, 20);
	}
	
	
	public void pyyhiPallo(int x, int y) {
		gc.clearRect(x, y, 10, 10);
	}
	
	public void uusiAsiakas() {
		gc.setFill(Color.BROWN);
		gc.fillOval(i,j,10,10);
		
		i = (i + 12) % this.getWidth();
		j = (j + 12) % this.getHeight();	
			
	}
	
}
	

