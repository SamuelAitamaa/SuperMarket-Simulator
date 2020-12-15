package application.model;


/**
 * Luokka, jonka avulla määritellään Tapahtumat, niiden tyypit ja ajat.
 * @author Samuel
 *
 */
public class Tapahtuma implements Comparable<Tapahtuma> {
	
		
	/**
	 * Tapahtuman tyyppi
	 */
	private TapahtumanTyyppi tyyppi;
	/**
	 * Aika
	 */
	private double aika;
	
	public Tapahtuma(TapahtumanTyyppi tyyppi, double aika){
		this.tyyppi = tyyppi;
		this.aika = aika;
	}
	
	/**
	 * @param tyyppi asettaa tapahtuman tyypin
	 */
	public void setTyyppi(TapahtumanTyyppi tyyppi) {
		this.tyyppi = tyyppi;
	}
	/**
	 * @return palauttaa tapahtuman tyypin
	 */
	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}
	/**
	 * @param aika asettaa tapahtumalle skeduloitavan ajan
	 */
	public void setAika(double aika) {
		this.aika = aika;
	}
	/**
	 * @return palauttaa tapahtumalle skeduloidun ajan
	 */
	public double getAika() {
		return aika;
	}

	/**
	 * compareTo, jota Tapahtumalista hyödyntää lisätessään tapahtumia tapahtumalistaan.
	 */
	@Override
	public int compareTo(Tapahtuma arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}
	
	
	

}
