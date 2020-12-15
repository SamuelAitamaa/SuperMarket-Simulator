package application.controller;

import application.model.Kello;
import application.model.Tulokset;
import application.view.GuiIf;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.text.NumberFormat;


	/**
	 * Kontrolleri
	 * Tässä luokassa esittelemme Moottorin (model) hyödyntämiä metodeja, jotka vaikuttavat suoraan visuaaliseen ilmentymään.
	 *
	 * @author Samuel Aitamaa
	 * @version 1
	 * 
	 */
public class Kontrolleri {

	private GuiIf gui;
	/**
	 * Desimaalilukujen muotoilija, jolla aika näytetään yhden desimaalin tarkkuudella
	 */
	NumberFormat formatter = new DecimalFormat("#0.0"); 
	/**
	 * Aika Labelin alustus
	 */
	private Label timeLabel;
	
	double simulointiaika;
	int asiakkaidenKokonaismaara;
	int palvellutAsiakkaat;
	double jonotuskeskiarvo;
	double palvelukeskiarvo;
	int kassojenMaara;
	String itsepalvelukassat;
	int henkilokunta;
	double tyotunnit;
	double tyonTeho;
	String test;
	Tulokset tulokset;
	Tulokset[] kaikkiTulokset = new Tulokset[17];
	int index;
	
	SimulationController ctrl = new SimulationController();
	
	public Kontrolleri(GuiIf gui) {
		this.gui = gui;
	}
	
	
	/**
	 * piirraPallo(x,y) on Visualisoinnin metodi, joka viedään Moottorille tämän Kontrollerin kautta. Palloja piirtämällä visualisoidaan palvelupisteihin saapuvia asiakkaita.
	 * @param x merkkaa piirrettävän pallon sijaintia x-akselilla näkymässä.
	 * @param y merkkaa piirrettävän pallon sijaintia y-akselilla näkymässä.
	 */
	public void piirraPallo(int x, int y) {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().piirraPallo(x,y);
			}
		});
	}
	
	/**
	 * pyyhiPallo(x,y) on Visualisoinnin metodi, joka viedään Moottorille tämän Kontrollerin kautta. Palloja pyyhkimällä visualisoidaan palvelupisteistä poistuvia asiakkaita.
	 * @param x merkkaa pyyhittävän pallon sijaintia x-akselilla näkymässä.
	 * @param y merkkaa pyyhittävän pallon sijaintia y-akselilla näkymässä.
	 */
	public void pyyhiPallo(int x, int y) {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().pyyhiPallo(x,y);
			}
		});
	}
	
	/**
	 * Tämä metodi vastaanottaa viittauksen Label-elementtiin, jossa kuvaamme nykyistä aikaa.
	 * @param timeLabel viittaus Labeliin.
	 */
	public void setTimeLabel(Label timeLabel) {
		this.timeLabel = timeLabel;
	}

	/**
	 * Metodi, jolla päivitämme aikaelementtiä ajankohtaisesti.
	 */
	public void updateTimeLabel() {
		Platform.runLater(new Runnable(){
			public void run(){
	
		timeLabel.setText(formatter.format(Kello.getInstance().getAika()) + " s");
			}
		});
	}
	
	public void avaaKassa(int x, int y) {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().avaaKassa(x,y);
			}
		});
	}
	
	public void varaaKassa(int x, int y) {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().varaaKassa(x,y);
			}
		});
	}
	
	public void suljeKassa(int x, int y) {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().suljeKassa(x,y);
			}
		});
	}
	
	public void vieTulokset(Tulokset tulokset, int index) {
		this.index = index;
		kaikkiTulokset[index] = tulokset;
	}
	
	
	
	public Tulokset[] tuoTulokset() {
		//System.out.println(kaikkiTulokset.length);
		Platform.runLater(() -> kaikkiTulokset[index].tulostaTulokset());
		return this.kaikkiTulokset;
	}

}
