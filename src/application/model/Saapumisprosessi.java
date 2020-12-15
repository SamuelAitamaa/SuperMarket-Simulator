package application.model;
import eduni.distributions.*;


/**
 * Luokka, joka vastaa asiakkaiden saapumisista kauppaan
 * @author Samuel
 *
 */
public class Saapumisprosessi {
	private Moottori moottori;
	private ContinuousGenerator generaattori;
	private TapahtumanTyyppi tyyppi;

	//Testej� varten,  ks. alla oleva kommentoitu osuus  
	
	
	public Saapumisprosessi(Moottori m, ContinuousGenerator g, TapahtumanTyyppi tyyppi){
		this.moottori = m;
		this.tyyppi = tyyppi;
		this.generaattori = g;
	}

	/**
	 * Generoi seuraavan asiakkaan saapumisen ja lisää sen tapahtumalistaan
	 */
	public void generoiSeuraava(){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika()+generaattori.sample());
		moottori.uusiTapahtuma(t);
	}

}
