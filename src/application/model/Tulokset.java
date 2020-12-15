package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import application.controller.Kontrolleri;
import application.view.GuiIf;

@Entity
@Table
public class Tulokset {
	@Column
	String simulointiaika;
	@Column
	int asiakkaidenKokonaismaara;
	@Column
	int palvellutAsiakkaat;
	@Column
	String jonotuskeskiarvo;
	@Column
	String palvelukeskiarvo;
	@Column
	int kassojenMaara;
	@Column
	String itsepalvelukassat;
	@Column
	int henkilokunta;
	@Column
	String tyotunnit;
	@Column
	String tyonTeho;
	@Column
	int index;
	
	private static int i = 1;
	GuiIf gui;
	
	Kontrolleri ctrl = new Kontrolleri(gui);
	
	public Tulokset(int index, String simulointiaika, int asiakkaidenKokonaismaara, int palvellutAsiakkaat,
			String jonotuskeskiarvo, String palvelukeskiarvo, int kassojenMaara, String itsepalvelukassat,
			int henkilokunta, String tyotunnit, String tyonTeho) {
		
		this.index = index;
		this.simulointiaika = simulointiaika;
		this.asiakkaidenKokonaismaara = asiakkaidenKokonaismaara;
		this.palvellutAsiakkaat = palvellutAsiakkaat;
		this.jonotuskeskiarvo = jonotuskeskiarvo;
		this.palvelukeskiarvo = palvelukeskiarvo;
		this.kassojenMaara = kassojenMaara;
		this.itsepalvelukassat = itsepalvelukassat;
		this.henkilokunta = henkilokunta;
		this.tyotunnit = tyotunnit;
		this.tyonTeho = tyonTeho;
	}
	
	public Tulokset() { }
	
	public void tulostaTulokset() {
		System.out.println("\n------------------------------------TULOKSET--------------------------------------------\n");
		
		System.out.println("Simulointi päättyi kello " + simulointiaika);
		
		System.out.println("\n---------------------------Asiakkaat----------------------------------------------\n");
		
	
		System.out.println("Asiakkaita saapui: " + asiakkaidenKokonaismaara);
		System.out.println("Asiakkaita palveltiin: " + palvellutAsiakkaat);
		System.out.println("Keskimääräinen jonotusaika: " + jonotuskeskiarvo);
		System.out.println("Keskimääräinen palveluaika: " + palvelukeskiarvo);
		
		System.out.println("\n---------------------------Henkilökunta----------------------------------------\n");
		
		System.out.println("Perinteisiä kassoja: " + kassojenMaara);
		System.out.println("Itsepalvelukassat: " + itsepalvelukassat);
		System.out.println("Työllistetty henkilökunta: " + henkilokunta);
		
		System.out.println("\n---------------------------Kauppa---------------------------------------------------\n");
		
		System.out.println("Työtunnit: " + tyotunnit);
		System.out.println("Palveltuja asiakkaita per työtunti: " + tyonTeho);
		System.out.println("\n---------------------------------------------------------------------------------\n");
	}
	
	
	/*double simulointiaika, int asiakkaidenKokonaismaara, int palvellutAsiakkaat,
			double jonotuskeskiarvo, double palvelukeskiarvo, int kassojenMaara, String itsepalvelukassat,
			int henkilokunta, double tyotunnit, double tyonTeho*/
	public int getIndex() {
		return index;
	}
	public String getSimulointiaika() {
		return simulointiaika;
	}
	
	public int getAsiakkaidenKokonaismaara() {
		return asiakkaidenKokonaismaara;
	}
	public int getPalvellutAsiakkaat() {
		return palvellutAsiakkaat;
	}
	public String getJonotuskeskiarvo() {
		return jonotuskeskiarvo;
	}
	public String getPalvelukeskiarvo() {
		return palvelukeskiarvo;
	}
	public int getKassojenMaara() {
		return kassojenMaara;
	}
	public String getItsepalvelukassat() {
		return itsepalvelukassat;
	}
	public int getHenkilokunta() {
		return henkilokunta;
	}
	public String getTyotunnit() {
		return tyotunnit;
	}
	public String getTyonTeho() {
		return tyonTeho;
	}
	
	public String toString() {
		return  "ID: " + getIndex() + "\n" +
				"Simulointiaika: " + getSimulointiaika() + "\n" +
				"Asiakkaita: " + getAsiakkaidenKokonaismaara() + "\n" +
				"Palveltuja: " + getPalvellutAsiakkaat() + "\n" +
				"Keskim. jonotus: " + getJonotuskeskiarvo() + "\n" +
				"Keskim. palvelu: " + getPalvelukeskiarvo() + "\n" +
				"Kassoja: " + getKassojenMaara() + "\n" +
				"Itsepalvelukassat: " + getItsepalvelukassat() + "\n" +
				"Henkilökunta: " + getHenkilokunta() + "\n" +
				"Työtunnit: " + getTyotunnit() + "\n" +
				"Työn teho: " + getTyonTeho() + "\n";
				
		
	}

}
