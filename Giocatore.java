/**
 * Classe Giocatore.
 * @author Gabriele
 * @version 1.0
 */
public class Giocatore {

	protected String titoloG;
	protected char pedina;
	protected int score;
	protected int pos;
	
	public Giocatore(String titoloG, char pedina, int score, int pos) {
		this.titoloG = titoloG;
		this.pedina = pedina;
		this.score = score;
		this.pos = pos;
	}
	
	public Giocatore(String titoloG, char pedina) {
		this.titoloG = titoloG;
		this.pedina = pedina;
	}

	public String getTitoloG() {
		return titoloG;
	}

	public char getPedina() {
		return pedina;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int x) {
		score = x;
	}

	/**
	 * Metodo che prende in entrata un valore e lo somma al punteggio del giocatore.
	 * @param addScore Quantita di punti aggiunta.
	 */
	public void addScore(int addScore) {
		score = score + addScore;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
	/**
	 * Metodo per spostare la pedina del giocatore in avanti o indietro
	 * @param n Quantita' di caselle di cui spostarsi
	 */
	public void movePos(int n) {
		pos += n;
		if(pos < 0) {
			pos = 0;
		}
	}
	
	
	
	
	
}
