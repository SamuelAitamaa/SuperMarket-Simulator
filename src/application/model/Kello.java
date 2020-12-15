package application.model;


/**
 * @author Samuel
 * Luokka, jonka instanssia käytetään mittaamaan aikaa. 
 *
 */
public class Kello {

	/**
	 * Kellon aika
	 */
	private double aika;
	/**
	 * Singleton Kellon instanssin alustus
	 */
	private static Kello instanssi; 
	private Kello(){
		aika = 0;
	}
	
	/**
	 * Luo Kello instanssin
	 * @return palauttaa instanssin
	 */
	public static Kello getInstance(){
		if (instanssi == null){
			instanssi = new Kello();	
		}
		return instanssi;
	}
	
	
	/**
	 * Asettaa ajan haluttuun hetkeen
	 * @param aika haluttu ajanhetki
	 */
	public void setAika(double aika){
		this.aika = aika;
	}

	/**
	 * @return palauttaa kellon nykyhetken
	 */
	public double getAika(){
		return aika;
	}
}
