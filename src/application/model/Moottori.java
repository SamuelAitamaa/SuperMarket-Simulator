package application.model;

import java.text.DecimalFormat;
import application.controller.Kontrolleri;
import eduni.distributions.*;

/**
 * Tässä luokassa toimii simulaattorin moottori. Moottorissa tehdään lähes kaikki toiminnallisuus mitä simulaattori tarvitsee
 * @author Samuel
 *
 */
public class Moottori extends Thread implements MoottoriIf {
	
	/**
	 * Kello instanssin alustus
	 */
	private Kello kello;
	/**
	 * Palvelupisteiden listan alustus
	 */
	private Palvelupiste[] palvelupisteet;
	
	/**
	 * Simulointiajan alustus
	 */
	private double simulointiaika = 0;
	/**
	 * Viiveen alustus
	 */
	private double viive = 0;
	/**
	 * Kassojen määrän alustus
	 */
	private int kassojenMaara = 0;
	/**
	 * Asiakkaiden kokonaismäärä
	 */
	private static int asiakkaidenKokonaismaara = 0;
	/**
	 * Lyhyimmän palvelupisteen jonon indeksi
	 */
	private int lyhyimmanIndex;

	/**
	 * Saapumisprosessin olion alustus
	 */
	private Saapumisprosessi saapumisprosessi;
	/**
	 * Tapahtumalistan olion alustus
	 */
	private Tapahtumalista tapahtumalista;
	/**
	 * Asiakkaiden olioiden alustus
	 */
	private Asiakas a;
	/**
	 * Kontrollerin olion alustus
	 */
	private Kontrolleri ctrl;
	/**
	 * Itsepalvelukassoihin viittaavan totuusarvon alustus
	 */
	private boolean kaytossa;
	
	private int palvellutAsiakkaat;
	DecimalFormat df = new DecimalFormat("#0.0");
	
	private static int index = 0;
	
	
	
	String itsepalvelukassat;
	private int ikIndex;
	

	public Moottori(Kontrolleri ctrl, int saapumisKA, int hajonta){
		this.ctrl = ctrl;
		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia
		palvelupisteet = new Palvelupiste[8];
		palvelupisteet[0]=new Palvelupiste(this, TapahtumanTyyppi.DEP0, 0, new Normal(1,1));
		palvelupisteet[1]=new Palvelupiste(this, TapahtumanTyyppi.DEP1, 1, new Normal(60,3));
		palvelupisteet[2]=new Palvelupiste(this, TapahtumanTyyppi.DEP2, 2, new Normal(60,3));
		palvelupisteet[3]=new Palvelupiste(this, TapahtumanTyyppi.DEP3, 3, new Normal(60,3));
		palvelupisteet[4]=new Palvelupiste(this, TapahtumanTyyppi.DEP4, 4, new Normal(60,3));
		palvelupisteet[5]=new Palvelupiste(this, TapahtumanTyyppi.DEP5, 5, new Normal(60,3));
		palvelupisteet[6]=new Palvelupiste(this, TapahtumanTyyppi.DEP6, 6, new Normal(60,3));
		
		// Itsepalvelukassa		
		
		palvelupisteet[7]=new Palvelupiste(this, TapahtumanTyyppi.DEP7, 7, new Normal(10,3));
		
		saapumisprosessi = new Saapumisprosessi(this, new Normal (saapumisKA,hajonta),TapahtumanTyyppi.ARR0);
		tapahtumalista = new Tapahtumalista();	
		
		saapumisprosessi.generoiSeuraava();
	}
	
	
	/**
	 * Aika, kuinka kauan haluamme simuloida
	 */
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}
	
	
	/**
	 * Viive, selkeyttää simulaattorin visuaalista ilmentymää
	 */
	private void viive() {
		try {
			sleep((long)viive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metodi jota kutsutaan moottorin käynnistyessä. Looppaa simulointiajan mukaisesti
	 */
	public void run(){
		while (simuloidaan()){
			viive();
			kello.setAika(nykyaika());
			ctrl.updateTimeLabel();
			siirräAsiakasJonoon();
			Palvele();
		}
		tulostaTulokset();
	}
	
	/**
	 * Suorittaa seuraavan tapahtuman tapahtumalistasta ja samalla poistaa sen sieltä
	 */
	void siirräAsiakasJonoon(){
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	/**
	 * Tarkistaa palvelupisteet jotka ovat vapaina ja joissa on jonoa, aloittaa palvelun siellä
	 */
	void Palvele(){
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}
	
	/**
	 * Tarkistaa lyhyimmän jonon. Määrittää jonon, johon seuraava asiakas siirtyy
	 * @return indeksi joka osoittaa lyhyimpään jonoon
	 */
	private int lyhyinJono() {
        //lyhyimmänIndex = 0;
        for (int i=0; i < kassojenMaara+1; i++){
            if (palvelupisteet[lyhyimmanIndex].asiakkaitaJonossa() > palvelupisteet[i].asiakkaitaJonossa() || (!palvelupisteet[i].onVarattu() && palvelupisteet[lyhyimmanIndex].onVarattu())) {
                lyhyimmanIndex = i;
                break;
            }
        }
        if (kaytossa && palvelupisteet[lyhyimmanIndex].asiakkaitaJonossa() > palvelupisteet[7].asiakkaitaJonossa()) {
        	lyhyimmanIndex = 7;
        }
        return lyhyimmanIndex;
    }
	
	/**
	 * Suorittaa tapahtuman tapahtumalistasta.
	 * ARR0 asiakas saapuu kauppaan
	 * DEP0 asiakas siirtyy lyhyimpään kassajonoon
	 * DEP1-6 Asiakkaat poistuvat palvelupisteiltä
	 * Kussakin vaiheessa asiakkaita visualisoidaan ja tarkkaillaan tapahtumahetkien aikoja
	 * @param t
	 */
	void suoritaTapahtuma(Tapahtuma t){  // Keskitetty algoritmi tapahtumien k�sittelyyn ja asiakkaiden siirtymisten hallintaan
		switch (t.getTyyppi()){
		
			case ARR0:	
						ctrl.piirraPallo(553+(palvelupisteet[0].asiakkaitaJonossa() * 12), 242);
						a = new Asiakas();
						palvelupisteet[0].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "Asiakas " +a.getId()+" saapui kauppaan " + kello.getAika());
						asiakkaidenKokonaismaara++;
						saapumisprosessi.generoiSeuraava();
				break;
			case DEP0:		// Siirretään lyhyimpään jonoon
						int lyhyimmanPalvelupisteenIndeksi = lyhyinJono();
						Palvelupiste kohde = palvelupisteet[lyhyimmanPalvelupisteenIndeksi];

						switch(lyhyimmanPalvelupisteenIndeksi){
							case 1: 
								ctrl.piirraPallo(130+kohde.asiakkaitaJonossa()*12,116);
								ctrl.varaaKassa(105,115);
								break;
							case 2: 
								ctrl.piirraPallo(130+kohde.asiakkaitaJonossa()*12,170); 
								ctrl.varaaKassa(105,165);
								break;
							case 3: 
								ctrl.piirraPallo(130+kohde.asiakkaitaJonossa()*12,217); 
								ctrl.varaaKassa(105,215);
								break;
							case 4: 
								ctrl.piirraPallo(130+kohde.asiakkaitaJonossa()*12,268); 
								ctrl.varaaKassa(105,265);
								break;
							case 5: 
								ctrl.piirraPallo(130+kohde.asiakkaitaJonossa()*12,317); 
								ctrl.varaaKassa(105,315);
								break;
							case 6: 
								ctrl.piirraPallo(130+kohde.asiakkaitaJonossa()*12,364); 
								ctrl.varaaKassa(105,365);
								break;
							case 7: 
								ctrl.piirraPallo(200+kohde.asiakkaitaJonossa()*12,437); 
								ikIndex = (int) ((Math.random() * (6 - 1)) + 1);
								varaaItsepalvelukassa(ikIndex);
								break;
				}
							ctrl.pyyhiPallo(553+(palvelupisteet[0].asiakkaitaJonossa() * 12), 242);
							
							Asiakas a = palvelupisteet[0].otaJonosta();
							palvelupisteet[lyhyimmanPalvelupisteenIndeksi].lisaaJonoon(a);
							a.setJonoonSaapuminen(kello.getAika());
							Trace.out(Trace.Level.INFO, "Asiakas " +a.getId()+" saapui jonoon " + (lyhyimmanPalvelupisteenIndeksi+1) + " " /*+ kello.getAika()*/);
							
							//palvelupisteet[lyhyimmanPalvelupisteenIndeksi].lisaaJonoon(palvelupisteet[0].otaJonosta());							
							break;
							
							// DEP1-6 poistetaan järjestelmästä
			case DEP1:		
							
							 ctrl.pyyhiPallo(130+((palvelupisteet[1].asiakkaitaJonossa() - 1) * 12), 116);
							 poistaKassalta(1);
							 ctrl.avaaKassa(105,115+(0*50));
							 break;
			case DEP2:		
							ctrl.pyyhiPallo(130+((palvelupisteet[2].asiakkaitaJonossa() - 1) * 12), 170);				
							poistaKassalta(2);
							 ctrl.avaaKassa(105,115+(1*50));
							break;
			case DEP3:		
							ctrl.pyyhiPallo(130+((palvelupisteet[3].asiakkaitaJonossa() - 1) * 12), 217);
							poistaKassalta(3);
							ctrl.avaaKassa(105,115+(2*50));
							break;
			case DEP4:		
							ctrl.pyyhiPallo(130+((palvelupisteet[4].asiakkaitaJonossa() - 1) * 12), 268);
							poistaKassalta(4);
							 ctrl.avaaKassa(105,115+(3*50));
				 			break;
			case DEP5:		
							ctrl.pyyhiPallo(130+((palvelupisteet[5].asiakkaitaJonossa() - 1) * 12), 317);
							poistaKassalta(5);
							ctrl.avaaKassa(105,115+(4*50));
				 			break;
			case DEP6:		
							ctrl.pyyhiPallo(130+((palvelupisteet[6].asiakkaitaJonossa() - 1) * 12), 364);
							poistaKassalta(6);
							ctrl.avaaKassa(105,115+(5*50));
							palvellutAsiakkaat++;
				 			break;	
			case DEP7: 
							ctrl.pyyhiPallo(200+((palvelupisteet[7].asiakkaitaJonossa() - 1) * 12), 437);
							poistaKassalta(7);
							avaaItsepalvelukassa(ikIndex);
							break;	
							
			
					}
	}
	
	public void poistaKassalta(int kassanIndex) {
		a = palvelupisteet[kassanIndex].otaJonosta();
		 a.setJonostaPoistuminen(kello.getAika());
		 Trace.out(Trace.Level.INFO, "Asiakas " +a.getId()+" poistui jonosta ja saapui kassalle " + 1 + " kello " + kello.getAika());
		 a.setSaapumisaika(kello.getAika());
		 a.setPoistumisaika(kello.getAika() + palvelupisteet[kassanIndex].getPalveluaika());
		 Trace.out(Trace.Level.INFO, "Asiakasta " +a.getId()+" palveltiin kassalla " + 1);
		 a.raportti();						
		 palvellutAsiakkaat++;
	}

	/**
	 * Luo uuden tapahtuman tapahtumalistaan
	 * @param t uusi tapahtuma
	 */
	public void uusiTapahtuma(Tapahtuma t){
		tapahtumalista.lisaa(t);
	}


	/**
	 * 
	 * @return aika, jolloin seuraava tapahtuma on skeduloitu tapahtuvaksi
	 */
	public double nykyaika(){
		return tapahtumalista.getSeuraavanAika();
	}
	
	/**
	 * Totuusarvo, joka määrittää moottorin toiminnan jatkumisen simulointiajan ja nykyajan perusteella
	 * @return Arvo, jota run() metodi hyödyntää
	 */
	public boolean simuloidaan(){
		Trace.out(Trace.Level.INFO, "Kello on: " + kello.getAika());
		return kello.getAika() < simulointiaika;
	}
	
	
	
	/**
	 * Tulosten kirjaamiseen käytettävä metodi. Kutsutaan simulaattorin toiminnan loputtua tarkastelemaan saatuja lukuja.
	 */
	private void tulostaTulokset(){
		int henkilokunta;
		double tyotunnit;
		double tyonTeho;
		Asiakas a = new Asiakas();
		if(kaytossa) {
			itsepalvelukassat = "Kyllä";
			henkilokunta = kassojenMaara + 1;
		} else {
			itsepalvelukassat = "Ei";
			henkilokunta = kassojenMaara;
		}
		tyotunnit = (double) ((simulointiaika * henkilokunta) / 3600);
		tyonTeho = palvellutAsiakkaat/tyotunnit;
		String jonotuskeskiarvo = df.format(a.getJonotuskeskiarvo()) + " s";
		String palvelukeskiarvo = df.format(a.getPalvelukeskiarvo()) + " s";
		String formattedTyotunnit = df.format(tyotunnit) + " h";
		String formattedTyonTeho = df.format(tyonTeho);
		String formattedSimulointiaika = df.format(simulointiaika) + " s";
		
		Tulokset tulokset = new Tulokset(index, formattedSimulointiaika, asiakkaidenKokonaismaara, palvellutAsiakkaat, jonotuskeskiarvo, palvelukeskiarvo, kassojenMaara, itsepalvelukassat, henkilokunta, formattedTyotunnit, formattedTyonTeho);
		ctrl.vieTulokset(tulokset, index);
		index++;
	}

	/**
	 * Asettaa halutun viiveen
	 */
	@Override
	public void setViive(long aika) {
		viive = aika;
		
	}

	/**
	 * Metodi, jonka avulla saadaan asetettu viive
	 */
	@Override
	public long getViive() {
		return (long) viive;
	}
	
	/**
	 * Metodi, jolla saadaan kassojen määrä. Kutsutaan LauncherControllerista.
	 * @param kassojenMäärä tekstikentästä saatu tieto
	 */
	public void getKassojenmaara(int kassojenMäärä) {
		this.kassojenMaara = kassojenMäärä;
	}
	
	/**
	 * Totuusarvo sen mukaan onko itsepalvelukassat käytössä vai ei
	 * @param käytössä arvo jota hyödynnetään lyhyinJono() funktiossa
	 */
	public void setItsepalvelukassat(boolean kaytossa) {
		this.kaytossa = kaytossa;
		
	}
	
	public void avaaKassat(int kassojenMaara, boolean itsepalvelukassat) {
		for(int i=0; i < kassojenMaara; i++) {
			ctrl.avaaKassa(105,115+(i*50));
		}
		
		if(itsepalvelukassat) {
			for(int i=0; i < 3; i++) {
				ctrl.avaaKassa(75+(i*41), 410);
			}
			
			for(int i=0; i < 3; i++) {
				ctrl.avaaKassa(75+(i*41), 463);
			}
		}
	}
	
	public void varaaItsepalvelukassa(int ikIndex) {
		
		switch (ikIndex) {
		case 1: ctrl.varaaKassa(75+(0*41), 410); break;
		case 2: ctrl.varaaKassa(75+(1*41), 410); break;
		case 3: ctrl.varaaKassa(75+(2*41), 410); break;
		case 4: ctrl.varaaKassa(75+(0*41), 463); break;
		case 5: ctrl.varaaKassa(75+(1*41), 463); break;
		case 6: ctrl.varaaKassa(75+(2*41), 463); break;
		}
	}
	
	public void avaaItsepalvelukassa(int ikIndex) {
		switch (ikIndex) {
		case 1: ctrl.avaaKassa(75+(0*41), 410); break;
		case 2: ctrl.avaaKassa(75+(1*41), 410); break;
		case 3: ctrl.avaaKassa(75+(2*41), 410); break;
		case 4: ctrl.avaaKassa(75+(0*41), 463); break;
		case 5: ctrl.avaaKassa(75+(1*41), 463); break;
		case 6: ctrl.avaaKassa(75+(2*41), 463); break;
		}
	}
	
	//avaaKassat(Integer.parseInt(kassojenMäärä.getText()), getItsepalvelukassat));
	
}