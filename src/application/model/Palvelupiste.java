package application.model;

import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;


/**
 * Luokka joka määrittää palvelupisteet ja niiden toiminnallisuudet.
 * @author Samuel
 *
 */
public class Palvelupiste {

	private Moottori moottori;

	/**
	 * Palvelupisteen jono
	 */
	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>();
	/**
	 * Generaattori, joka tuottaa lukuja halutulla jakaumalla
	 */
	private ContinuousGenerator generator;
	/**
	 * Tapahtuman tyyppi
	 */
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi; 
	/**
	 * Asiakkaiden kokonaismäärä
	 */
	private static int asiakkaidenKokonaismaara = 0;
	/**
	 * Palvellut asiakkaat
	 */
	private static int palvellutAsiakkaat = 0;
	/**
	 * Kassanumero
	 */
	private int kassaNro;
	/**
	 * Palveluaika
	 */
	private double palveluaika;
	/**
	 * Kellon instanssi
	 */
	Kello kello = Kello.getInstance();
	/**
	 * Tapahtuma
	 */
	Tapahtuma t;
	
	
	private boolean varattu;//= new Normal(0.5, 5)
	


	public Palvelupiste(Moottori moottori, TapahtumanTyyppi tyyppi, int kassaNro, ContinuousGenerator generator){
		this.moottori = moottori;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.kassaNro = kassaNro;
		this.varattu = false;
		this.generator = generator;
	}


	/**
	 * Lisää asiakkaan jonoon ja asettaa jonoonsaapumisajan
	 * @param a lisättävä asiakas
	 */
	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		//Trace.out(Trace.Level.INFO, "Asiakas " +a.getId()+" saapui jonoon " +kassaNro + " " /*+ kello.getAika()*/);
		//a.setJonoonSaapuminen(Kello.getInstance().getAika());
		asiakkaidenKokonaismaara++;
	}

	
	/**
	 * Metodi, joka ottaa ensimmäisen asiakkaan jonosta ja asettaa jonosta poistumisajan.
	 * @return jonon kärki
	 */
	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		//jono.peek().setJonostaPoistuminen(kello.getAika());
		return jono.poll();
	}

	/**
	 * Metodi, jossa palvelupiste asetetaan varatuksi ja skeduloidaan palvelupisteeltä poistuminen
	 */
	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		varattu = true;
		t = new Tapahtuma(skeduloitavanTapahtumanTyyppi,kello.getAika()+getPalveluaika());
		moottori.uusiTapahtuma(t);
		palvellutAsiakkaat++;
	}


	/**
	 * @return palauttaa palvelupisteen statuksen
	 */
	public boolean onVarattu(){
		return varattu;
	}

	/**
	 * @return palauttaa palvelupisteen jonon statuksen
	 */
	public boolean onJonossa(){
		return jono.size() != 0;
	}
	
	/**
	 * @return palauttaa palvelupisteen jonon pituuden
	 */
	public int asiakkaitaJonossa() {
		return jono.size();
	}
	
	/**
	 * @return palauttaa asiakkaiden kokonaismäärän
	 */
	public int asiakkaidenKokonaismäärä() {
		return asiakkaidenKokonaismaara;
	}
	
	/**
	 * @return palauttaa palvelluiden asiakkaiden kokonaismäärän
	 */
	public int palvellutAsiakkaat() {
		return palvellutAsiakkaat;
	}
	
	/**
	 * Generoi seuraavan palveluajan
	 * @return palauttaa palveluajan
	 */
	public double getPalveluaika() {
		palveluaika = generator.sample();
		return palveluaika;
	}


}
