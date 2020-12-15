package application.model;

import java.util.PriorityQueue;

/**
 * Luokka, joka luo varsinaisen tapahtumalistan ja jonka metodeja käytetään listan käsittelyyn.
 * @author Samuel
 *
 */
public class Tapahtumalista {
	/**
	 * Varsinainen tapahtumalista
	 */
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();
	
	public Tapahtumalista(){}
	
	/**
	 * Poistaa listan ensimmäisenä olevan tapahtuman
	 * @return lista, josta ensimmäinen tapahtuma on poistettu
	 */
	public Tapahtuma poista(){
		return lista.remove();
	}
	
	/**
	 * Lisää tapahtuman listalle
	 * @param t haluttu tapahtuma
	 */
	public void lisaa(Tapahtuma t){
		lista.add(t);
	}
	
	/**
	 * 
	 * @return palauttaa listan ensimmäisenä olevan tapahtuman tapahtuma-ajan
	 */
	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}
	
	/**
	 * @return palauttaa tapahtumalistan kokonaisuudessaan
	 */
	public PriorityQueue<Tapahtuma> getLista() {
		return lista;
	}
	
	
}
