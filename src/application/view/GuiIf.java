package application.view;

public interface GuiIf {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public Visualisointi getVisualisointi();
	public int getKassojenmaara();
	//public void showSimulationView();
	//public void setTulokset(String test);

}
