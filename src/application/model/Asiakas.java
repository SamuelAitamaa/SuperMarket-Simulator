package application.model;


/**
 * @author Samuel
 * Luokka, jossa käsitellään asiakkaita ja heidän käyttämiään aikoja jonoissa ja kassoilla.
 *
 */
public class Asiakas {
	/**
	 * Aika, jolloin saavutaan palvelupisteeseen
	 */
	private double saapumisaika;
	/**
	 * Aika, jolloin poistutaan palvelupisteetä
	 */
	private double poistumisaika;
	/**
	 * Aika, jolloin saavutaan palvelupisteen jonoon
	 */
	private double jonoonSaapuminen;
	/**
	 * Aika, jolloin poistutaan palvelupisteen jonosta
	 */
	private double jonostaPoistuminen;
	/**
	 * Jonossa vietetty aika
	 */
	private double jonotusaika;
	/**
	 * Palvelupisteessä vietetty aika
	 */
	private double palveluaika;
	/**
	 * Asiakkaiden henkilökohtainen id
	 */
	private int id;
	/**
	 * id:n muodostamista varten alustettu indeksi
	 */
	private static int i = 1;
	/**
	 * Jonotuksien keskiarvoa varten alustettu muuttuja
	 */
	private static double jonotusSum = 0;
	/**
	 * Palveluaikojen keskiarvoa varten alustettu muuttuja
	 */
	private static double palveluSum = 0;
	/**
	 * Jonoissa vietettyjen aikojen keskiarvo
	 */
	private static double jonotusKeskiarvo;
	/**
	 * Palvelupisteissä vietettyjen aikojen keskiarvo
	 */
	private static double palveluKeskiarvo;
	
	public Asiakas(){
	    id = i++;
	    
		//saapumisaika = Kello.getInstance().getAika();
		//Trace.out(Trace.Level.INFO, "Uusi asiakas:" + id + ":"+saapumisaika);
	}

	/**
	 * Metodi joka palauttaa tietyn asiakkaat poistumisajan kassalta
	 * @return poistumisaika kassalta.
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/**
	 * Metodi joka asettaa tietyn asiakkaat poistumisajan kassalta
	 * @param poistumisaika aika jolloin poistuttiin (nykyhetki)
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/**
	 * Metodi joka palauttaa tietyn asiakkaat saapumisajan kassalle
	 * @return saapumisaika kassalle
	 */
	public double getSaapumisaika() {
		return saapumisaika;
	}

	/**
	 * Metodi joka asettaa tietyn asiakkaat saapumisajan kassalle
	 * @param saapumisaika jolloin kassalle saavuttiin (nykyhetki)
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}
	
	/**
	 * Metodi, jolla raportoidaan kunkin asiakkaan käyttämiä aikoja systeemissä sekä yleisiä keskiarvoja.
	 */
	public void raportti(){
		
		Trace.out(Trace.Level.INFO, "------------------------------------------------------------------");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui jonoon:" + getJonoonSaapuminen() + " ja poistui " + getJonostaPoistuminen());
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui kassalle:" + getSaapumisaika() + " ja poistui kassalta " + getPoistumisaika());
		Trace.out(Trace.Level.INFO, "----------------------------// Keskiarvot //-------------------------------------");
		jonotusSum += getJonotusAika();
		palveluSum += getPalveluAika();
		jonotusKeskiarvo = jonotusSum/id;
		palveluKeskiarvo = palveluSum/id;
		
		Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen jonotusaika: "+ jonotusKeskiarvo);
		Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen palveluaika: "+ palveluKeskiarvo);
		Trace.out(Trace.Level.INFO, "------------------------------------------------------------------");
	}
	
	public double getJonotuskeskiarvo() {
		return jonotusKeskiarvo;
	}
	
	public double getPalvelukeskiarvo() {
		return palveluKeskiarvo;
	}
	
	

	/**
	 * Metodi, joka palauttaa jonoon saapumisajan
	 * @return palauttaa jonoon saapumisajan
	 */
	public double getJonoonSaapuminen() {
		return jonoonSaapuminen;
	}

	/**
	 * metodi, joka asettaa jonoon saapumisajan
	 * @param jonoonSaapuminen aika, jolloin jonoon saavutaan
	 */
	public void setJonoonSaapuminen(double jonoonSaapuminen) {
		this.jonoonSaapuminen = jonoonSaapuminen;
	}

	/**
	 * @return palauttaa jonosta poistumisajan
	 */
	public double getJonostaPoistuminen() {
		return jonostaPoistuminen;
	}

	/**
	 * Asettaa jonosta poistumisajan
	 * @param jonostaPoistuminen haluttu kellonaika
	 */
	public void setJonostaPoistuminen(double jonostaPoistuminen) {
		this.jonostaPoistuminen = jonostaPoistuminen;
	}
	
	/**
	 * Laskee jonotusajan poistumisen ja saapumisen perusteella
	 * @return palauttaa jonotusajan
	 */
	public double getJonotusAika() {
		jonotusaika = (getJonostaPoistuminen() - getJonoonSaapuminen());
		return jonotusaika;
	}
	
	/**
	 * Laskee palveluajan poistumis- ja saapumisaikojen perusteella
	 * @return palauttaa palveluajan
	 */
	public double getPalveluAika() {
		this.palveluaika = (getPoistumisaika() - getSaapumisaika());
		return palveluaika;
	}
	/**
	 * Palauttaa jokaiselle asiakkaalle yksilöllisen ID:n
	 * @return Asiakkaan id
	 */
	public int getId() {
		return id;
	}
	

}
